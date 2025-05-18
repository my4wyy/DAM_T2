# **Exercício 5: Web Services (SOAP vs REST vs GraphQL)**  


## **1. API REST para Sistema de Biblioteca**  

### **Endpoints Principais**  

| **Recurso**       | **Método HTTP** | **Endpoint**                     | **Descrição**                                  |
|-------------------|----------------|----------------------------------|-----------------------------------------------|
| Livros            | GET            | `/livros`                        | Lista todos os livros                         |
| Livro por ID      | GET            | `/livros/{id}`                   | Detalhes de um livro específico               |
| Adicionar Livro   | POST           | `/livros`                        | Cadastra um novo livro                        |
| Atualizar Livro   | PUT            | `/livros/{id}`                   | Atualiza informações de um livro              |
| Deletar Livro     | DELETE         | `/livros/{id}`                   | Remove um livro                               |
| Autores           | GET            | `/autores`                       | Lista todos os autores                        |
| Empréstimos       | GET            | `/usuarios/{id}/emprestimos`     | Lista empréstimos ativos de um usuário        |

### **Exemplo de Request/Response**  
**GET `/livros/123`**  
```json
{
  "id": 123,
  "titulo": "Clean Code",
  "autor": {
    "id": 456,
    "nome": "Robert C. Martin"
  },
  "genero": "Programação",
  "disponivel": false
}
```


## **2. API GraphQL para o Mesmo Sistema**  

### **Schema (Tipos, Queries, Mutations)**  
```graphql
type Livro {
  id: ID!
  titulo: String!
  autor: Autor!
  genero: String!
  disponivel: Boolean!
}

type Autor {
  id: ID!
  nome: String!
  livros: [Livro!]!
}

type Emprestimo {
  id: ID!
  usuarioId: ID!
  livro: Livro!
  dataEmprestimo: String!
  dataDevolucao: String
}

type Query {
  livro(id: ID!): Livro
  livrosPorGenero(genero: String!, ordenarPorPopularidade: Boolean): [Livro!]!
  emprestimosAtivos(usuarioId: ID!): [Emprestimo!]!
}

type Mutation {
  adicionarLivro(titulo: String!, autorId: ID!, genero: String!): Livro!
}
```

### **Exemplo de Query**  
```graphql
query {
  livro(id: "123") {
    titulo
    autor {
      nome
    }
  }
}
```


## **3. Comparação REST vs GraphQL**  

| **Caso de Uso**                                      | **REST**                                                                 | **GraphQL**                                                                 |
|-------------------------------------------------------|--------------------------------------------------------------------------|-----------------------------------------------------------------------------|
| **Obter livro + autor**                               | Requisição separada ou nested response (overfetching)                    | Uma única query com apenas os campos necessários                            |
| **Listar empréstimos ativos com detalhes dos livros** | Múltiplas chamadas (`/usuarios/123/emprestimos` + `/livros/{id}` para cada) | Consulta aninhada em uma única requisição                                   |
| **Buscar livros por gênero + ordenação**              | Query params (`/livros?genero=ficcao&sort=popularidade`)                 | Query flexível com filtros e ordenação no schema                            |

**Conclusão:**  
 - **REST** é mais simples para casos básicos, mas pode levar a **overfetching** ou **underfetching**.  
 - **GraphQL** é mais eficiente para consultas complexas e evita múltiplas requisições.  