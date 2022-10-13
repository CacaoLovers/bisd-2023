
create schema if not exists cw1;

set search_path to cw1;

drop table if exists product;
drop table if exists client;
drop table if exists orders;



create table product (

                         id bigserial primary key,
                         name varchar(30)


);

create table client (

                        id bigserial primary key,
                        password varchar(30),
                        first_name varchar(30)

);

create table orders (

                        id_client bigint,
                        id_product bigint,
                        time_order timestamp,
                        count int,
                        foreign key (id_client) references product(id),
                        foreign key (id_product) references client(id)

);