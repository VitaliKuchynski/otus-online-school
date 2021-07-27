create table if not exists students (
    id bigserial primary key,
    name varchar(100) not null,
    email varchar (100) not null,
    address varchar (100) not null,
    phone varchar (100) not null
);

create table courses (
    id bigserial primary key,
    name varchar(100) not null
);

create table students_courses (
    course_id bigint,
    student_id bigint
);

create table roles (
     id bigserial primary key,
     name varchar(100) not null
);

insert into roles values (1, 'ADMIN');

create table staff (
    id bigserial primary key,
    name varchar(100) not null,
    email varchar (100) not null,
    address varchar (100) not null,
    phone varchar (100) not null
);
insert into staff values (1, 'Valera_Admin', 'vradmin@gmail.com', 'Big street', '2656412452' );

CREATE SEQUENCE hibernate_sequence START 2;

create table staff_roles (
    staff_id bigint,
    roles_id bigint
);
insert into staff_roles values (1 , 1);

create table payments (
    id bigserial not null,
    local_date_time timestamp not null default current_timestamp
);
