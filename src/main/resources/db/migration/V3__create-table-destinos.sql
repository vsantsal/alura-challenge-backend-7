-- criação de tabela
drop table if exists cd_destinos;
create table cd_destinos(
    id serial primary key,
    nome varchar(120) not null,
    preco_em_centavos bigint not null,
    url_foto varchar(255)
);
