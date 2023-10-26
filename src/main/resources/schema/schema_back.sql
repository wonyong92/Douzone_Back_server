use douzone_test;


drop table if exists attendance_appeal_request;
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


create table admin
(
    admin_id varchar(10) not null
        primary key,
    password varchar(10) not null
);

create table attendance_status_category
(
    `key` varchar(10) not null
        primary key
);

create table employee
(
    employee_id        varchar(10)                    not null
        primary key,
    password           varchar(10)                    not null,
    name               varchar(10)                    not null,
    attendance_manager boolean                     not null,
    hire_year          DATE DEFAULT DATE_FORMAT(CURDATE(), '%Y-01-01') NOT NULL
    # 올해 년도를 가져와 올해 연도 1월 1일로 default 값 생성
);

create table attendance_info
(
    attendance_info_id         bigint               not null
        primary key auto_increment,
    attendance_status_category varchar(10)          not null,
    employee_id                varchar(10)          not null,
    start_time                 datetime             null,
    end_time                   datetime             null,
    attendance_date            date default (now()) not null,
    constraint attendance_info_ibfk_1
        foreign key (employee_id) references employee (employee_id),
    constraint attendance_info_ibfk_2
        foreign key (attendance_status_category) references attendance_status_category (`key`)
);

create table attendance_appeal_request
(
    attendance_appeal_request_id   bigint                   not null
        primary key auto_increment,
    status                         varchar(10)              not null,
    reason                         text                     not null,
    attendance_info_id             bigint                   not null,
    appealed_start_time            datetime                 not null,
    appealed_end_time              datetime                 not null,
    employee_id                    varchar(10)              not null,
    attendance_appeal_request_time datetime default (now()) not null,
    reason_for_rejection text,
    constraint attendance_appeal_request_ibfk_1
        foreign key (attendance_info_id) references attendance_info (attendance_info_id),
    constraint attendance_appeal_request_ibfk_2
        foreign key (employee_id) references employee (employee_id)
);

create table attendance_approval
(
    attendance_approval_id   bigint      not null
        primary key auto_increment,
    attendance_info_id       bigint      not null,
    attendance_approval_date datetime    not null,
    employee_id              varchar(10) not null,
    constraint attendance_approval_ibfk_1
        foreign key (attendance_info_id) references attendance_info (attendance_info_id),
    constraint attendance_approval_ibfk_2
        foreign key (employee_id) references employee (employee_id)
);

create table image
(
    file_id     bigint                   not null
        primary key auto_increment,
    employee_id varchar(10)              not null,
    file_name   varchar(255)             not null,
    uuid        varchar(255)             not null,
    upload_date datetime default (now()) not null,
    constraint image_ibfk_1
        foreign key (employee_id) references employee (employee_id)
);

create table vacation_adjusted_history
(
    vacation_adjusted_history_id bigint      not null
        primary key auto_increment,
    employee_id                  varchar(10) null,
    adjust_type                  varchar(10) not null,
    adjust_time                  datetime    not null,
    adjust_quantity              int         not null,
    reason                       text        not null,
    constraint vacation_adjusted_history_ibfk_1
        foreign key (employee_id) references employee (employee_id)
);

create table vacation_category
(
    vacation_category_key varchar(10) not null
        primary key,
    admit_time            int         not null
);

create table vacation_quantity_setting
(
    setting_key  int      not null
        primary key auto_increment,
    freshman     int      not null,
    senior       int      not null,
    setting_time datetime default Now() not null
);

create table vacation_request
(
    vacation_request_key        bigint      not null
        primary key auto_increment,
    vacation_category_key       varchar(10) not null,
    employee_id                 varchar(10) not null,
    result                      varchar(10) not null,
    vacation_quantity           int         not null,
    vacation_start_time         datetime    not null,
    vacation_end_time           datetime    not null,
    vacation_related_start_time datetime    not null,
    vacation_related_end_time   datetime    not null,
    reason                      text        not null,
    vacation_request_time datetime default(now()),
    reason_for_rejection text,
    constraint vacation_request_ibfk_1
        foreign key (vacation_category_key) references vacation_category (vacation_category_key),
    constraint vacation_request_ibfk_2
        foreign key (employee_id) references employee (employee_id)
);


CREATE TABLE `regular_time_adjustment_history`
(
    `regular_time_adjustment_history_id` BIGINT   NOT NULL PRIMARY KEY auto_increment,
    `target_date`                        DATE     NOT NULL DEFAULT (DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 1 DAY)),
    `adjusted_start_time`                TIME NOT NULL,
    `adjusted_end_time`                  TIME NOT NULL,
    `reason`                             TEXT     NOT NULL,
    `regular_time_adjustment_time`       DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);
