Exercício 6: Serverless e FaaS (Processamento de Imagens)

Solução AWS Serverless

Componentes
1. Amazon S3 (Bucket de Armazenamento)
   - Recebe uploads de imagens.
   - Dispara evento para Lambda quando um arquivo é adicionado.

2. AWS Lambda (Função de Processamento)
   - Redimensiona a imagem em múltiplos tamanhos.
   - Gera thumbnail para imagens grandes.
   - Extrai metadados (resolução, formato, tamanho).

3. Amazon DynamoDB (Banco de Metadados)
   - Armazena:
     - Caminho da imagem original e versões redimensionadas.
     - Metadados (timestamp, dimensões, formato).

Fluxo de Processamento
1. Usuário faz upload para bucket-origem.
2. S3 dispara evento para Lambda.
3. Lambda processa a imagem:
   - Gera versões em 200x200, 500x500, 1000x1000.
   - Cria thumbnail 100x100 se imagem >5MB.
4. Salva metadados no DynamoDB.
5. Armazena resultados em bucket-processado.

Escalabilidade
- Lambda escala automaticamente para cada novo upload.
- S3 suporta milhões de requisições simultâneas.
- DynamoDB escala horizontalmente com aumento de carga.
