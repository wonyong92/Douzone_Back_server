drop table if exists auth;
drop table if exists employee;

create table employee
(
    employee_id       varchar(30) not null
        primary key,
    name              varchar(30) null,
    employee_password varchar(30) null,
    email             varchar(40) null,
    manager           boolean     not null default false
);

create table auth
(
    employee_id varchar(30) not null,
    ip          varchar(30) not null,
    login_time  datetime    null,
    logout_time datetime    null,
    primary key (employee_id, ip),
    constraint auth_ibfk_1
        foreign key (employee_id) references employee (employee_id)
);
