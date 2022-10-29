create schema if not exists hw3;
set search_path to hw3;


drop table if exists guest cascade;
drop table if exists desk cascade;
drop table if exists booking cascade;

drop type if exists placement;
create type placement as enum ('VIP-zone', 'veranda', 'lounge');

create table guest (

    id bigserial primary key,
    first_name varchar(30),
    last_name varchar(30),
    phone_number varchar(11),
    comment text

);



create table desk (

    number int primary key,
    capacity int,
    place varchar default 'lounge',
    description text

);


create table booking(

    id_guest bigint,
    id_table int,
    time timestamptz,
    duration integer,
    number_person int,
--     primary key (id_guest, id_table),
    foreign key (id_guest) references guest(id) on update cascade on delete cascade ,
    foreign key (id_table) references desk(number)  on update cascade on delete cascade
--     unique (id_guest, id_table)

);

