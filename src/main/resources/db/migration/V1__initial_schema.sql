create table if not exists students(
    id bigserial primary key,
    name varchar(100) not null,
    email varchar (100) not null,
    address varchar (100) not null,
    phone varchar (100) not null
);

create table courses(
    id bigserial primary key,
    name varchar(100) not null
);

create table students_courses(
    course_id bigint,
    student_id bigint
);
create table roles (
     id bigserial primary key,
     name varchar(100) not null
);

insert into roles values (1, 'admin');
insert into roles values (2, 'teacher');

CREATE SEQUENCE hibernate_sequence START 3;


create table staff (
    id bigserial primary key,
    name varchar(100) not null,
    email varchar (100) not null,
    address varchar (100) not null,
    phone varchar (100) not null
);

-- insert into staff values (1, 'Valera');

create table staff_roles(
    staff_id bigint,
    roles_id bigint
);

create table groups(
      id bigserial primary key,
      name varchar(100) not null,
      student varchar (100)
);

create table transactions (
    id int not null,
    date date not null
--     student_id int not null
);
