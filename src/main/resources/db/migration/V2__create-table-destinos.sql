-- Postgres
-- pré-requisitos:
--  1. criação manual da base de dados na máquina local e do schema `challenge` nesse database
--  2. Usuário da aplicação com os privilégios de acesso e modificação adequados
-- e do schema challenge;

-- setando search_path
SET search_path TO challenge;

-- criação de tabela
drop table if exists "challenge".cd_destinos;
create table "challenge".cd_destinos(
    id serial primary key,
    nome varchar(120) not null,
    preco_em_centavos bigint not null,
    url_foto varchar(255)
);
