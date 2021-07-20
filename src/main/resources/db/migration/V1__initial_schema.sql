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

create table groups(
      id bigserial primary key,
      name varchar(100) not null,
      student varchar (100)
);

create table staff (
    id bigserial primary key,
    name varchar(100) not null
);

insert into staff values (1, 'Valera');


create table transactions (
    id int not null,
    date date not null
--     student_id int not null
);
create table role (
    id int primary key,
    name varchar(100) not null
);

insert into role values (1, 'admin');
