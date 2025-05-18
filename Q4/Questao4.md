# **Exercício 4: Fila de Mensagens****

Em sistemas distribuídos, garantir que um pedido seja processado **exatamente uma vez** (exactly-once) é um desafio comum, especialmente em cenários com falhas de rede, crashes de workers ou processamento duplicado. Este documento propõe uma solução usando **filas de mensagens** para garantir que pedidos de múltiplas origens (web, mobile, API) sejam processados de forma confiável, com **retentativas automáticas** e **deduplicação**.  

## **Arquitetura Proposta**  
A solução consiste nos seguintes componentes:  

1. **Produtores (Clients):**  
   - Aplicações web, mobile e API que publicam pedidos em uma fila.  
   - Cada pedido tem um **ID único** (UUID ou hash do conteúdo + timestamp).  

2. **Fila de Mensagens Principal (Order Queue):**  
   - Armazena pedidos pendentes (ex: **RabbitMQ, Amazon SQS, Apache Kafka**).  
   - Suporta **visibilidade temporizada** (para evitar processamento concorrente).  

3. **Workers (Consumidores):**  
   - Múltiplos workers consomem pedidos da fila.  
   - Implementam **lógica de idempotência** (evitam duplicação).  

4. **Banco de Dados de Estado (Order Status DB):**  
   - Armazena o **status** de cada pedido (ex: `pending`, `processing`, `completed`, `failed`).  
   - Usado para **deduplicação** (evitar reprocessamento).  

5. **Fila de Mensagens Mortas (DLQ - Dead Letter Queue):**  
   - Mensagens com falhas após **N tentativas** são movidas para análise manual.  

## **Fluxo de Processamento**  

1. **Publicação do Pedido:**  
   - O pedido é enviado para a fila com um **ID único**.  

2. **Consumo pelo Worker:**  
   - O worker pega a mensagem e ela fica **invisível temporariamente** (para evitar concorrência).  
   - Verifica no **banco de dados** se o pedido já foi processado.  
     - Se **já foi processado**, descarta a mensagem e confirma (**ACK**).  
     - Se **não foi processado**, marca como `processing` e executa a lógica de negócio.  

3. **Confirmação (ACK) ou Retentativa (NACK):**  
   - Se o processamento **falhar**, o worker envia **NACK** (não confirma) e a mensagem fica visível novamente após um tempo.  
   - Se **der certo**, envia **ACK**, marca como `completed` no banco e remove da fila.  

4. **Tratamento de Falhas Persistentes:**  
   - Se uma mensagem falhar **após X tentativas**, é movida para a **DLQ** para análise posterior.  


## **Tratamento de Cenários de Falha**  

| **Cenário de Falha**               | **Comportamento do Sistema**                                                                 |
|------------------------------------|---------------------------------------------------------------------------------------------|
| **Worker crash antes do ACK**      | A mensagem volta para a fila após o timeout de visibilidade e outro worker tenta processar.  |
| **Processamento duplicado**        | O banco de dados verifica o status e ignora pedidos já concluídos (idempotência).            |
| **Mensagem corrompida**            | Após N tentativas, vai para a DLQ para análise manual.                                      |
| **Banco de dados indisponível**    | Workers não confirmam processamento, e a mensagem retorna à fila após timeout.              |
| **Processamento lento**            | Workers podem estender o timeout para evitar reprocessamento desnecessário.                 |


## **Implementação Simplificada   

```python
def worker_process_message(message):
    order_id = message["id"]
    
    # Verifica se já foi processado (deduplicação)
    if database.get_order_status(order_id) == "completed":
        queue.ack(message)  # Confirma e remove da fila
        return
    
    try:
        # Marca como "em processamento" no banco
        database.set_order_status(order_id, "processing")
        
        # Processa o pedido (lógica de negócio)
        process_order(message)
        
        # Atualiza status para concluído
        database.set_order_status(order_id, "completed")
        queue.ack(message)  # Confirmação
        
    except TemporaryError as e:
        # Falha temporária (ex: timeout de rede)
        database.record_retry(order_id)
        queue.nack(message)  # Não confirma, permitindo retry
        
    except PermanentError as e:
        # Falha irreparável (ex: dados inválidos)
        database.set_order_status(order_id, "failed")
        queue.move_to_dlq(message)  # Manda para DLQ
```


## **Conclusão**  
A solução proposta garante **exactly-once processing** combinando:  
 - **Identificadores únicos** para cada pedido.  
 - **Banco de dados como fonte da verdade** para evitar duplicação.  
 - **Mecanismo de ACK/NACK** para retentativas automáticas.  
- **DLQ** para lidar com falhas persistentes.  

Essa abordagem é escalável, tolerante a falhas e adequada para sistemas de alta demanda, como e-commerce e processamento de pagamentos