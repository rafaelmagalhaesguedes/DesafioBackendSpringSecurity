# Desafio Backend - Spring Security + JWT

O desafio consiste em criar um microsserviço de autenticação e autorização de usuários em um sistema, integrando o Spring Security com JWT para gerenciamento de tokens. Foram implementados mecanismos de criptografia para proteger dados sensíveis e utilizada herança entre as classes para organizar e compartilhar comportamentos entre os diferentes tipos de usuários: User, Admin e Customer, cada um com permissões distintas.

## Tecnologias

- Java 17
- Spring Boot
- Spring Security
- JWT (Json Web Token)
- Swagger
- Postgres
- Docker
- NGNIX

## Uso

1. Clone o projeto para sua máquina
2. Na pasta raiz, suba os serviços com Docker:

    ```
    docker compose up -d --build
    ```
   
3. A aplicação estará disponível em http://localhost

## Documentação

O projeto está documentado com Swagger, acesse o [link aqui](http://localhost/swagger-ui/index.html#/).

### Developed By

Rafa Guedes

