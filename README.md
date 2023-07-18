Challenge Back-End 7 Alura: API REST com Spring Boot
==========================

<!-- TOC -->
* [Challenge Back-End 7 Alura: API REST com Spring Boot](#challenge-back-end-7-alura-api-rest-com-spring-boot)
* [Descrição](#descrição)
* [Semanas](#semanas)
  * [Primeira Semana](#primeira-semana-)
    * [Primeiro passo](#primeiro-passo)
<!-- TOC -->

# Descrição

![framework_back](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)
![server_ci](https://img.shields.io/badge/Github%20Actions-282a2e?style=for-the-badge&logo=githubactions&logoColor=367cfe)
![example workflow](https://github.com/vsantsal/alura-challenge-backend-7/actions/workflows/maven.yml/badge.svg)

Desafio é a construção de API para "disponibilizar informações e recursos do banco de dados relacionados a possíveis destinos de viagem, exibindo fotos, textos e também recursos sobre depoimentos de outras pessoas viajantes", conforme exposto na [página do desafio](https://www.alura.com.br/challenges/back-end-7).

Utilizaremos o *framework* Spring Boot, com Postgres como SGBD.

# Semanas

## Primeira Semana 

### Primeiro passo

Começamos o projeto fazendo o CRUD para Depoimentos, que consistem de: `Foto`, `Depoimento` e `Nome da pessoa que fez o depoimento`.

Desde o início, realizamos testes Spring Boot para guiar o desenvolvimento do `controller`, validações e restrições.

Adicionalmente, utilizamos `flyway` para versionamento de scripts de banco de dados.

Configuramos o `Actions` para execução dos testes conforme integrações ao ramo principal ocorrerem.

### Segundo passo

Na sequência, desenvolveremos o *endpoint* `depoimentos-home`, que deverá retornar "depoimentos de 3 pessoas de forma randômica".

Previamente, acrescentaremos ao projeto dependências para geração de documentação automática por *swagger*, a qual pode ser conferida nos *endpoints* habituais `/v3/api-docs` e `/swagger-ui.html`.