insert into employee
values ('200001011', 'test', 'testone', false, '2000-01-01'),
       ('200001012', 'test', 'testtwo', true, '2000-01-01'),
       ('200001013', 'test', 'testthree', true, '2000-01-01'),
       ('200001014', 'test', 'testfour', true, '2000-01-01'),
       ('200001015', 'test', 'testfive', false, '2000-01-01'),
       ('200001016', 'test', 'testsix', false, '2000-01-01'),
       ('200001017', 'test', 'testseven', false, '2000-01-01'),
       ('200001018', 'test', 'testeight', false, '2000-01-01'),
       ('200001019', 'test', 'testnine', false, '2000-01-01'),
       ('200001020', 'test', 'testten', false, '2000-01-01'),
       ('200001021', 'test', 'testeleven', false, '2000-01-01');

insert into admin
values ('admin', 'admin');

insert into vacation_request_state_category
values ('requested');
insert into vacation_request_state_category
values ('permitted');
insert into vacation_request_state_category
values ('rejected');
insert into vacation_category
values ('normal', 1);
insert into douzone_test.attendance_appeal_request_status (attendance_appeal_request_status_key)
values ('permitted'),
       ('rejected'),
       ('approved'),
       ('requested');

insert into attendance_status_category
values ('abnormal'),
       ('requested'),
       ('pending'),
       ('normal');
insert into douzone_test.attendance_info (attendance_info_id, attendance_status_category, employee_id, start_time,
                                          end_time, attendance_date)
values (1, 'abnormal', '200001011', '2023-10-31 09:24:57', '2023-10-31 16:24:59', '2023-10-31');
insert into douzone_test.attendance_appeal_request (status, reason, attendance_info_id, appealed_start_time,
                                                    appealed_end_time, employee_id, attendance_appeal_request_time,
                                                    reason_for_rejection, attendance_appeal_request_id)
values ('requested', 'dddd', 1, '14:17:49', '16:17:58', '200001011', '2023-10-31 16:18:10', null, 1);


insert into vacation_category
values ('a', 2);
insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity,
                             vacation_start_date, vacation_end_date, vacation_request_time, reason)
values ('a', '200001011', 'requested', 1, '2023-01-01', '2023-01-10', '2023-01-05 15:00:00', 'a');
insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity,
                             vacation_start_date, vacation_end_date, vacation_request_time, reason)
values ('a', '200001012', 'permitted', 1, '2023-01-01', '2023-01-10', '2023-01-01 16:00:00', 'a');

insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity,
                             vacation_start_date, vacation_end_date, vacation_request_time, reason)
values ('a', '200001011', 'requested', 1, '2023-01-03', '2023-01-10', '2023-01-04 15:00:00', 'a');
insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity,
                             vacation_start_date, vacation_end_date, vacation_request_time, reason)
values ('a', '200001012', 'permitted', 1, '2023-01-04', '2023-01-10', '2023-01-03 16:00:00', 'a');

insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity,
                             vacation_start_date, vacation_end_date, vacation_request_time, reason)
values ('a', '200001011', 'requested', 1, '2023-01-05', '2023-01-10', '2023-01-02 15:00:00', 'a');
insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity,
                             vacation_start_date, vacation_end_date, vacation_request_time, reason)
values ('a', '200001012', 'permitted', 1, '2023-01-01', '2023-01-10', '2023-01-01 16:00:00', 'a');
insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity,
                             vacation_start_date, vacation_end_date, vacation_request_time, reason)
values ('a', '200001011', 'permitted', 10, '2023-03-01', '2023-03-10', '2023-03-01 15:00:00', 'a');
insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity,
                             vacation_start_date, vacation_end_date, vacation_request_time, reason)
values ('a', '200001012', 'permitted', 1, '2023-03-01', '2023-03-10', '2023-03-01 15:00:00', 'a');
insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity,
                             vacation_start_date, vacation_end_date, vacation_request_time, reason,
                             reason_for_rejection)
values ('a', '200001011', 'rejected', 1, '2023-05-01', '2023-05-10', '2023-05-01 15:00:00', 'a', '안돼 돌아가');
insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity,
                             vacation_start_date, vacation_end_date, vacation_request_time, reason,
                             reason_for_rejection)
values ('a', '200001012', 'rejected', 1, '2023-05-01', '2023-05-10', '2023-05-01 15:00:00', 'a', '안돼 돌아가요');
insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity,
                             vacation_start_date, vacation_end_date, vacation_request_time, reason,
                             reason_for_rejection)
values ('a', '200001011', 'rejected', 1, '2023-06-01', '2023-06-10', '2023-06-01 15:00:00', 'a', '안돼 돌아가');
insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity,
                             vacation_start_date, vacation_end_date, vacation_request_time, reason,
                             reason_for_rejection)
values ('a', '200001012', 'rejected', 1, '2023-07-01', '2023-07-10', '2023-07-01 15:00:00', 'a', '안돼 돌아가요');
insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity,
                             vacation_start_date, vacation_end_date, vacation_request_time, reason)
values ('a', '200001011', 'permitted', 4, '2023-07-01', '2023-07-10', '2023-07-01 15:00:00', 'a');
insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity,
                             vacation_start_date, vacation_end_date, vacation_request_time, reason)
values ('a', '200001011', 'permitted', 4, '2023-12-02', '2023-12-06', '2023-07-01 15:00:00', 'a');



insert into vacation_quantity_setting(freshman, senior, setting_time, employee_id)
values (11, 14, '22-10-26 09:00:00', '200001011');
insert into vacation_quantity_setting(freshman, senior, setting_time, employee_id)
values (12, 14, '2022-10-27 09:00:00', '200001011');
insert into vacation_quantity_setting(freshman, senior, setting_time, employee_id)
values (13, 24, '2022-10-28 09:00:00', '200001011');
insert into vacation_quantity_setting(freshman, senior, setting_time, employee_id)
values (14, 24, '2022-10-29 09:00:00', '200001012');
insert into vacation_quantity_setting(freshman, senior, setting_time, employee_id)
values (15, 24, '2023-10-16 09:00:00', '200001011');
insert into vacation_quantity_setting(freshman, senior, setting_time, employee_id)
values (16, 24, '2023-10-6 09:00:00', '200001011');
insert into vacation_quantity_setting(freshman, senior, setting_time, employee_id)
values (17, 24, '2023-10-1 09:00:00', '200001011');
insert into vacation_quantity_setting(freshman, senior, setting_time, employee_id)
values (18, 24, '2023-10-2 09:00:00', '200001011');
insert into vacation_quantity_setting(freshman, senior, setting_time, employee_id)
values (19, 24, '2023-10-3 09:00:00', '200001011');
insert into vacation_quantity_setting(freshman, senior, setting_time, employee_id)
values (21, 24, '2023-10-4 09:00:00', '200001011');
insert into vacation_quantity_setting(freshman, senior, setting_time, employee_id)
values (21, 34, '2023-10-5 09:00:00', '200001011');
insert into vacation_quantity_setting(freshman, senior, setting_time, employee_id)
values (21, 44, '2023-10-7 09:00:00', '200001011');
insert into vacation_quantity_setting(freshman, senior, setting_time, employee_id)
values (20, 24, '2023-10-8 09:00:00', '200001012');
insert into vacation_quantity_setting(freshman, senior, setting_time, employee_id)
values (20, 24, '2023-10-8 09:00:00', '200001012');



INSERT INTO regular_time_adjustment_history(TARGET_DATE, adjusted_start_time, adjusted_end_time, reason,
                                            regular_time_adjustment_time, employee_id)
values ('2023-01-01', '09:00:00', '16:00:00', '집에 일찍 가고싶어서', '2022-12-31 09:00:00', '200001011');
INSERT INTO regular_time_adjustment_history(TARGET_DATE, adjusted_start_time, adjusted_end_time, reason,
                                            regular_time_adjustment_time, employee_id)
values ('2023-02-01', '09:00:00', '16:00:00', '집에 일찍 가고싶어서', '2022-12-31 09:00:00', '200001011');
INSERT INTO regular_time_adjustment_history(TARGET_DATE, adjusted_start_time, adjusted_end_time, reason,
                                            regular_time_adjustment_time, employee_id)
values ('2023-03-01', '09:00:00', '16:00:00', '집에 일찍 가고싶어서', '2022-12-31 09:00:00', '200001011');
INSERT INTO regular_time_adjustment_history(TARGET_DATE, adjusted_start_time, adjusted_end_time, reason,
                                            regular_time_adjustment_time, employee_id)
values ('2023-04-01', '09:00:00', '16:00:00', '집에 일찍 가고싶어서', '2022-12-31 09:00:00', '200001011');
INSERT INTO regular_time_adjustment_history(TARGET_DATE, adjusted_start_time, adjusted_end_time, reason,
                                            regular_time_adjustment_time, employee_id)
values ('2023-05-01', '09:00:00', '16:00:00', '집에 일찍 가고싶어서', '2022-12-31 09:00:00', '200001011');
INSERT INTO regular_time_adjustment_history(TARGET_DATE, adjusted_start_time, adjusted_end_time, reason,
                                            regular_time_adjustment_time, employee_id)
values ('2023-06-01', '09:00:00', '16:00:00', '집에 일찍 가고싶어서', '2022-12-31 09:00:00', '200001011');
INSERT INTO regular_time_adjustment_history(TARGET_DATE, adjusted_start_time, adjusted_end_time, reason,
                                            regular_time_adjustment_time, employee_id)
values ('2023-07-01', '09:00:00', '16:00:00', '집에 일찍 가고싶어서', '2022-12-31 09:00:00', '200001011');
INSERT INTO regular_time_adjustment_history(TARGET_DATE, adjusted_start_time, adjusted_end_time, reason,
                                            regular_time_adjustment_time, employee_id)
values ('2023-08-01', '09:00:00', '16:00:00', '집에 일찍 가고싶어서', '2022-12-31 09:00:00', '200001011');
INSERT INTO regular_time_adjustment_history(TARGET_DATE, adjusted_start_time, adjusted_end_time, reason,
                                            regular_time_adjustment_time, employee_id)
values ('2023-09-01', '09:00:00', '16:00:00', '집에 일찍 가고싶어서', '2022-12-31 09:00:00', '200001011');
INSERT INTO regular_time_adjustment_history(TARGET_DATE, adjusted_start_time, adjusted_end_time, reason,
                                            regular_time_adjustment_time, employee_id)
values ('2023-10-01', '09:00:00', '16:00:00', '집에 일찍 가고싶어서', '2022-12-31 09:00:00', '200001011');
INSERT INTO regular_time_adjustment_history(TARGET_DATE, adjusted_start_time, adjusted_end_time, reason,
                                            regular_time_adjustment_time, employee_id)
values ('2023-11-01', '09:00:00', '16:00:00', '집에 일찍 가고싶어서', '2022-12-31 09:00:00', '200001011');
INSERT INTO regular_time_adjustment_history(TARGET_DATE, adjusted_start_time, adjusted_end_time, reason,
                                            regular_time_adjustment_time, employee_id)
values ('2023-12-01', '09:00:00', '16:00:00', '집에 일찍 가고싶어서', '2022-12-31 09:00:00', '200001012');


INSERT INTO vacation_adjusted_history(employee_id, adjust_type, adjust_quantity, reason)
VALUES ('200001011', 'a', -1, '싫어');
INSERT INTO vacation_adjusted_history(employee_id, adjust_type, adjust_quantity, reason)
VALUES ('200001011', 'a', 1, '싫어');
INSERT INTO vacation_adjusted_history(employee_id, adjust_type, adjust_quantity, reason)
VALUES ('200001011', 'a', -1, '싫어');

select *
from attendance_status_category;

INSERT INTO attendance_info (attendance_status_category, employee_id, start_time, end_time, attendance_date)
VALUES ('normal', '200001011', '2023-02-01 09:00:00', '2023-02-01 18:00:00', '2023-02-01'),
       ('abnormal', '200001011', '2023-02-01 09:00:00', '2023-02-01 18:00:00', '2023-02-03'),
       ('abnormal', '200001011', '2023-02-01 09:00:00', '2023-02-01 18:00:00', '2023-02-04'),
       ('abnormal', '200001011', '2023-02-01 09:00:00', '2023-02-01 18:00:00', '2023-02-05'),
       ('abnormal', '200001011', '2023-02-01 09:00:00', '2023-02-01 18:00:00', '2023-02-06'),
       ('abnormal', '200001011', '2023-02-01 09:00:00', '2023-02-01 18:00:00', '2023-02-07'),
       ('abnormal', '200001011', '2023-02-01 09:00:00', '2023-02-01 18:00:00', '2023-02-08'),
       ('abnormal', '200001011', '2023-02-01 09:00:00', '2023-02-01 18:00:00', '2023-02-09'),
       ('abnormal', '200001011', '2023-02-01 09:00:00', '2023-02-01 18:00:00', '2023-02-10'),
       ('abnormal', '200001011', '2023-02-01 09:00:00', '2023-02-01 18:00:00', '2023-02-11'),
       ('abnormal', '200001011', '2023-02-01 09:00:00', '2023-02-01 18:00:00', '2023-02-12'),
       ('normal', '200001012', '2023-01-02 09:00:00', '2023-01-02 18:00:00', '2023-01-02'),
       ('abnormal', '200001012', '2023-02-02 09:00:00', '2023-02-02 18:00:00', '2023-02-02'),
       ('normal', '200001013', '2023-01-03 09:00:00', '2023-01-03 18:00:00', '2023-01-03'),
       ('normal', '200001013', '2023-02-03 09:00:00', '2023-02-03 18:00:00', '2023-02-03'),
       ('abnormal', '200001014', '2023-01-04 09:00:00', '2023-01-04 18:00:00', '2023-01-04'),
       ('normal', '200001013', '2023-02-03 09:00:00', '2023-02-03 18:00:00', '2023-02-05'),
       ('normal', '200001013', '2023-02-03 09:00:00', '2023-02-03 18:00:00', '2023-02-06'),
       ('abnormal', '200001014', '2023-02-04 09:00:00', '2023-02-04 18:00:00', '2023-02-04'),
       ('abnormal', '200001015', '2023-01-05 09:00:00', '2023-01-05 18:00:00', '2023-01-05'),
       ('normal', '200001015', '2023-02-05 09:00:00', '2023-02-05 18:00:00', '2023-02-05'),
       ('abnormal', '200001016', '2023-01-06 09:00:00', '2023-01-06 18:00:00', '2023-01-06'),
       ('normal', '200001016', '2023-02-06 09:00:00', '2023-02-06 18:00:00', '2023-02-06'),
       ('normal', '200001018', '2023-01-07 09:00:00', '2023-01-07 18:00:00', '2023-01-07'),
       ('normal', '200001018', '2023-02-07 09:00:00', '2023-02-07 18:00:00', '2023-02-07'),
       ('abnormal', '200001019', '2023-01-08 09:00:00', '2023-01-08 18:00:00', '2023-01-08'),
       ('normal', '200001019', '2023-02-08 09:00:00', '2023-02-08 18:00:00', '2023-02-08'),
       ('abnormal', '200001019', '2023-01-09 09:00:00', '2023-01-09 18:00:00', '2023-01-09'),
       ('normal', '200001019', '2023-02-09 09:00:00', '2023-02-09 18:00:00', '2023-02-09'),

       ('normal', '200001021', '2023-01-10 09:00:00', '2023-01-10 18:00:00', '2023-01-10');



INSERT INTO attendance_approval (attendance_info_id, attendance_approval_date, employee_id)
VALUES (1, NOW(), '200001011');
INSERT INTO attendance_approval (attendance_info_id, attendance_approval_date, employee_id)
VALUES (2, NOW(), '200001012');
INSERT INTO attendance_approval (attendance_info_id, attendance_approval_date, employee_id)
VALUES (3, NOW(), '200001013');
INSERT INTO attendance_approval (attendance_info_id, attendance_approval_date, employee_id)
VALUES (4, NOW(), '200001014');
INSERT INTO attendance_approval (attendance_info_id, attendance_approval_date, employee_id)
VALUES (5, NOW(), '200001015');
INSERT INTO attendance_approval (attendance_info_id, attendance_approval_date, employee_id)
VALUES (6, NOW(), '200001016');
INSERT INTO attendance_approval (attendance_info_id, attendance_approval_date, employee_id)
VALUES (7, NOW(), '200001018');
INSERT INTO attendance_approval (attendance_info_id, attendance_approval_date, employee_id)
VALUES (8, NOW(), '200001019');
INSERT INTO attendance_approval (attendance_info_id, attendance_approval_date, employee_id)
VALUES (9, NOW(), '200001019');
INSERT INTO attendance_approval (attendance_info_id, attendance_approval_date, employee_id)
VALUES (10, NOW(), '200001021');


INSERT INTO attendance_appeal_request
(status, reason, attendance_info_id, appealed_start_time, appealed_end_time, employee_id,
 attendance_appeal_request_time, reason_for_rejection)
VALUES ('requested', 'abnormal으로 인한 근태 조정 요청', 1, '2023-11-03 09:10:00', '2023-11-03 18:10:00', '200001011', NOW(),
        NULL),
       ('rejected', '조기 퇴근에 대한 조정 요청', 2, '2023-11-04 08:50:00', '2023-11-04 16:00:00', '200001012', NOW(),
        '정당한 이유 없음'),
       ('approved', '병가로 인한 전일 근무시간 조정', 3, '2023-11-05 00:00:00', '2023-11-05 00:00:00', '200001013', NOW(), NULL),
       ('requested', '외출로 인한 근태 조정 요청', 4, '2023-11-06 08:30:00', '2023-11-06 15:30:00', '200001014', NOW(), NULL),
       ('approved', '업무상 필요에 의한 조정', 5, '2023-11-07 10:30:00', '2023-11-07 19:30:00', '200001015', NOW(), NULL),
       ('rejected', '개인 사유에 의한 근태 조정 요청', 6, '2023-11-08 09:00:00', '2023-11-08 17:00:00', '200001016', NOW(),
        '근거 불충분'),
       ('requested', '교통사고로 인한 조정 요청', 7, '2023-11-09 08:45:00', '2023-11-09 17:45:00', '200001018', NOW(), NULL),
       ('approved', '대중교통 지연에 따른 normal시간 조정', 8, '2023-11-10 08:40:00', '2023-11-10 17:40:00', '200001019', NOW(),
        NULL),
       ('rejected', '예상치 못한 개인 사정으로 인한 조정 요청', 9, '2023-11-11 08:55:00', '2023-11-11 17:55:00', '200001019', NOW(),
        '증빙 자료 미제출'),
       ('requested', '아동 돌봄으로 인한 조정 요청', 10, '2023-11-12 09:05:00', '2023-11-12 18:05:00', '200001021', NOW(), NULL),
       ('requested', 'abnormal으로 인한 근태 조정 요청', 11, '2023-11-04 09:15:00', '2023-11-04 18:00:00', '200001011', NOW(),
        NULL),
       ('requested', '개인 사정으로 인한 조기 퇴근 요청', 12, '2023-11-05 09:00:00', '2023-11-05 16:00:00', '200001011', NOW(), NULL),
       ('requested', '건강 문제로 인한 abnormal 요청', 13, '2023-11-06 00:00:00', '2023-11-06 00:00:00', '200001011', NOW(),
        NULL),
       ('requested', '외근으로 인한 근태 조정 요청', 14, '2023-11-07 10:00:00', '2023-11-07 15:00:00', '200001011', NOW(), NULL),
       ('requested', '교육 참석으로 인한 반차 요청', 15, '2023-11-08 13:00:00', '2023-11-08 18:00:00', '200001011', NOW(), NULL),
       ('requested', '대중교통 지연으로 인한 abnormal 요청', 16, '2023-11-09 09:30:00', '2023-11-09 18:00:00', '200001011', NOW(),
        NULL),
       ('requested', '가족 행사 참석으로 인한 휴가 요청', 17, '2023-11-10 09:00:00', '2023-11-10 18:00:00', '200001011', NOW(), NULL),
       ('requested', '긴급한 개인 사정으로 인한 abnormal 요청', 18, '2023-11-11 00:00:00', '2023-11-11 00:00:00', '200001011', NOW(),
        NULL),
       ('requested', '야근으로 인한 다음날 abnormal 요청', 19, '2023-11-12 10:00:00', '2023-11-12 18:00:00', '200001011', NOW(),
        NULL),
       ('requested', '전일 야근 회복을 위한 abnormal 요청', 20, '2023-11-13 10:00:00', '2023-11-13 18:00:00', '200001011', NOW(),
        NULL);

select *
from employee;

select *
from vacation_request;


SELECT vr.employee_id as employeeId, vr.vacation_request_state_category_key as vacationRequestStateCategoryKey, vr.vacation_start_date as vacationStartDate, vr.vacation_end_date as vacationEndDate, vr.reason, vr.vacation_request_time as vacationRequestTime, vr.reason_for_rejection as reasonForRejection, e.name FROM vacation_request vr INNER JOIN employee e ON vr.employee_id = e.employee_id WHERE (e.name LIKE CONCAT('%', 'test', '%')) AND vr.vacation_request_time = '2023-01-01' LIMIT 10 OFFSET 1 ;


SELECT vr.employee_id as employeeId, vr.vacation_request_state_category_key as vacationRequestStateCategoryKey, vr.vacation_start_date as vacationStartDate, vr.vacation_end_date as vacationEndDate, vr.reason, vr.vacation_request_time as vacationRequestTime, vr.reason_for_rejection as reasonForRejection, e.name FROM vacation_request vr INNER JOIN employee e ON vr.employee_id = e.employee_id WHERE (e.name LIKE CONCAT('%', 'te', '%')) AND DATE(vr.vacation_request_time) = '2023-01' LIMIT 10 OFFSET 1 ;