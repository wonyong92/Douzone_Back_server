insert into employee
values ('200001011', 'test', 'testone', false, '2000-01-01'),
       ('200001012', 'test', 'testtwo', true, '2023-01-01'),
       ('200001013', 'test', 'testthree', true, '2011-01-01'),
       ('200001014', 'test', 'testfour', true, '2022-01-01'),
       ('200001015', 'test', 'testfive', false, '2013-01-01'),
       ('200001016', 'test', 'testsix', false, '2015-01-01'),
       ('200001017', 'test', 'testseven', false, '2018-01-01'),
       ('200001018', 'test', 'testeight', false, '2021-01-01'),
       ('200001019', 'test', 'testnine', false, '2023-07-01'),
       ('200001020', 'test', 'testten', false, '2020-09-01'),
       ('200001021', 'test', 'testeleven', false, '2023-09-01');

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
       ('조정 요청 승인'),
       ('정상 근태'),
       ('조정 요청 중'),
       ('이상 근태(조기 퇴근)'),
       ('이상 근태(결근)'),
       ('이상 근태(지각, 조기 퇴근)'),
       ('이상 근태(지각)'),
       ('이상 근태(지각, 퇴근 정보 없음)'),
       ('이상 근태(퇴근 정보 없음)'),
       ('승인|이상 근태(조기 퇴근)'),
       ('승인|이상 근태(결근)'),
       ('승인|이상 근태(지각, 조기 퇴근)'),
       ('승인|이상 근태(지각)'),
       ('승인|이상 근태(지각, 퇴근 정보 없음)'),('연차 요청 중'),('연차 요청 반려'),('연차 요청 승인'),
        ('조정 요청 반려'),
       ('승인|이상 근태(퇴근 정보 없음)');
insert into douzone_test.attendance_info (attendance_info_id, attendance_status_category, employee_id, start_time,
                                          end_time, attendance_date)
values (1, '이상 근태(지각)', '200001011', '2023-10-31 09:24:57', '2023-10-31 16:24:59', '2023-10-31');
insert into douzone_test.attendance_appeal_request (status, reason, attendance_info_id, appealed_start_time,
                                                    appealed_end_time, employee_id, attendance_appeal_request_time,
                                                    reason_for_rejection, attendance_appeal_request_id)
values ('조정 요청 중', 'dddd', 1, '14:17:49', '16:17:58', '200001011', '2023-10-31 16:18:10', null, 1);

# insert into vacation_category
# values ('a', 2);

insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity,
                             vacation_start_date, vacation_end_date, vacation_request_time, reason)
values ('undefined', '200001011', '연차 요청 승인', 3, '2023-11-01', '2023-11-03', '2023-10-01 15:00:00', '사유1');

insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity,
                             vacation_start_date, vacation_end_date, vacation_request_time, reason)
values ('undefined', '200001011', '연차 요청 승인', 2, '2023-11-06', '2023-11-07', '2023-10-01 15:00:00', '사유1');

insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity,
                             vacation_start_date, vacation_end_date, vacation_request_time, reason)
values ('undefined', '200001012', '연차 요청 승인', 2, '2023-11-02', '2023-11-03', '2023-07-01 15:00:00', '사유1');

insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity,
                             vacation_start_date, vacation_end_date, vacation_request_time, reason)
values ('undefined', '200001012', '연차 요청 승인', 2, '2023-11-06', '2023-11-07', '2023-07-01 15:00:00', '사유1');

insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity,
                             vacation_start_date, vacation_end_date, vacation_request_time, reason)
values ('undefined', '200001012', '연차 요청 승인', 1, '2023-12-01', '2023-12-01', '2023-10-01 15:00:00', '사유1');

insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity,
                             vacation_start_date, vacation_end_date, vacation_request_time, reason)
values ('undefined', '200001012', '연차 요청 승인', 1, '2023-12-04', '2023-12-04', '2023-10-01 15:00:00', '사유1');

insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity,
                             vacation_start_date, vacation_end_date, vacation_request_time, reason,reason_for_rejection)
values ('undefined', '200001012', '연차 요청 반려', 1, '2023-11-08', '2023-11-08', '2023-07-01 15:00:00', '사유1','업무 있음');

insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity,
                             vacation_start_date, vacation_end_date, vacation_request_time, reason , reason_for_rejection)
values ('undefined', '200001012', '연차 요청 반려', 1, '2023-12-12', '2023-12-12', '2023-07-01 15:00:00', '사유1' , '업무 있음');

insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity,
                             vacation_start_date, vacation_end_date, vacation_request_time, reason , reason_for_rejection)
values ('undefined', '200001012', '연차 요청 반려', 1, '2023-12-18', '2023-12-18', '2023-07-01 15:00:00', '사유1','업무 있음');

insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity,
                             vacation_start_date, vacation_end_date, vacation_request_time, reason , reason_for_rejection)
values ('undefined', '200001011', '연차 요청 승인', 1, '2022-11-09', '2022-11-09', '2022-11-03 15:00:00', '개인 사정','permitted');

insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity,
                             vacation_start_date, vacation_end_date, vacation_request_time, reason , reason_for_rejection)
values ('undefined', '200001011', '연차 요청 승인', 1, '2022-11-11', '2022-11-11', '2022-11-04 15:00:00', '개인 사정','permitted');

insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity,
                             vacation_start_date, vacation_end_date, vacation_request_time, reason , reason_for_rejection)
values ('undefined', '200001011', '연차 요청 반려', 1, '2022-11-16', '2022-11-16', '2022-11-07 15:00:00', '개인 사정','출장 일정 존재');

insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity,
                             vacation_start_date, vacation_end_date, vacation_request_time, reason , reason_for_rejection)
values ('undefined', '200001011', '연차 요청 승인', 1, '2022-11-18', '2022-11-18', '2022-11-10 15:00:00', '개인 사정','permitted');

insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity,
                             vacation_start_date, vacation_end_date, vacation_request_time, reason , reason_for_rejection)
values ('undefined', '200001011', '연차 요청 승인', 1, '2022-11-23', '2022-11-23', '2022-11-17 15:00:00', '개인 사정','permitted');


insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity,
                             vacation_start_date, vacation_end_date, vacation_request_time, reason , reason_for_rejection)
values ('undefined', '200001011', '연차 요청 반려', 1, '2022-12-06', '2022-12-06', '2022-12-01 15:00:00', '개인 사정','중요 미팅 일정 존재');

insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity,
                             vacation_start_date, vacation_end_date, vacation_request_time, reason , reason_for_rejection)
values ('undefined', '200001011', '연차 요청 승인', 1, '2022-12-12', '2022-12-12', '2022-12-08 15:00:00', '개인 사정','permitted');

insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity,
                             vacation_start_date, vacation_end_date, vacation_request_time, reason , reason_for_rejection)
values ('undefined', '200001011', '연차 요청 승인', 1, '2022-12-14', '2022-12-14', '2022-12-12 15:00:00', '개인 사정','permitted');

insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity,
                             vacation_start_date, vacation_end_date, vacation_request_time, reason , reason_for_rejection)
values ('undefined', '200001011', '연차 요청 반려', 1, '2022-12-19', '2022-12-19', '2022-12-15 15:00:00', '개인 사정','중요 미팅 일정 존재');

insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity,
                             vacation_start_date, vacation_end_date, vacation_request_time, reason , reason_for_rejection)
values ('undefined', '200001011', '연차 요청 반려', 1, '2022-12-27', '2022-12-27', '2022-12-27 15:00:00', '개인 사정','출장 일정 존재');



insert into vacation_quantity_setting(freshman, senior, setting_time, employee_id,target_date)
values (35, 50, '22-10-26 09:00:00', '200001011','2023-01-01');

insert into vacation_quantity_setting(freshman, senior, setting_time, employee_id,target_date)
values (27, 40, '21-12-26 09:00:00', '200001011','2022-01-01');


INSERT INTO regular_time_adjustment_history(TARGET_DATE, adjusted_start_time, adjusted_end_time, reason,
                                            regular_time_adjustment_time, employee_id)
values ('2023-02-01', '09:00:00', '16:00:00', '집에 일찍 가고싶어서', '2022-12-31 09:00:00', '200001012');

INSERT INTO regular_time_adjustment_history(TARGET_DATE, adjusted_start_time, adjusted_end_time, reason,
                                            regular_time_adjustment_time, employee_id)
values ('2022-01-01', '09:00:00', '17:00:00', '저녁이 있는 삶을 위한 연도로 지정', '2021-12-31 09:00:00', '200001011');



INSERT INTO vacation_adjusted_history(employee_id, adjust_type, adjust_quantity, reason)
VALUES ('200001011', 'a', 1, '싫어');

INSERT INTO vacation_adjusted_history(employee_id, adjust_type, adjust_quantity, reason)
VALUES ('200001011', 'a', -1, '싫어');

INSERT INTO vacation_adjusted_history(employee_id, adjust_type, adjust_quantity, reason)
VALUES ('200001012', 'a', -5, '싫어');


INSERT INTO attendance_info (attendance_status_category, employee_id, start_time, end_time, attendance_date)
VALUES ('승인|이상 근태(지각, 퇴근 정보 없음)', '200001012', '2023-10-04 09:00:00', '2023-10-04 18:00:00', '2023-10-04'),
       ('정상 근태', '200001012', '2023-10-05 09:00:00', '2023-10-05 18:00:00', '2023-10-05'),
       ('정상 근태', '200001012', '2023-10-06 09:00:00', '2023-10-06 18:00:00', '2023-10-06'),
       ('정상 근태', '200001012', '2023-10-10 09:00:00', '2023-10-10 18:00:00', '2023-10-10'),
       ('정상 근태', '200001012', '2023-10-11 09:00:00', '2023-10-11 18:00:00', '2023-10-11'),
       ('정상 근태', '200001012', '2023-10-12 09:00:00', '2023-10-12 18:00:00', '2023-10-12'),
       ('승인|이상 근태(지각, 퇴근 정보 없음)', '200001012', '2023-10-13 09:00:00', '2023-10-13 18:00:00', '2023-10-13'),
       ('정상 근태', '200001012', '2023-10-16 09:00:00', '2023-10-16 18:00:00', '2023-10-16'),
       ('승인|이상 근태(지각, 퇴근 정보 없음)', '200001012', '2023-10-17 09:00:00', '2023-10-17 18:00:00', '2023-10-17'),
       ('정상 근태', '200001012', '2023-10-18 09:00:00', '2023-10-18 18:00:00', '2023-10-18'),
       ('정상 근태', '200001012', '2023-10-19 09:00:00', '2023-10-19 18:00:00', '2023-10-19'),
       ('정상 근태', '200001012', '2023-10-20 09:00:00', '2023-10-20 18:00:00', '2023-10-20'),
       ('정상 근태', '200001012', '2023-10-23 09:00:00', '2023-10-23 18:00:00', '2023-10-23'),
       ('정상 근태', '200001012', '2023-10-24 09:00:00', '2023-10-24 18:00:00', '2023-10-24'),
       ('정상 근태', '200001012', '2023-10-25 09:00:00', '2023-10-25 18:00:00', '2023-10-25'),
       ('정상 근태', '200001012', '2023-10-26 09:00:00', '2023-10-26 18:00:00', '2023-10-26'),
       ('정상 근태', '200001012', '2023-10-27 09:00:00', '2023-10-27 18:00:00', '2023-10-27'),
       ('정상 근태', '200001012', '2023-10-30 09:00:00', '2023-10-30 18:00:00', '2023-10-30'),
       ('정상 근태', '200001012', '2023-11-08 09:00:00', '2023-11-08 18:00:00', '2023-11-08'),
       ('정상 근태', '200001012', '2023-11-09 09:00:00', '2023-11-09 18:00:00', '2023-11-09'),
       ('정상 근태', '200001012', '2023-11-10 09:00:00', '2023-11-10 18:00:00', '2023-11-10'),
       ('정상 근태', '200001012', '2023-11-13 09:00:00', '2023-11-13 18:00:00', '2023-11-13'),
       ('정상 근태', '200001012', '2023-11-14 09:00:00', '2023-11-14 18:00:00', '2023-11-14'),
       ('이상 근태(지각)', '200001012', '2023-11-15 09:00:00', '2023-11-15 18:00:00', '2023-11-15'),
       ('정상 근태', '200001012', '2023-11-16 09:00:00', '2023-11-16 18:00:00', '2023-11-16'),
       ('정상 근태', '200001012', '2023-11-17 09:00:00', '2023-11-17 18:00:00', '2023-11-17'),
       ('정상 근태', '200001012', '2023-11-20 09:00:00', '2023-11-20 18:00:00', '2023-11-20'),
       ('정상 근태', '200001012', '2023-11-21 09:00:00', '2023-11-21 18:00:00', '2023-11-21'),
       ('정상 근태', '200001012', '2023-11-22 09:00:00', '2023-11-22 18:00:00', '2023-11-22'),
       ('정상 근태', '200001012', '2023-11-23 09:00:00', '2023-11-23 18:00:00', '2023-11-23'),
       ('이상 근태(지각)', '200001012', '2023-11-24 09:00:00', '2023-11-24 18:00:00', '2023-11-24'),
       ('정상 근태', '200001012', '2023-11-27 09:00:00', '2023-11-27 18:00:00', '2023-11-27'),
       ('이상 근태(결근)', '200001012', '2023-11-28 09:00:00', '2023-11-28 18:00:00', '2023-11-28'),
       ('이상 근태(결근)', '200001012', '2023-11-29 09:00:00', '2023-11-29 18:00:00', '2023-11-29'),
       ('승인|이상 근태(결근)', '200001012', '2023-11-30 09:00:00', '2023-11-30 18:00:00', '2023-11-30'),
       ('이상 근태(결근)', '200001012', '2023-12-05 09:00:00', '2023-12-05 18:00:00', '2023-12-05'),
       ('정상 근태', '200001012', '2023-12-06 09:00:00', '2023-12-06 18:00:00', '2023-12-06'),
       ('정상 근태', '200001012', '2023-12-07 09:00:00', '2023-12-07 18:00:00', '2023-12-07'),
       ('정상 근태', '200001012', '2023-12-08 09:00:00', '2023-12-08 18:00:00', '2023-12-08'),
       ('정상 근태', '200001012', '2023-12-11 09:00:00', '2023-12-11 18:00:00', '2023-12-11'),
       ('정상 근태', '200001012', '2023-12-12 09:00:00', '2023-12-12 18:00:00', '2023-12-12'),
       ('정상 근태', '200001012', '2023-12-13 09:00:00', '2023-12-13 18:00:00', '2023-12-13'),
       ('정상 근태', '200001012', '2023-12-14 09:00:00', '2023-12-14 18:00:00', '2023-12-14'),
       ('정상 근태', '200001012', '2023-12-15 09:00:00', '2023-12-15 18:00:00', '2023-12-15'),
       ('정상 근태', '200001012', '2023-12-18 09:00:00', '2023-12-18 18:00:00', '2023-12-18'),
       ('정상 근태', '200001012', '2023-12-19 09:00:00', '2023-12-19 18:00:00', '2023-12-19'),
       ('정상 근태', '200001012', '2023-12-20 09:00:00', '2023-12-20 18:00:00', '2023-12-20'),
       ('정상 근태', '200001012', '2023-12-21 09:00:00', '2023-12-21 18:00:00', '2023-12-21'),



       ('정상 근태', '200001011', '2023-10-05 09:00:00', '2023-10-05 18:00:00', '2023-10-05'),
       ('정상 근태', '200001011', '2023-10-06 09:00:00', '2023-10-06 18:00:00', '2023-10-06'),
       ('정상 근태', '200001011', '2023-10-10 09:00:00', '2023-10-10 18:00:00', '2023-10-10'),
       ('정상 근태', '200001011', '2023-10-11 09:00:00', '2023-10-11 18:00:00', '2023-10-11'),
       ('정상 근태', '200001011', '2023-10-12 09:00:00', '2023-10-12 18:00:00', '2023-10-12'),
       ('승인|이상 근태(지각, 퇴근 정보 없음)', '200001011', '2023-10-13 09:00:00', '2023-10-13 18:00:00', '2023-10-13'),
       ('정상 근태', '200001011', '2023-10-16 09:00:00', '2023-10-16 18:00:00', '2023-10-16'),
       ('승인|이상 근태(지각, 퇴근 정보 없음)', '200001011', '2023-10-17 09:00:00', '2023-10-17 18:00:00', '2023-10-17'),
       ('정상 근태', '200001011', '2023-10-18 09:00:00', '2023-10-18 18:00:00', '2023-10-18'),
       ('정상 근태', '200001011', '2023-10-19 09:00:00', '2023-10-19 18:00:00', '2023-10-19'),
       ('정상 근태', '200001011', '2023-10-20 09:00:00', '2023-10-20 18:00:00', '2023-10-20'),
       ('정상 근태', '200001011', '2023-10-23 09:00:00', '2023-10-23 18:00:00', '2023-10-23'),
       ('정상 근태', '200001011', '2023-10-24 09:00:00', '2023-10-24 18:00:00', '2023-10-24'),
       ('정상 근태', '200001011', '2023-10-25 09:00:00', '2023-10-25 18:00:00', '2023-10-25'),
       ('정상 근태', '200001011', '2023-10-26 09:00:00', '2023-10-26 18:00:00', '2023-10-26'),
       ('정상 근태', '200001011', '2023-10-27 09:00:00', '2023-10-27 18:00:00', '2023-10-27'),
       ('정상 근태', '200001011', '2023-10-30 09:00:00', '2023-10-30 18:00:00', '2023-10-30'),
       ('정상 근태', '200001011', '2023-11-08 09:00:00', '2023-11-08 18:00:00', '2023-11-08'),
       ('정상 근태', '200001011', '2023-11-09 09:00:00', '2023-11-09 18:00:00', '2023-11-09'),
       ('정상 근태', '200001011', '2023-11-10 09:00:00', '2023-11-10 18:00:00', '2023-11-10'),
       ('정상 근태', '200001011', '2023-11-13 09:00:00', '2023-11-13 18:00:00', '2023-11-13'),
       ('정상 근태', '200001011', '2023-11-14 09:00:00', '2023-11-14 18:00:00', '2023-11-14'),
       ('이상 근태(지각)', '200001011', '2023-11-15 09:00:00', '2023-11-15 18:00:00', '2023-11-15'),
       ('정상 근태', '200001011', '2023-11-16 09:00:00', '2023-11-16 18:00:00', '2023-11-16'),
       ('정상 근태', '200001011', '2023-11-17 09:00:00', '2023-11-17 18:00:00', '2023-11-17'),
       ('정상 근태', '200001011', '2023-11-20 09:00:00', '2023-11-20 18:00:00', '2023-11-20'),
       ('정상 근태', '200001011', '2023-11-21 09:00:00', '2023-11-21 18:00:00', '2023-11-21'),
       ('정상 근태', '200001011', '2023-11-22 09:00:00', '2023-11-22 18:00:00', '2023-11-22'),
       ('정상 근태', '200001011', '2023-11-23 09:00:00', '2023-11-23 18:00:00', '2023-11-23'),
       ('이상 근태(지각)', '200001011', '2023-11-24 09:00:00', '2023-11-24 18:00:00', '2023-11-24'),
       ('정상 근태', '200001011', '2023-11-27 09:00:00', '2023-11-27 18:00:00', '2023-11-27'),
       ('이상 근태(결근)', '200001011', '2023-11-28 09:00:00', '2023-11-28 18:00:00', '2023-11-28'),
       ('이상 근태(결근)', '200001011', '2023-11-29 09:00:00', '2023-11-29 18:00:00', '2023-11-29'),
       ('승인|이상 근태(결근)', '200001011', '2023-11-30 09:00:00', '2023-11-30 18:00:00', '2023-11-30'),
       ('정상 근태', '200001011', '2023-12-01 09:00:00', '2023-12-01 18:00:00', '2023-12-01'),
       ('이상 근태(결근)', '200001011', '2023-12-05 09:00:00', '2023-12-05 18:00:00', '2023-12-05'),
       ('정상 근태', '200001011', '2023-12-06 09:00:00', '2023-12-06 18:00:00', '2023-12-06'),
       ('정상 근태', '200001011', '2023-12-07 09:00:00', '2023-12-07 18:00:00', '2023-12-07'),
       ('정상 근태', '200001011', '2023-12-08 09:00:00', '2023-12-08 18:00:00', '2023-12-08'),
       ('정상 근태', '200001011', '2023-12-11 09:00:00', '2023-12-11 18:00:00', '2023-12-11'),
       ('정상 근태', '200001011', '2023-12-12 09:00:00', '2023-12-12 18:00:00', '2023-12-12'),
       ('정상 근태', '200001011', '2023-12-13 09:00:00', '2023-12-13 18:00:00', '2023-12-13'),
       ('정상 근태', '200001011', '2023-12-14 09:00:00', '2023-12-14 18:00:00', '2023-12-14'),
       ('정상 근태', '200001011', '2023-12-15 09:00:00', '2023-12-15 18:00:00', '2023-12-15'),




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
       ('정상 근태', '200001021', '2023-01-10 09:00:00', '2023-01-10 18:00:00', '2023-01-10'),

       ('이상 근태(조기 퇴근)', '200001011', '2022-11-01 09:00:00', '2022-11-01 15:00:00', '2022-11-01'),
       ('정상 근태', '200001011', '2022-11-02 09:00:00', '2022-11-02 18:00:00', '2022-11-02'),
       ('정상 근태', '200001011', '2022-11-03 09:00:00', '2022-11-03 18:00:00', '2022-11-03'),
       ('정상 근태', '200001011', '2022-11-04 09:00:00', '2022-11-04 18:00:00', '2022-11-04'),
       ('정상 근태', '200001011', '2022-11-07 09:00:00', '2022-11-07 18:00:00', '2022-11-07'),
       ('정상 근태', '200001011', '2022-11-08 09:00:00', '2022-11-08 18:00:00', '2022-11-08'),

       ('이상 근태(지각, 조기 퇴근)', '200001011', '2022-11-10 10:00:00', '2022-11-10 16:00:00', '2022-11-10'),

       ('정상 근태', '200001011', '2022-11-14 09:00:00', '2022-11-14 18:00:00', '2022-11-14'),
       ('정상 근태', '200001011', '2022-11-15 09:00:00', '2022-11-15 18:00:00', '2022-11-15'),
       ('정상 근태', '200001011', '2022-11-16 09:00:00', '2022-11-16 18:00:00', '2022-11-16'),
       ('정상 근태', '200001011', '2022-11-17 09:00:00', '2022-11-17 18:00:00', '2022-11-17'),

       ('이상 근태(지각, 조기 퇴근)', '200001011','2022-11-21 10:00:00', '2022-11-21 17:00:00', '2022-11-21'),
       ('이상 근태(지각)', '200001011', '2022-11-22 10:00:00', '2022-11-22 18:00:00', '2022-11-22'),

       ('이상 근태(지각)', '200001011', '2022-11-24 13:00:00', '2022-11-24 18:00:00', '2022-11-24'),
       ('이상 근태(지각)', '200001011', '2022-11-25 11:00:00', '2022-11-25 18:00:00', '2022-11-25'),
       ('정상 근태', '200001011', '2022-11-28 09:00:00', '2022-11-28 18:00:00', '2022-11-28'),
       ('정상 근태', '200001011', '2022-11-29 09:00:00', '2022-11-29 18:00:00', '2022-11-29'),
       ('정상 근태', '200001011', '2022-11-30 09:00:00', '2022-11-30 18:00:00', '2022-11-30'),

       ('이상 근태(조기 퇴근)', '200001011', '2022-12-01 09:00:00', '2022-12-01 15:00:00', '2022-12-01'),
       ('정상 근태', '200001011', '2022-12-02 09:00:00', '2022-12-02 18:00:00', '2022-12-02'),
       ('정상 근태', '200001011', '2022-12-05 09:00:00', '2022-12-05 18:00:00', '2022-12-05'),
       ('정상 근태', '200001011', '2022-12-06 09:00:00', '2022-12-06 18:00:00', '2022-12-06'),
       ('정상 근태', '200001011', '2022-12-07 09:00:00', '2022-12-07 18:00:00', '2022-12-07'),
       ('정상 근태', '200001011', '2022-12-08 09:00:00', '2022-12-08 18:00:00', '2022-12-08'),
       ('이상 근태(조기 퇴근)', '200001011', '2022-12-09 09:00:00', '2022-12-09 15:00:00', '2022-12-09'),

       ('정상 근태', '200001011', '2022-12-13 09:00:00', '2022-12-13 18:00:00', '2022-12-13'),

       ('정상 근태', '200001011', '2022-12-15 09:00:00', '2022-12-15 18:00:00', '2022-12-15'),
       ('정상 근태', '200001011', '2022-12-16 09:00:00', '2022-12-16 18:00:00', '2022-12-16'),
       ('정상 근태', '200001011', '2022-12-19 09:00:00', '2022-12-19 18:00:00', '2022-12-19'),
       ('정상 근태', '200001011', '2022-12-20 09:00:00', '2022-12-20 18:00:00', '2022-12-20'),
       ('이상 근태(지각, 조기 퇴근)', '200001011', '2022-12-21 10:00:00', '2022-12-21 17:00:00', '2022-12-21'),
       ('이상 근태(지각)', '200001011', '2022-12-22 10:00:00', '2022-12-22 18:00:00', '2022-12-22'),
       ('정상 근태', '200001011', '2022-12-23 09:00:00', '2022-12-23 18:00:00', '2022-12-23'),
       ('이상 근태(지각)', '200001011', '2022-12-26 13:00:00', '2022-12-26 18:00:00', '2022-12-26'),
       ('이상 근태(지각, 조기 퇴근)', '200001011', '2022-12-27 11:00:00', '2022-12-27 16:00:00', '2022-12-27'),
       ('정상 근태', '200001011', '2022-12-28 09:00:00', '2022-12-28 18:00:00', '2022-12-28'),
       ('정상 근태', '200001011', '2022-12-29 09:00:00', '2022-12-29 18:00:00', '2022-12-29'),
       ('정상 근태', '200001011', '2022-12-30 09:00:00', '2022-12-30 18:00:00', '2022-12-30');



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



# INSERT INTO attendance_approval (attendance_info_id, attendance_approval_date, employee_id)
# VALUES (15, "2023-11-26T02:56:55", '200001013');
# INSERT INTO attendance_approval (attendance_info_id, attendance_approval_date, employee_id)
# VALUES (16, NOW(), '200001013');
# INSERT INTO attendance_approval (attendance_info_id, attendance_approval_date, employee_id)
# VALUES (18, "2023-11-26T02:56:56", '200001013');
# INSERT INTO attendance_approval (attendance_info_id, attendance_approval_date, employee_id)
# VALUES (19, NOW(), '200001013');


INSERT INTO attendance_appeal_request
(status, reason, attendance_info_id, appealed_start_time, appealed_end_time, employee_id,
 attendance_appeal_request_time, reason_for_rejection)
VALUES ('조정 요청 중', '이상 근태(지각)으로 인한 근태 조정 요청', 1, '2023-11-03 09:10:00', '2023-11-03 18:10:00', '200001011', '2023-11-27T15:11:07',
        NULL),
       ('조정 요청 반려', '조기 퇴근에 대한 조정 요청', 2, '2023-11-04 08:50:00', '2023-11-04 16:00:00', '200001012', NOW(),
        '정당한 이유 없음'),
       ('조정 요청 승인', '병가로 인한 전일 근무시간 조정', 3, '2023-11-05 00:00:00', '2023-11-05 00:00:00', '200001013', NOW(), NULL),
       ('조정 요청 중', '외출로 인한 근태 조정 요청', 4, '2023-11-06 08:30:00', '2023-11-06 15:30:00', '200001014', NOW(), NULL),
       ('조정 요청 중', '야근으로 인한 다음날 이상 근태(지각) 요청', 24, '2023-11-12 10:00:00', '2023-11-12 18:00:00', '200001011', '2023-11-1 18:05:00',
        NULL);
#
# INSERT INTO douzone_test.notification_message (message_id, receiver, message, receive_time, read_time, link_to, identifier, for_manager) VALUES (1, '200001011', '1st', '2023-11-23 20:17:45', null, 'appeal', '1', 0);
# INSERT INTO douzone_test.notification_message (message_id, receiver, message, receive_time, read_time, link_to, identifier, for_manager) VALUES (2, '200001011', '2nd', '2023-11-23 20:17:45', null, 'appeal', '1', 0);
# INSERT INTO douzone_test.notification_message (message_id, receiver, message, receive_time, read_time, link_to, identifier, for_manager) VALUES (3, '200001011', '3rd', '2023-11-23 20:17:45', null, 'appeal', '1', 0);
# INSERT INTO douzone_test.notification_message (message_id, receiver, message, receive_time, read_time, link_to, identifier, for_manager) VALUES (4, '200001011', '4th', '2023-11-23 20:17:45', null, 'appeal', '1', 0);