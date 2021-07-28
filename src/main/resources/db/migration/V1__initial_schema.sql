create table if not exists user (
    id bigserial primary key,
    name varchar(100) not null,
    email varchar (100) not null,
    address varchar (100) not null,
    phone varchar (100) not null
);

create table if not exists courses (
    id bigserial primary key,
    name varchar(100) not null
);

create table if not exists students_courses (
    course_id bigint,
    student_id bigint
);

create table if not exists roles (
     id bigserial primary key,
     name varchar(100) not null
);

insert into roles values (1, 'ADMIN');

create table if not exists staff (
    id bigserial primary key,
    name varchar(100) not null,
    email varchar (100) not null,
    address varchar (100) not null,
    phone varchar (100) not null,
    password varchar (100) not null
);
insert into staff values (1, 'Valera_Admin', 'vradmin@gmail.com', 'Big street', '2656412452', '123');

CREATE SEQUENCE hibernate_sequence START 2;

create table if not exists staff_roles (
    staff_id bigint,
    roles_id bigint
);
insert into staff_roles values (1 , 1);

create table if not exists payments (
    id bigserial not null,
    local_date_time timestamp not null default current_timestamp
);
