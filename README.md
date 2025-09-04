# 📌 NeoApp Clientes API

## 📖 Visão Geral

Este projeto foi desenvolvido como parte do **desafio técnico da NeoApp** para a vaga de **Desenvolvedor Back-End Estagiário**.

A proposta consistiu em construir uma **REST API** para **cadastro e gestão de clientes pessoa física**, seguindo boas práticas de código, princípios do **SOLID** e utilizando tecnologias amplamente adotadas no mercado.

---

## 🚀 Tecnologias Utilizadas

* **Java 17** + **Spring Boot**
* **Spring Data JPA** (abstração da camada de persistência)
* **PostgreSQL** como banco de dados relacional
* **Docker** para conteinerização do banco
* **Spring Validation** para validação dos DTOs
* **Spring Security + JWT** para autenticação
* **JUnit** e **Mockito** para testes unitários
* **Lombok** para redução de boilerplate
* **Maven** para gerenciamento de dependências

---

## ⚙️ Funcionalidades Implementadas

✔️ Inclusão de novos clientes
✔️ Atualização de clientes existentes
✔️ Exclusão (hard delete) de clientes
✔️ Exclusão lógica (soft delete - alteração de status)
✔️ Listagem de clientes com **paginação e ordenação**
✔️ Busca por atributos cadastrais (nome, cpf, email, status) usando **Specifications**
✔️ Cálculo automático da **idade** a partir da data de nascimento
✔️ Validações nos DTOs de entrada (`@NotBlank`, `@Email`, `@Past`)
✔️ Segurança com **Spring Security e JWT**

---

## 📐 Decisões Arquiteturais e Boas Práticas

### 📌 Estrutura em Camadas

O projeto foi organizado em **camadas bem definidas**, seguindo o padrão arquitetural clássico:

* **Controller** → Responsável por expor os endpoints REST e tratar requisições.
* **Service** → Contém as regras de negócio e orquestra o fluxo entre camadas.
* **Repository** → Interface com o banco de dados, abstraída pelo Spring Data JPA.
* **DTOs** → Transferência de dados entre API e domínio, garantindo desacoplamento.
* **Mapper** → Conversão entre entidades e DTOs, garantindo separação de responsabilidades.

### 📌 Princípios SOLID

* **S (Single Responsibility Principle)** → Cada classe tem uma responsabilidade clara (ex: `CustomerMapper` só converte objetos).
* **O (Open/Closed Principle)** → Specifications permitem estender filtros sem modificar código existente.
* **L (Liskov Substitution Principle)** → Uso de abstrações (`ICustomerService`) garante que implementações possam ser trocadas sem quebra.
* **I (Interface Segregation Principle)** → Serviços expõem apenas métodos necessários.
* **D (Dependency Inversion Principle)** → Injeção de dependências do Spring garante baixo acoplamento.

### 📌 Padrões de Projeto

* **DTO Pattern** → Separação entre dados expostos e entidade de domínio.
* **Builder Pattern (Lombok @Builder)** → Facilita construção de objetos de resposta padronizados (`APIResponse`).
* **Specification Pattern** → Implementado para filtros dinâmicos de busca.

### 📌 Tratamento de exceções Centralizado

Para garantir consistência nas respostas de erro e manter os controllers limpos, foi implementado um `GlobalHandlerException`. Utilizando a anotação `@RestControllerAdvice`, esta classe intercepta exceções lançadas em qualquer camada da aplicação (como `ResourceNotFoundException` ou erros de validação do `jakarta.validation`) e as converte em respostas HTTP padronizadas, com o status code apropriado e um corpo de erro consistente.

**Benefícios desta abordagem:**
* **Clean Code** → Os controllers não precisam de blocos `try-catch`, focando apenas no fluxo de sucesso.
* **Consistência** → Todos os erros retornados pela API seguem a mesma estrutura JSON.
* **Manutenibilidade** → A lógica de tratamento de erros fica centralizada em um único local, facilitando futuras modificações.

---

## 🐳 Configuração com Docker

O banco de dados **PostgreSQL** roda em um container Docker para padronizar o ambiente.

```bash
docker run --name neoapp-postgres \
  -e POSTGRES_USER=neoapp \
  -e POSTGRES_PASSWORD=neoapp \
  -e POSTGRES_DB=neoappdb \
  -p 5432:5432 \
  -d postgres
```

---


## 🔍 Endpoints da API

A API está dividida em endpoints públicos para autenticação e endpoints privados para o gerenciamento de clientes. O fluxo de uso é: **1. Registrar e/ou Fazer Login** para obter um token JWT, e **2. Usar o Token** para acessar as funcionalidades de cliente.

### 🔑 Autenticação (Endpoints Públicos)

Estes endpoints são a porta de entrada para a API.

#### 1. Registrar um Novo Usuário

Cria um novo usuário no sistema que poderá se autenticar para obter um token.

- **Requisição:** `POST http://localhost:8080/register`
- **Descrição:** Registra um novo usuário e já retorna um token JWT para login imediato, facilitando o primeiro uso.
- **Corpo da Requisição (Request Body):**

   ```json
  {
      "name": "jhonatan",
      "email": "email@teste.com",
      "password": "Teste@123"
  }
   ````

#### 2\. Realizar Login

Autentica um usuário existente e retorna um JSON Web Token (JWT) para ser usado nas rotas protegidas.

- **Requisição:** `POST http://localhost:8080/login`

- **Descrição:** Valida as credenciais do usuário e gera um token de acesso.

- **Corpo da Requisição (Request Body):**

  ```json
  {
      "email": "email@teste.com",
      "password" : "Teste@123"
  }
  ```

- **Resposta de Sucesso (Success Response):**

  ```json
  {
    "status": "SUCCESS",
    "errors": null,
    "results": {
      "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWFpbEB0ZXN0ZS5jb20i..."
    }
  }
  ```

  > **Importante:** Copie o valor do campo `token` para usar nos endpoints de clientes.

-----

### 👤 Clientes (Endpoints Protegidos)

Todos os endpoints abaixo são protegidos. Para acessá-los, é **obrigatório** o envio do token JWT no cabeçalho (`Header`) de autorização.

- **Como se autenticar:** Em todas as requisições a seguir, inclua o cabeçalho:

  ```http
  Authorization: Bearer <SEU_TOKEN_JWT_GERADO_NO_LOGIN>
  ```

#### Criar um novo cliente

- **Requisição:** `POST http://localhost:8080/api/create`

- **Descrição:** Cadastra um novo cliente no banco de dados.

- **Corpo da Requisição (Request Body):**

  ```json
  {
      "name": "jhonatan",
      "cpf": "12345678920",
      "email": "test@email.com",
      "phone": "11912345678",
      "birthDate": "1996-04-08"
  }
  ```

- **Resposta:** Retorna o cliente recém-criado com status `201 Created` e o cabeçalho `Location` com a URL para o novo recurso.

#### Atualizar um cliente existente

- **Requisição:** `PUT http://localhost:8080/api/customer/1`
- **Descrição:** Atualiza os dados de um cliente existente a partir do seu `ID`.
- **Corpo da Requisição (Request Body):** (Não é necessário enviar o CPF)
  ```json
  {
    "name": "Jhonatan Atualizado",
    "email": "att@email.com",
    "phone": "+55 11 98765-4321",
    "birthDate": "1990-05-15"
  }
  ```

#### Listar clientes com paginação

- **Requisição:** `GET http://localhost:8080/api/customers`

- **Descrição:** Retorna uma lista paginada de todos os clientes.

- **Parâmetros de Query (Query Params) Opcionais:**

   - `page`: Número da página (começa em 0). Padrão: `0`.
   - `size`: Quantidade de itens por página. Padrão: `10`.
   - `sort`: Campo para ordenação e direção (`nomeCampo,asc` ou `nomeCampo,desc`). Padrão: `createdAt,desc`.

- **Exemplo de Requisição:**

  ```http
  GET http://localhost:8080/api/customers?page=0&size=5&sort=name,asc
  ```

#### Buscar clientes por filtros

- **Requisição:** `GET http://localhost:8080/api/customers/search`

- **Descrição:** Permite uma busca dinâmica por diferentes atributos do cliente.

- **Parâmetros de Query (Query Params) Opcionais:** `name`, `email`, `cpf`.

- **Exemplo de Requisição:**

  ```http
  GET http://localhost:8080/api/customers/search?name=jhonatan
  ```

#### Excluir um cliente (Soft Delete)

- **Requisição:** `PATCH http://localhost:8080/api/status/customer/1`
- **Descrição:** Altera o status do cliente para `INACTIVE`, mantendo o registro no banco. Esta é a forma recomendada de "exclusão".

#### Excluir um cliente permanentemente (Hard Delete)

- **Requisição:** `DELETE http://localhost:8080/api/customers/1`
- **Descrição:** Remove permanentemente o registro do cliente do banco de dados. Use com cuidado.




---

## 🧪 Testes

Foram implementados **testes unitários** com **JUnit 5 e Mockito**, cobrindo:

* Serviços de negócio (`CustomerServiceImpl`)
* Validação de regras (ex: não permitir CPF duplicado)
* Mapeamento entre DTO ↔ Entity

> O foco foi garantir a **confiabilidade das regras de negócio** e reduzir riscos de regressões.

---

## 🌐 Como Rodar o Projeto

### Pré-requisitos

* Java 17+
* Maven
* Docker

### Passos

1. Clonar repositório

   ```bash
   git clone https://github.com/seuusuario/neoapp-clientes-api.git
   ```
2. Subir banco de dados com Docker (comando acima)
3. Rodar a aplicação

   ```bash
   mvn spring-boot:run
   ```

---

## 🎯 Conclusão e Próximos Passos

Este projeto foi uma oportunidade de aplicar e demonstrar competências essenciais no desenvolvimento de software back-end, indo além do MVP solicitado no desafio. A API de gerenciamento de clientes foi construída sobre uma fundação sólida, priorizando não apenas a entrega das funcionalidades, mas também a **qualidade, manutenibilidade e escalabilidade** do código.

As decisões arquiteturais, como a **estrutura em camadas**, o uso de **padrões de projeto** como DTO e a aplicação dos **princípios SOLID**, foram escolhas deliberadas para garantir um sistema de baixo acoplamento e alta coesão. A segurança foi tratada como um requisito de primeira classe, com a implementação de autenticação e autorização via **Spring Security e JWT**.

Além dos requisitos fundamentais, a iniciativa de adicionar funcionalidades como **soft delete**, a conteinerização do banco de dados com **Docker** e a criação de **testes unitários** reflete um compromisso com as melhores práticas do mercado.

### Próximos Passos

Embora o projeto atual seja robusto, os próximos passos para evoluí-lo incluiriam:
* **Documentação com Swagger (OpenAPI):** Integrar a biblioteca `springdoc-openapi` para gerar uma documentação interativa e automatizada, facilitando o consumo da API.
* **Deploy em Nuvem:** Empacotar a aplicação em uma imagem Docker e realizar o deploy em um provedor de nuvem (como AWS, GCP ou Heroku).
* **Ampliar a Cobertura de Testes:** Desenvolver testes de integração para validar o fluxo completo, desde a requisição HTTP até a resposta, garantindo a integração entre todas as camadas.
* **Desenvolvimento de Front End em Angular:** Criar uma interface em Angular para consumir a API, proporcionando uma experiência intuitiva e completa.
* **Integração com SonarQube e JaCoCo:** Configurar Sonarqube para análise de código, garantindo métricas de qualidade e verificação de vulnerabilidades, integrar JaCoCo para cobertura de testes.
* **Implementação de Logs:** Adicionar logs para registrar ações pelo fluxo da aplicação.
Agradeço pela oportunidade de participar deste desafio e demonstrar minhas habilidades.

---
