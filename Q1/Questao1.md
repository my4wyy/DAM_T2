## Abordagem de Microsserviços
Nessa arquitetura, o sistema é decomposto em serviços independentes, cada um com uma responsabilidade bem definida. O serviço de catálogo gerencia metadados de vídeos, enquanto o serviço de streaming lida com a entrega de mídia, adaptando bitrates e integrando-se a CDNs. A autenticação e autorização são tratadas por um serviço dedicado, garantindo segurança com tokens JWT ou OAuth. O serviço de recomendações utiliza algoritmos personalizados, e o serviço de pagamentos processa assinaturas de forma isolada.  

A comunicação entre microsserviços ocorre via APIs REST, gRPC ou mensageria (Kafka, RabbitMQ). Bancos de dados especializados (MongoDB para catálogo, Redis para cache) são comuns, e a orquestração é feita com Kubernetes e Docker.  

## Abordagem Cliente-Servidor Tradicional
Aqui, o sistema opera como um monolito, com um único backend centralizado que gerencia todas as funcionalidades. O frontend (app, web ou TV) consome uma API única, que trata desde autenticação até streaming e pagamentos. O banco de dados é centralizado (PostgreSQL, MySQL), e a lógica de negócios está acoplada em um mesmo código.  

Tecnologias como Node.js, Django ou Spring Boot são usadas no backend, enquanto o streaming é entregue via servidores como Nginx (RTMP) ou FFmpeg para transcodificação.  

## Diferenças

1. Escalabilidade
   - Microsserviços permitem escalar serviços individualmente (ex.: mais réplicas do serviço de streaming sob carga).  
   - Cliente-servidor tradicional escala verticalmente (mais CPU/RAM) ou via réplicas do monolito, o que é menos eficiente.  

2. Manutenção
   - Microsserviços exigem CI/CD independente e monitoramento distribuído, aumentando complexidade.  
   - Sistemas monolíticos são mais simples de manter inicialmente, mas atualizações podem ser arriscadas (efeito cascata).  

3. Resiliência 
   - Em microsserviços, falhas são isoladas (ex.: serviço de pagamentos pode cair sem afetar o streaming).  
   - No modelo tradicional, uma falha no backend derruba toda a aplicação.  

4. Desempenho e Custo
   - Microsserviços introduzem latência na comunicação entre serviços, além de custo mais alto (infraestrutura distribuída).  
   - Cliente-servidor tradicional tem menor latência e custo inicial, mas pode enfrentrar gargalos conforme cresce.  
