create database if not exists douzon_test;
use douzone_test;

drop table if exists notification_message;
drop table if exists auth;
drop table if exists attendance_appeal_request;
drop table if exists attendance_appeal_request_status;
drop table if exists attendance_approval;
drop table if exists attendance_info;
drop table if exists attendance_status_category;
drop table if exists image;
drop table if exists vacation_adjusted_history;
drop table if exists vacation_request;
drop table if exists vacation_category;
drop table if exists vacation_quantity_setting;
drop table if exists regular_time_adjustment_history;
drop table if exists admin;
drop table if exists employee;
drop table if exists vacation_request_state_category;

drop table if exists back_notification_message;
drop table if exists back_auth;
drop table if exists back_attendance_appeal_request;
drop table if exists back_attendance_appeal_request_status;
drop table if exists back_attendance_approval;
drop table if exists back_attendance_info;
drop table if exists back_attendance_status_category;
drop table if exists back_image;
drop table if exists back_vacation_adjusted_history;
drop table if exists back_vacation_request;
drop table if exists back_vacation_category;
drop table if exists back_vacation_quantity_setting;
drop table if exists back_regular_time_adjustment_history;
drop table if exists back_admin;
drop table if exists back_employee;
drop table if exists back_vacation_request_state_category;

create table if not exists  admin
(
    admin_id varchar(100)not null
        primary key,
    password varchar(100)not null
);

create table if not exists attendance_status_category
(
    `key` varchar(100) not null
        primary key
) default character set utf8
  collate utf8_general_ci;

create table if not exists employee
(
    employee_id        varchar(100)not null
        primary key,
    password           varchar(100)not null,
    name               varchar(100)not null,
    attendance_manager boolean     not null,
    hire_year          date        not null
) default character set utf8
  collate utf8_general_ci;
create table if not exists back_employee
(
    employee_id        varchar(100)not null
        primary key,
    password           varchar(100)not null,
    name               varchar(100)not null,
    attendance_manager boolean     not null,
    hire_year          date        not null
) default character set utf8
  collate utf8_general_ci;

CREATE TABLE attendance_info
(
    attendance_info_id         BIGINT      NOT NULL AUTO_INCREMENT,
    attendance_status_category VARCHAR(100) NOT NULL default 'pending',
    employee_id                VARCHAR(100)NOT NULL,
    start_time                 timestamp,
    end_time                   timestamp,
    attendance_date            DATE                 DEFAULT (CURRENT_DATE) NOT NULL,
    PRIMARY KEY (employee_id, attendance_date),
    UNIQUE (attendance_info_id),
    CONSTRAINT attendance_info_ibfk_1 FOREIGN KEY (employee_id) REFERENCES employee (employee_id) on delete cascade ,
    CONSTRAINT attendance_info_ibfk_2 FOREIGN KEY (attendance_status_category) REFERENCES attendance_status_category (`key`) on delete cascade
) default character set utf8
  collate utf8_general_ci;
CREATE TABLE back_attendance_info
(
    attendance_info_id         BIGINT      NOT NULL primary key AUTO_INCREMENT,
    attendance_status_category VARCHAR(100) NOT NULL default 'pending',
    employee_id                VARCHAR(100)NOT NULL,
    start_time                 timestamp,
    end_time                   timestamp,
    attendance_date            DATE                 DEFAULT (CURRENT_DATE) NOT NULL

) default character set utf8
  collate utf8_general_ci;

create table if not exists attendance_appeal_request_status
(
    attendance_appeal_request_status_key varchar(100) not null primary key
) default character set utf8
  collate utf8_general_ci;

create table if not exists attendance_appeal_request
(
    attendance_appeal_request_id   bigint                    not null
        primary key auto_increment,
    status                         varchar(100)              not null,
    reason                         text                      not null,
    attendance_info_id             bigint                    not null,
    appealed_start_time            time                      not null,
    appealed_end_time              time                      not null,
    employee_id                    varchar(100)              not null,
    attendance_appeal_request_time timestamp default (now()) not null,
    reason_for_rejection           text,
    constraint attendance_appeal_request_ibfk_1
        foreign key (attendance_info_id) references attendance_info (attendance_info_id) on delete cascade,
    constraint attendance_appeal_request_ibfk_2
        FOREIGN KEY (employee_id) REFERENCES employee (employee_id) on delete cascade,
    constraint attendance_appeal_request_ibfk_3
        foreign key (status) references attendance_appeal_request_status (attendance_appeal_request_status_key)
) default character set utf8
  collate utf8_general_ci;
create table if not exists back_attendance_appeal_request
(
    attendance_appeal_request_id   bigint                    not null
        primary key auto_increment,
    status                         varchar(100)              not null,
    reason                         text                      not null,
    attendance_info_id             bigint                    not null,
    appealed_start_time            time                      not null,
    appealed_end_time              time                      not null,
    employee_id                    varchar(100)              not null,
    attendance_appeal_request_time timestamp default (now()) not null,
    reason_for_rejection           text

) default character set utf8
  collate utf8_general_ci;

create table if not exists attendance_approval
(
    attendance_approval_id   bigint auto_increment
        primary key,
    attendance_info_id       bigint      not null,
    attendance_approval_date timestamp   not null,
    employee_id              varchar(100)not null,
    constraint attendance_approval_unique
        unique (attendance_info_id, employee_id),
    constraint attendance_approval_ibfk_1
        foreign key (attendance_info_id) references attendance_info (attendance_info_id) on delete cascade,
    constraint attendance_approval_ibfk_2
        FOREIGN KEY (employee_id) REFERENCES employee (employee_id) on delete cascade
) default character set utf8
  collate utf8_general_ci;
create table if not exists back_attendance_approval
(
    attendance_approval_id   bigint auto_increment
        primary key,
    attendance_info_id       bigint      not null,
    attendance_approval_date timestamp   not null,
    employee_id              varchar(100)not null

) default character set utf8
  collate utf8_general_ci;

create table if not exists image
(
    file_id     bigint                    not null
        primary key auto_increment,
    identifier varchar(100)              not null,
    file_name   varchar(255)              not null,
    uuid        varchar(255)              not null,
    upload_date timestamp default (now()) not null,
    constraint image_ibfk_1
        FOREIGN KEY (identifier) REFERENCES employee (employee_id) on delete cascade
) default character set utf8
  collate utf8_general_ci;

create table if not exists image_appeal
(
    file_id     bigint                    not null
        primary key auto_increment,
    identifier varchar(100)              not null,
    file_name   varchar(255)              not null,
    uuid        varchar(255)              not null,
    upload_date timestamp default (now()) not null

) default character set utf8
  collate utf8_general_ci;


create table if not exists vacation_adjusted_history
(
    vacation_adjusted_history_id bigint                  not null
        primary key auto_increment,
    employee_id                  varchar(100)            null,
    adjust_type                  varchar(100)            not null,
    adjust_time                  timestamp default now() not null,
    adjust_quantity              int                     not null,
    reason                       text                    not null,
    constraint vacation_adjusted_history_ibfk_1
        FOREIGN KEY (employee_id) REFERENCES employee (employee_id) on delete cascade
) default character set utf8
  collate utf8_general_ci;
create table if not exists back_vacation_adjusted_history
(
    vacation_adjusted_history_id bigint                  not null
        primary key auto_increment,
    employee_id                  varchar(100)            null,
    adjust_type                  varchar(100)            not null,
    adjust_time                  timestamp default now() not null,
    adjust_quantity              int                     not null,
    reason                       text                    not null

) default character set utf8
  collate utf8_general_ci;

create table if not exists vacation_category
(
    vacation_category_key varchar(100) not null
        primary key,
    admit_time            int          not null
) default character set utf8
  collate utf8_general_ci;

create table if not exists vacation_quantity_setting
(
    setting_key  int                                                            not null
        primary key auto_increment,
    freshman     int                                                            not null,
    senior       int                                                            not null,
    setting_time timestamp default Now()                                        not null,
    target_date  DATE DEFAULT (CONCAT(YEAR(NOW()) + 1, '-01-01')) NOT NULL,
    employee_id  varchar(100)                                                   not null,
    constraint vacation_quantity_setting_ibfk_1
        FOREIGN KEY (employee_id) REFERENCES employee (employee_id) on delete cascade
) default character set utf8
  collate utf8_general_ci;
create table if not exists back_vacation_quantity_setting
(
    setting_key  int                                                            not null
        primary key auto_increment,
    freshman     int                                                            not null,
    senior       int                                                            not null,
    setting_time timestamp default Now()                                        not null,
    target_date  DATE DEFAULT (CONCAT(YEAR(NOW()) + 1, '-01-01')) NOT NULL,
    employee_id  varchar(100)                                                   not null

) default character set utf8
  collate utf8_general_ci;

create table if not exists vacation_request_state_category
(
    vacation_request_state_category_key varchar(100)not null primary key
) default character set utf8
  collate utf8_general_ci;

create table if not exists vacation_request
(
    vacation_request_key                bigint       not null
        primary key auto_increment,
    vacation_category_key               varchar(100) not null,
    employee_id                         varchar(100) not null,
    vacation_request_state_category_key varchar(100),
    vacation_quantity                   int          not null,
    vacation_start_date                 date         not null,
    vacation_end_date                   date         not null,
    reason                              text         not null,
    vacation_request_time               timestamp default (now()),
    reason_for_rejection                text,
    constraint vacation_request_ibfk_1
        foreign key (vacation_category_key) references vacation_category (vacation_category_key),
    constraint vacation_request_ibfk_2
        FOREIGN KEY (employee_id) REFERENCES employee (employee_id) on delete cascade,
    constraint vacation_request_ibfk_3
        foreign key (vacation_request_state_category_key) references vacation_request_state_category (vacation_request_state_category_key)
) default character set utf8
  collate utf8_general_ci;
create table if not exists back_vacation_request
(
    vacation_request_key                bigint       not null
        primary key auto_increment,
    vacation_category_key               varchar(100) not null,
    employee_id                         varchar(100) not null,
    vacation_request_state_category_key varchar(100),
    vacation_quantity                   int          not null,
    vacation_start_date                 date         not null,
    vacation_end_date                   date         not null,
    reason                              text         not null,
    vacation_request_time               timestamp default (now()),
    reason_for_rejection                text

) default character set utf8
  collate utf8_general_ci;


CREATE TABLE `regular_time_adjustment_history`
(
    `regular_time_adjustment_history_id` BIGINT      NOT NULL PRIMARY KEY auto_increment,
    `target_date`                        DATE        NOT NULL DEFAULT (DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 1 DAY)),
    `adjusted_start_time`                TIME        NOT NULL,
    `adjusted_end_time`                  TIME        NOT NULL,
    `reason`                             TEXT        NOT NULL,
    `regular_time_adjustment_time`       timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `employee_id`                        varchar(100)not null,
    constraint regular_time_adjustment_history_ibfk_1
        FOREIGN KEY (employee_id) REFERENCES employee (employee_id) on delete cascade
) default character set utf8
  collate utf8_general_ci;
CREATE TABLE `back_regular_time_adjustment_history`
(
    `regular_time_adjustment_history_id` BIGINT      NOT NULL PRIMARY KEY auto_increment,
    `target_date`                        DATE        NOT NULL DEFAULT (DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 1 DAY)),
    `adjusted_start_time`                TIME        NOT NULL,
    `adjusted_end_time`                  TIME        NOT NULL,
    `reason`                             TEXT        NOT NULL,
    `regular_time_adjustment_time`       timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `employee_id`                        varchar(100)not null

) default character set utf8
  collate utf8_general_ci;

create table if not exists `auth`
(
    login_id    varchar(30) not null,
    ip          varchar(30) not null,
    login_time  datetime    null,
    logout_time datetime    null,
    primary key (login_id, ip)

) default character set utf8
  collate utf8_general_ci;
create table if not exists `back_auth`
(
    login_id    varchar(30) not null,
    ip          varchar(30) not null,
    login_time  datetime    null,
    logout_time datetime    null,
    primary key (login_id, ip)

) default character set utf8
  collate utf8_general_ci;

create table if not exists `notification_message`
(
    message_id BIGINT PRIMARY KEY auto_increment,
    receiver   varchar(100)not null,
    message    varchar(254),
    receive_time datetime,
    read_time datetime,
    link_to varchar(50),
    identifier varchar(50),
    for_manager boolean default false,
    constraint notification_message_ibfk_1
        foreign key (receiver) references employee (employee_id) on delete cascade

) default character set utf8
  collate utf8_general_ci;

create table if not exists `back_notification_message`
(
    message_id BIGINT PRIMARY KEY auto_increment,
    receiver   varchar(100)not null,
    message    varchar(254),
    receive_time datetime,
    read_time datetime,
    link_to varchar(50),
    identifier varchar(50),
    for_manager boolean default false

) default character set utf8
  collate utf8_general_ci;