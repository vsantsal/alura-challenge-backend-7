-- criação de tabela
drop table if exists cd_depoimentos;
create table cd_depoimentos(
    id serial primary key,
    depoente varchar(120) not null,
    depoimento varchar(500) not null,
    url_foto varchar(255)
);
