create schema if not exists sem_1;
set search_path to sem_1;

drop table if exists notification cascade;
drop table if exists missing cascade;
drop table if exists volunteer cascade;
drop table if exists district cascade;
drop table if exists city cascade;
drop table if exists users cascade;

create table users (

    id bigserial primary key,
    login varchar(30) unique,
    first_name varchar(30),
    last_name varchar(30),
    password varchar(32),
    city varchar(30),
    mail varchar(50),
    phone_number varchar(11),
    last_session varchar(32),
    role varchar(30)

);

create table city (

    name varchar(30) unique,
    pos_center_x numeric,
    pos_center_y numeric

);

create table district (

    name varchar(30),
    city varchar(30),
    foreign key (city) references city(name) on update cascade on delete cascade,
    primary key (name, city)

);


create table missing (

    id bigserial primary key,
    id_owner bigint,
    name varchar(30),
    description text,
    pos_x numeric,
    pos_y numeric,
    city varchar(150),
    street varchar(150),
    district varchar(150),
    date date,
    path_image varchar(30),
    status varchar(10) check (status in ('active', 'closed')),
    side varchar(10) check ( side in ('lost', 'found') ),
    foreign key (id_owner) references users (id) on update cascade on delete cascade,
    foreign key (district, city) references district (name, city) on update cascade on delete cascade

);

create table volunteer (

    id_user bigint,
    district varchar(50),
    city varchar(50),
    foreign key (id_user) references users(id) on update cascade on delete cascade,
    foreign key (district, city) references district (name, city) on update cascade on delete cascade

);


create table notification (
    id_user_from bigint,
    id_user_to bigint,
    id_missing bigint,
    type varchar(50),
    status varchar(50),
    foreign key (id_user_from) references users(id) on delete cascade on update cascade,
    foreign key (id_user_to) references users(id) on delete cascade on update cascade,
    foreign key (id_missing) references missing(id) on delete cascade on update cascade
);


