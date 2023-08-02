Jornada Milhas - Challenge Back-End 7 Alura: API REST com Spring Boot
==========================

<!-- TOC -->
* [Jornada Milhas - Challenge Back-End 7 Alura: API REST com Spring Boot](#jornada-milhas---challenge-back-end-7-alura-api-rest-com-spring-boot)
* [👓 Descrição](#-descrição)
* [📖 API](#-api)
  * [`/depoimentos`](#depoimentos)
  * [`/depoimentos-home`](#depoimentos-home)
* [🗓️ Semanas](#-semanas)
  * [Primeira Semana](#primeira-semana-)
    * [Primeiro passo](#primeiro-passo)
    * [Segundo passo](#segundo-passo)
    * [Terceiro passo](#terceiro-passo)
    * [Quarto passo](#quarto-passo)
  * [Segunda Semana](#segunda-semana)
  * [Terceira Semana](#terceira-semana)
    * [Docker](#docker)
      * [Criação de `Docker network`](#criação-de-docker-network)
      * [Subir  Postgres](#subir--postgres-)
      * [Subir Aplicação](#subir-aplicação)
<!-- TOC -->

# 👓 Descrição

![status_desenvolvimento](https://img.shields.io/static/v1?label=Status&message=Em%20Desenvolvimento&color=yellow&style=for-the-badge)
![Badge Java](https://img.shields.io/static/v1?label=Java&message=17&color=orange&style=for-the-badge&logo=java)

![framework_back](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)
![server_ci](https://img.shields.io/badge/Github%20Actions-282a2e?style=for-the-badge&logo=githubactions&logoColor=367cfe)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)

![example workflow](https://github.com/vsantsal/alura-challenge-backend-7/actions/workflows/maven.yml/badge.svg)
![Coverage](.github/badges/jacoco.svg)

O desafio é a construção de API para "disponibilizar informações e recursos do banco de dados relacionados a possíveis destinos de viagem, exibindo fotos, textos e também recursos sobre depoimentos de outras pessoas viajantes", conforme exposto na [página do desafio](https://www.alura.com.br/challenges/back-end-7).

Utilizaremos o *framework* Spring Boot, com Postgres como SGBD.

# 📖 API

## `/depoimentos`

O *endpoint* permite cadastrar depoimentos de verbo **POST**, passando na requisição um *JSON* como ilustrado abaixo:

`POST /depoimentos`

*Requisição*
```json
{
  "depoente": "Marco Polo",
  "depoimento": "Fui e não gostei. Jamais voltaria. Arrependido.",
  "url_foto": "https://www.minhaimageminsatisfeita.com"
}
```

No cadastro, os campos `depoente` e `depoimento` são obrigatórios, ao passo que pode ou não se informar `url_foto`.

Também é possível buscar depoimentos por (opcionalmente) `depoente` e `depoimento`, assim como remover. Também apresentamos exemplos das chamadas na sequência.

`GET /depoimentos?depoente=juninho`

*Resposta*
```json
[
  {
    "id": 42,
    "depoente": "juninho",
    "depoimento": "Muito bacana!",
    "url_foto": null
  },
  {
    "id": 101,
    "depoente": "juninho",
    "depoimento": "Não gostei!",
    "url_foto": "https://www.imagemparamostrarquaoruimfoi.com"
  }  
]
```

`GET /depoimentos`

*Resposta*
```json
[
  { "id": 1,
    "depoente": "Marco Polo",
    "depoimento": "Fui e não gostei. Jamais voltaria. Arrependido.",
    "url_foto": "https://www.minhaimageminsatisfeita.com"
  },  
  {
    "id": 42,
    "depoente": "juninho",
    "depoimento": "Muito bacana!",
    "url_foto": null
  },
  {
    "id": 101,
    "depoente": "juninho",
    "depoimento": "Não gostei!",
    "url_foto": "https://www.imagemparamostrarquaoruimfoi.com"
  }  
]
```

`DELETE /depoimentos/1`

*Resposta*

```json
```

Por fim, é possível atualizar `url_foto` e `depoimento` (o campo `depoente` porém não aceita alterações).

`PUT /depoimentos`

*Requisição*
```json
{
  "id": 1,
  "depoente": "Marco Polo 2",
  "depoimento": "Mudei de ideia. Voltaria amanhã.",
  "url_foto": "https://www.minhaimagemsatisfeita.com"
}
```

*Resposta*
```json
{
  "id": 1,
  "depoente": "Marco Polo",
  "depoimento": "Mudei de ideia. Voltaria amanhã.",
  "url_foto": "https://www.minhaimagemsatisfeita.com"
}
```

Erros de requisição são tratados pela aplicação.

## `/depoimentos-home`

Como *endpoint* adicional, a API possibilita, ao se chamar o recurso, consultar 3 depoimentos aleatoriamente.

`GET /depoimentos-home`

*Resposta*
```json
[
  { "id": 1,
    "depoente": "Marco Polo",
    "depoimento": "Fui e não gostei. Jamais voltaria. Arrependido.",
    "url_foto": "https://www.minhaimageminsatisfeita.com"
  },
  ,
  {
    "id": 101,
    "depoente": "juninho",
    "depoimento": "Não gostei!",
    "url_foto": "https://www.imagemparamostrarquaoruimfoi.com"
  },
  {
    "id": 42,
    "depoente": "juninho",
    "depoimento": "Muito bacana!",
    "url_foto": null
  }
]
```

# 🗓️ Semanas

## Primeira Semana 

### Primeiro passo

Começamos o projeto fazendo o CRUD para Depoimentos, que consistem de: `Foto`, `Depoimento` e `Nome da pessoa que fez o depoimento`.

Desde o início, realizamos testes Spring Boot para guiar o desenvolvimento do `controller`, validações e restrições.

Adicionalmente, utilizamos `flyway` para versionamento de scripts de banco de dados.

Configuramos o `Actions` para execução dos testes conforme integrações ao ramo principal ocorrerem.

### Segundo passo

Na sequência, desenvolveremos o *endpoint* `depoimentos-home`, que deverá retornar "depoimentos de 3 pessoas de forma randômica".

Previamente, acrescentamos ao projeto dependências para geração de documentação automática por *swagger*, a qual pode ser conferida nos *endpoints* habituais `/v3/api-docs` e `/swagger-ui.html`.

Para cumprir a regra de negócio, utilizamos `JPQL` na interface `DepoimentosRepository` - e testamos através de classe de teste anotada por `@DataJpaTest`.

### Terceiro passo

Para liberar o Cors, utilizamos como solução criar uma subclasse de `OncePerRequestFilter` do Spring Framework.

### Quarto passo

Testes de *status code* já foram realizados durante o desenvolvimento no primeiro passo. Como desafio, adicionamos aqui dependência `jacoco` para mensurar cobertura de código pelos testes e também configuração do Github para exibir a badge no README.md.

## Segunda Semana

Na segunda semana, o foco é a criação *endpoint* `/destinos`, baseado no recurso `Destino`, com os seguintes dados:

* `Foto`
* `Nome`
* `Preco`

## Terceira Semana

### Docker

Previamente às atividades da semana, incluímos no projeto `Dockerfile` de modo a se rodar a aplicação em container `Docker`.

Para utilizar a imagem no ambiente, basta executar o comando abaixo (no diretório onde o arquivo se localiza):

```
docker build -t minhatag:latest
```

Dado que a aplicação utiliza banco de dados Postgres, será necessário também executar outro contêiner a partir de imagem para ele - e realizar a comunicação entre os contêineres da aplicação e do banco de dados.

```
docker pull postgres
```

#### Criação de `Docker network`

Para criar uma network a ser utilizada na comunicação entre os contêineres de aplicação e banco de dados, pode-se usar o comando abaixo:

```
docker network create redeminha
```

#### Subir  Postgres 

Pode-se executar da primeira vez:

```
docker run -p 5432:5432 --name basedados --network redeminha -e POSTGRES_PASSWORD=password -d postgres
```

Posteriormente, usar `docker start CONTEINER_ID` e `docker stop CONTEINER_ID`.

#### Subir Aplicação

Para levantar o contêiner da aplicação, pode-se executar da primeira vez:

```
docker run -p 8080:8080 --name aplicacao-spring --network redeminha -e POSTGRES_URL=basedados -e POSTGRES_USERNAME=postgres -e POSTGRES_PASSWORD=password -d minhatag:latest
```

Posteriormente, usar `docker start CONTEINER_ID` e `docker stop CONTEINER_ID`.
