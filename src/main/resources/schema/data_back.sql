insert into employee
values ('200001011', 'test', 'testone', false, '2000-01-01'),
       ('200001012', 'test', 'testtwo', true, '2023-01-01'),
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
values ('연차 요청 중'),('연차 요청 반려'),('연차 요청 승인');


insert into vacation_category
values ('normal', 1),('undefined',1);

insert into douzone_test.attendance_appeal_request_status (attendance_appeal_request_status_key)
values ('조정 요청 반려'),
       ('조정 요청 승인'),
       ('조정 요청 중');

insert into attendance_status_category
values ('근태 판정 중'),
       ('이상 근태(결근)'),

       ('조정 요청 중'),
       ('이상 근태(조기 퇴근)'),
       ('정상 근태'),
       ('이상 근태(지각, 조기 퇴근)'),
       ('이상 근태(지각)'),

       ('이상 근태(지각, 퇴근 정보 없음)'),
       ('이상 근태(퇴근 정보 없음)');
insert into douzone_test.attendance_info (attendance_info_id, attendance_status_category, employee_id, start_time,
                                          end_time, attendance_date)
values (1, '이상 근태(지각)', '200001011', '2023-10-31 09:24:57', '2023-10-31 16:24:59', '2023-10-31');
insert into douzone_test.attendance_appeal_request (status, reason, attendance_info_id, appealed_start_time,
                                                    appealed_end_time, employee_id, attendance_appeal_request_time,
                                                    reason_for_rejection, attendance_appeal_request_id)
values ('조정 요청 중', 'dddd', 1, '14:17:49', '16:17:58', '200001011', '2023-10-31 16:18:10', null, 1);


insert into vacation_category
values ('a', 2);
insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity,
                             vacation_start_date, vacation_end_date, vacation_request_time, reason)
values ('a', '200001011', '연차 요청 중', 10, '2023-01-16', '2023-01-20', '2023-01-05 15:00:00', '사유1');

-- 연차관리현황데이터
insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity,
                             vacation_start_date, vacation_end_date, vacation_request_time, reason)
values ('a', '200001012', '연차 요청 승인', 2, '2023-01-26', '2023-01-27', '2023-01-23 16:00:00', '사유1');
insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity,
                             vacation_start_date, vacation_end_date, vacation_request_time, reason)
values ('a', '200001012', '연차 요청 승인', 3, '2023-01-11', '2023-01-13', '2023-01-03 16:00:00', '사유1');
insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity,
                             vacation_start_date, vacation_end_date, vacation_request_time, reason)
values ('a', '200001012', '연차 요청 중', 8, '2023-03-19', '2023-03-29', '2023-01-04 15:00:00', '사유1');


insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity,
                             vacation_start_date, vacation_end_date, vacation_request_time, reason)
values ('a', '200001012', '연차 요청 반려', 6, '2023-04-15', '2023-04-20', '2023-01-03 16:00:00', '사유1');

insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity,
                             vacation_start_date, vacation_end_date, vacation_request_time, reason)
values ('a', '200001012', '연차 요청 반려', 2, '2022-12-31', '2023-01-01', '2023-01-03 16:00:00', '사유1');


insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity,
                             vacation_start_date, vacation_end_date, vacation_request_time, reason)
values ('a', '200001012', '연차 요청 반려', 5, '2023-01-21', '2023-01-25', '2023-01-03 16:00:00', '사유1');

insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity,
                             vacation_start_date, vacation_end_date, vacation_request_time, reason)
values ('a', '200001012', '연차 요청 반려', 3, '2023-01-30', '2023-02-01', '2023-01-03 18:00:00', '사유1');

insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity,
                             vacation_start_date, vacation_end_date, vacation_request_time, reason)
values ('a', '200001012', '연차 요청 반려', 2, '2023-02-28', '2023-03-01', '2023-03-03 18:00:00', '사유1');

insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity,
                             vacation_start_date, vacation_end_date, vacation_request_time, reason)
values ('a', '200001012', '연차 요청 반려', 3, '2023-02-14', '2023-02-16', '2023-03-03 18:00:00', '사유1');




insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity,
                             vacation_start_date, vacation_end_date, vacation_request_time, reason)
values ('a', '200001012', '연차 요청 승인', 10, '2023-03-01', '2023-03-10', '2023-03-01 15:00:00', '사유1');

insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity,
                             vacation_start_date, vacation_end_date, vacation_request_time, reason)
values ('a', '200001012', '연차 요청 승인', 5, '2023-11-02', '2023-11-06', '2023-07-01 15:00:00', '사유1');


insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity,
                             vacation_start_date, vacation_end_date, vacation_request_time, reason)
values ('a', '200001011', '연차 요청 중', 6, '2023-01-05', '2023-01-10', '2023-01-02 15:00:00', '사유1');
# insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity,
#                              vacation_start_date, vacation_end_date, vacation_request_time, reason)
# values ('a', '200001012', '연차 요청 승인', 1, '2023-01-01', '2023-01-10', '2023-01-01 16:00:00', '사유1');
insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity,
                             vacation_start_date, vacation_end_date, vacation_request_time, reason)
values ('a', '200001011', '연차 요청 승인', 10, '2023-03-01', '2023-03-10', '2023-03-01 15:00:00', '사유1');

insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity,
                             vacation_start_date, vacation_end_date, vacation_request_time, reason,
                             reason_for_rejection)
values ('a', '200001011', '연차 요청 반려', 10, '2023-05-01', '2023-05-10', '2023-05-01 15:00:00', 'a', '안돼 돌아가');
insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity,
                             vacation_start_date, vacation_end_date, vacation_request_time, reason,
                             reason_for_rejection)
values ('a', '200001012', '연차 요청 반려', 10, '2023-05-01', '2023-05-10', '2023-05-01 15:00:00', 'a', '안돼 돌아가요');
insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity,
                             vacation_start_date, vacation_end_date, vacation_request_time, reason,
                             reason_for_rejection)
values ('a', '200001011', '연차 요청 반려', 10, '2023-06-01', '2023-06-10', '2023-06-01 15:00:00', 'a', '안돼 돌아가');
insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity,
                             vacation_start_date, vacation_end_date, vacation_request_time, reason,
                             reason_for_rejection)
values ('a', '200001012', '연차 요청 반려', 10, '2023-07-01', '2023-07-10', '2023-07-01 15:00:00', 'a', '안돼 돌아가요');
insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity,
                             vacation_start_date, vacation_end_date, vacation_request_time, reason)
values ('a', '200001011', '연차 요청 승인', 10, '2023-07-01', '2023-07-10', '2023-07-01 15:00:00', '사유1');
insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity,
                             vacation_start_date, vacation_end_date, vacation_request_time, reason)
values ('a', '200001011', '연차 요청 승인', 5, '2023-12-02', '2023-12-06', '2023-07-01 15:00:00', '사유1');


insert into vacation_quantity_setting(freshman, senior, setting_time, employee_id)
values (35, 40, '22-10-26 09:00:00', '200001011');
insert into vacation_quantity_setting(freshman, senior, setting_time, employee_id)
values (12, 14, '2022-10-27 09:00:00', '200001011');
insert into vacation_quantity_setting(freshman, senior, setting_time, employee_id)
values (30, 50, '2022-10-28 09:00:00', '200001011');
insert into vacation_quantity_setting(freshman, senior, setting_time, employee_id)
values (30, 50, '2022-10-29 09:00:00', '200001012');
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

INSERT INTO vacation_adjusted_history(employee_id, adjust_type, adjust_quantity, reason)
VALUES ('200001012', 'a', -5, '싫어');


INSERT INTO attendance_info (attendance_status_category, employee_id, start_time, end_time, attendance_date)
VALUES ('정상 근태', '200001011', '2023-02-01 09:00:00', '2023-02-01 18:00:00', '2023-02-01'),
       ('이상 근태(지각)', '200001011', '2023-02-01 09:00:00', '2023-02-01 18:00:00', '2023-02-03'),
       ('이상 근태(지각)', '200001011', '2023-02-01 09:00:00', '2023-02-01 18:00:00', '2023-02-04'),
       ('이상 근태(지각)', '200001011', '2023-02-01 09:00:00', '2023-02-01 18:00:00', '2023-02-05'),
       ('이상 근태(지각)', '200001011', '2023-02-01 09:00:00', '2023-02-01 18:00:00', '2023-02-06'),
       ('이상 근태(지각)', '200001011', '2023-02-01 09:00:00', '2023-02-01 18:00:00', '2023-02-07'),
       ('이상 근태(지각)', '200001011', '2023-02-01 09:00:00', '2023-02-01 18:00:00', '2023-02-08'),
       ('이상 근태(지각)', '200001011', '2023-02-01 09:00:00', '2023-02-01 18:00:00', '2023-02-09'),
       ('이상 근태(지각)', '200001011', '2023-02-01 09:00:00', '2023-02-01 18:00:00', '2023-02-10'),
       ('이상 근태(지각)', '200001011', '2023-02-01 09:00:00', '2023-02-01 18:00:00', '2023-02-11'),
       ('이상 근태(지각)', '200001011', '2023-02-01 09:00:00', '2023-02-01 18:00:00', '2023-02-12'),
       ('정상 근태', '200001012', '2023-01-02 09:00:00', '2023-01-02 18:00:00', '2023-01-02'),
       ('이상 근태(지각)', '200001012', '2023-02-02 09:00:00', '2023-02-02 18:00:00', '2023-02-02'),
       ('이상 근태(지각)', '200001012', '2023-01-03 09:00:00', '2023-01-03 18:00:00', '2023-01-03'),
       ('이상 근태(지각)', '200001012', '2023-01-04 09:00:00', '2023-01-04 18:00:00', '2023-01-04'),
       ('이상 근태(지각)', '200001012', '2023-01-05 09:00:00', '2023-01-05 18:00:00', '2023-01-05'),


       ('조정 요청 중', '200001012', '2023-01-15 09:00:00', '2023-01-15 18:00:00', '2023-01-15'),
       ('조정 요청 중', '200001012', '2023-01-16 09:00:00', '2023-01-16 18:00:00', '2023-01-16'),
       ('조정 요청 중', '200001012', '2023-01-17 09:00:00', '2023-01-17 18:00:00', '2023-01-17'),


       ('정상 근태', '200001013', '2023-01-03 09:00:00', '2023-01-03 18:00:00', '2023-01-03'),
       ('정상 근태', '200001013', '2023-02-03 09:00:00', '2023-02-03 18:00:00', '2023-02-03'),
       ('이상 근태(지각)', '200001014', '2023-01-04 09:00:00', '2023-01-04 18:00:00', '2023-01-04'),
       ('정상 근태', '200001013', '2023-02-03 09:00:00', '2023-02-03 18:00:00', '2023-02-05'),
       ('정상 근태', '200001013', '2023-02-03 09:00:00', '2023-02-03 18:00:00', '2023-02-06'),
       ('이상 근태(지각)', '200001014', '2023-02-04 09:00:00', '2023-02-04 18:00:00', '2023-02-04'),
       ('이상 근태(지각)', '200001015', '2023-01-05 09:00:00', '2023-01-05 18:00:00', '2023-01-05'),
       ('정상 근태', '200001015', '2023-02-05 09:00:00', '2023-02-05 18:00:00', '2023-02-05'),
       ('이상 근태(지각)', '200001016', '2023-01-06 09:00:00', '2023-01-06 18:00:00', '2023-01-06'),
       ('정상 근태', '200001016', '2023-02-06 09:00:00', '2023-02-06 18:00:00', '2023-02-06'),
       ('정상 근태', '200001018', '2023-01-07 09:00:00', '2023-01-07 18:00:00', '2023-01-07'),
       ('정상 근태', '200001018', '2023-02-07 09:00:00', '2023-02-07 18:00:00', '2023-02-07'),
       ('이상 근태(지각)', '200001019', '2023-01-08 09:00:00', '2023-01-08 18:00:00', '2023-01-08'),
       ('정상 근태', '200001019', '2023-02-08 09:00:00', '2023-02-08 18:00:00', '2023-02-08'),
       ('이상 근태(지각)', '200001019', '2023-01-09 09:00:00', '2023-01-09 18:00:00', '2023-01-09'),
       ('정상 근태', '200001019', '2023-02-09 09:00:00', '2023-02-09 18:00:00', '2023-02-09'),

       ('정상 근태', '200001021', '2023-01-10 09:00:00', '2023-01-10 18:00:00', '2023-01-10');



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



INSERT INTO attendance_approval (attendance_info_id, attendance_approval_date, employee_id)
VALUES (15, "2023-11-26T02:56:55", '200001013');
INSERT INTO attendance_approval (attendance_info_id, attendance_approval_date, employee_id)
VALUES (16, NOW(), '200001013');
INSERT INTO attendance_approval (attendance_info_id, attendance_approval_date, employee_id)
VALUES (18, "2023-11-26T02:56:56", '200001013');
INSERT INTO attendance_approval (attendance_info_id, attendance_approval_date, employee_id)
VALUES (19, NOW(), '200001013');


INSERT INTO attendance_appeal_request
(status, reason, attendance_info_id, appealed_start_time, appealed_end_time, employee_id,
 attendance_appeal_request_time, reason_for_rejection)
VALUES ('조정 요청 중', '이상 근태(지각)으로 인한 근태 조정 요청', 1, '2023-11-03 09:10:00', '2023-11-03 18:10:00', '200001011', '2023-11-27T15:11:07',
        NULL),
       ('조정 요청 반려', '조기 퇴근에 대한 조정 요청', 2, '2023-11-04 08:50:00', '2023-11-04 16:00:00', '200001012', NOW(),
        '정당한 이유 없음'),
       ('조정 요청 승인', '병가로 인한 전일 근무시간 조정', 3, '2023-11-05 00:00:00', '2023-11-05 00:00:00', '200001013', NOW(), NULL),
       ('조정 요청 중', '외출로 인한 근태 조정 요청', 4, '2023-11-06 08:30:00', '2023-11-06 15:30:00', '200001014', NOW(), NULL),
       ('조정 요청 승인', '업무상 필요에 의한 조정', 5, '2023-11-07 10:30:00', '2023-11-07 19:30:00', '200001015', NOW(), NULL),
       ('조정 요청 반려', '개인 사유에 의한 근태 조정 요청', 6, '2023-11-08 09:00:00', '2023-11-08 17:00:00', '200001016', NOW(),
        '근거 불충분'),
       ('조정 요청 중', '교통사고로 인한 조정 요청', 7, '2023-11-09 08:45:00', '2023-11-09 17:45:00', '200001018', NOW(), NULL),
       ('조정 요청 승인', '대중교통 지연에 따른 정상 근태시간 조정', 8, '2023-11-10 08:40:00', '2023-11-10 17:40:00', '200001019', NOW(),
        NULL),
       ('조정 요청 반려', '예상치 못한 개인 사정으로 인한 조정 요청', 9, '2023-11-11 08:55:00', '2023-11-11 17:55:00', '200001019', NOW(),
        '증빙 자료 미제출'),
       ('조정 요청 중', '아동 돌봄으로 인한 조정 요청', 10, '2023-11-12 09:05:00', '2023-11-12 18:05:00', '200001021', '2023-11-27T15:11:07', NULL),
       ('조정 요청 중', '이상 근태(지각)으로 인한 근태 조정 요청', 11, '2023-11-04 09:15:00', '2023-11-04 18:00:00', '200001011', NOW(),
        NULL),
       ('조정 요청 중', '개인 사정으로 인한 조기 퇴근 요청', 12, '2023-11-05 09:00:00', '2023-11-05 16:00:00', '200001011', NOW(), NULL),
       ('조정 요청 중', '외근으로 인한 근태 조정 요청', 14, '2023-11-07 10:00:00', '2023-11-07 15:00:00', '200001011', '2023-11-27T15:11:08', NULL),
       ('조정 요청 중', '교육 참석으로 인한 반차 요청', 15, '2023-11-08 13:00:00', '2023-11-08 18:00:00', '200001011', '2023-11-27T15:11:07', NULL),
       ('조정 요청 중', '대중교통 지연으로 인한 이상 근태(지각) 요청', 16, '2023-11-09 09:30:00', '2023-11-09 18:00:00', '200001011', NOW(),
        NULL),
       ('조정 요청 중', '가족 행사 참석으로 인한 휴가 요청', 17, '2023-11-10 09:00:00', '2023-11-10 18:00:00', '200001011', '2023-11-27T15:11:06', NULL),
       ('조정 요청 중', '긴급한 개인 사정으로 인한 이상 근태(지각) 요청', 18, '2023-11-11 00:00:00', '2023-11-11 00:00:00', '200001011', NOW(),
        NULL),
       ('조정 요청 중', '야근으로 인한 다음날 이상 근태(지각) 요청', 19, '2023-11-12 10:00:00', '2023-11-12 18:00:00', '200001011', '2023-11-26T15:11:07',
        NULL),
       ('조정 요청 중', '전일 야근 회복을 위한 이상 근태(지각) 요청', 20, '2023-11-13 10:00:00', '2023-11-13 18:00:00', '200001011', NOW(),
        NULL),
       ('조정 요청 중', '대중교통 지연으로 인한 이상 근태(지각) 요청', 21, '2023-11-09 09:30:00', '2023-11-09 18:00:00', '200001011', '2023-11-1 18:05:00',
        NULL),
       ('조정 요청 중', '가족 행사 참석으로 인한 휴가 요청', 22, '2023-11-10 09:00:00', '2023-11-10 18:00:00', '200001011', '2023-11-1 18:05:00' ,NULL),
        ('조정 요청 중', '긴급한 개인 사정으로 인한 이상 근태(지각) 요청', 23, '2023-11-11 00:00:00', '2023-11-11 00:00:00', '200001011','2023-11-1 18:05:00',
         NULL),
        ('조정 요청 중', '야근으로 인한 다음날 이상 근태(지각) 요청', 24, '2023-11-12 10:00:00', '2023-11-12 18:00:00', '200001011', '2023-11-1 18:05:00',
         NULL);

INSERT INTO douzone_test.notification_message (message_id, receiver, message, receive_time, read_time, link_to, identifier, for_manager) VALUES (1, '200001011', '1st', '2023-11-23 20:17:45', null, 'appeal', '1', 0);
INSERT INTO douzone_test.notification_message (message_id, receiver, message, receive_time, read_time, link_to, identifier, for_manager) VALUES (2, '200001011', '2nd', '2023-11-23 20:17:45', null, 'appeal', '1', 0);
INSERT INTO douzone_test.notification_message (message_id, receiver, message, receive_time, read_time, link_to, identifier, for_manager) VALUES (3, '200001011', '3rd', '2023-11-23 20:17:45', null, 'appeal', '1', 0);
INSERT INTO douzone_test.notification_message (message_id, receiver, message, receive_time, read_time, link_to, identifier, for_manager) VALUES (4, '200001011', '4th', '2023-11-23 20:17:45', null, 'appeal', '1', 0);





