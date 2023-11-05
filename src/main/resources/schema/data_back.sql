INSERT INTO employee (employee_id, password, name, attendance_manager, hire_year) VALUES
                                                                                      ('emp01', 'pass01', '사원1', true, '2022-01-01'),
                                                                                      ('emp02', 'pass02', '사원2', false, '2022-02-01'),
                                                                                      ('emp03', 'pass03', '사원3', true, '2022-03-01'),
                                                                                      ('emp04', 'pass04', '사원4', false, '2022-04-01'),
                                                                                      ('emp05', 'pass05', '사원5', false, '2022-05-01'),
                                                                                      ('emp06', 'pass06', '사원6', true, '2022-06-01'),
                                                                                      ('emp07', 'pass07', '사원7', false, '2022-07-01'),
                                                                                      ('emp08', 'pass08', '사원8', false, '2022-08-01'),
                                                                                      ('emp09', 'pass09', '사원9', false, '2022-09-01'),
                                                                                   ('emp10', 'pass10', '사원10', false, '2022-10-01');


select * from employee;
select * from attendance_status_category;

INSERT INTO attendance_status_category (`key`)
VALUES
    ('출근'),
    ('근태이상'),
    ('결근'),
    ('지각'),
    ('미지정상태');

# INSERT INTO attendance_info (attendance_status_category, employee_id, attendance_date, start_time)
# VALUES ('미지정상태', 'emp01', CURDATE(), 'start_time')
# ON DUPLICATE KEY UPDATE start_time = VALUES(start_time);

# INSERT INTO attendance_info (employee_id, attendance_date, start_time)
# VALUES ('emp01', CURDATE(), '2023-11-01 08:40:00')
# ON DUPLICATE KEY UPDATE start_time = '2023-11-01 08:30:00';

# INSERT INTO attendance_info (employee_id, attendance_date)
# VALUES ('emp01', CURDATE());
#
# INSERT INTO attendance_info (employee_id, attendance_date)
# VALUES ('emp02', CURDATE());
#
# # INSERT INTO attendance_info (employee_id, attendance_date)
# # VALUES ('emp03', CURDATE());
#
# INSERT INTO attendance_info (employee_id, attendance_date)
# VALUES ('emp04', CURDATE());
#
# INSERT INTO attendance_info (employee_id, attendance_date)
# VALUES ('emp05', CURDATE());



#사원조회
# select * from attendance_info;
# INSERT INTO attendance_info (employee_id, attendance_date)
# VALUES ('emp03', '2023-10-31 09:00:00');

# #사원 출근 퇴근 정보 예시데이터
INSERT INTO attendance_info (attendance_status_category, employee_id, start_time, end_time, attendance_date) VALUES
                                                                                                                 ('근태이상', 'emp01', '2023-01-01 09:00:00', '2023-01-01 18:00:00', '2023-01-01'),
                                                                                                                 ('근태이상', 'emp01', '2023-02-01 09:00:00', '2023-02-01 18:00:00', '2023-02-01'),
                                                                                                                 ('근태이상', 'emp01', '2023-02-01 09:00:00', '2023-02-01 18:00:00', '2023-02-03'),
                                                                                                                 ('근태이상', 'emp01', '2023-02-01 09:00:00', '2023-02-01 18:00:00', '2023-02-04'),
                                                                                                                 ('근태이상', 'emp01', '2023-02-01 09:00:00', '2023-02-01 18:00:00', '2023-02-05'),
                                                                                                                 ('출근', 'emp02', '2023-01-02 09:00:00', '2023-01-02 18:00:00', '2023-01-02'),
                                                                                                                 ('결근', 'emp02', '2023-02-02 09:00:00', '2023-02-02 18:00:00', '2023-02-02'),
                                                                          ('출근', 'emp03', '2023-01-03 09:00:00', '2023-01-03 18:00:00', '2023-01-03'),
                                                                                                                 ('출근', 'emp03', '2023-02-03 09:00:00', '2023-02-03 18:00:00', '2023-02-03'),
                                                                                                                 ('근태이상', 'emp04', '2023-01-04 09:00:00', '2023-01-04 18:00:00', '2023-01-04'),
                                                                                                                 ('출근', 'emp03', '2023-02-03 09:00:00', '2023-02-03 18:00:00', '2023-02-05'),
                                                                                                                 ('출근', 'emp03', '2023-02-03 09:00:00', '2023-02-03 18:00:00', '2023-02-06'),
                                                                                                                 ('근태이상', 'emp04', '2023-02-04 09:00:00', '2023-02-04 18:00:00', '2023-02-04'),
                                                                                                                 ('근태이상', 'emp05', '2023-01-05 09:00:00', '2023-01-05 18:00:00', '2023-01-05'),
                                                                                                                 ('출근', 'emp05', '2023-02-05 09:00:00', '2023-02-05 18:00:00', '2023-02-05'),
                                                                                                                 ('근태이상', 'emp06', '2023-01-06 09:00:00', '2023-01-06 18:00:00', '2023-01-06'),
                                                                                                                 ('출근', 'emp06', '2023-02-06 09:00:00', '2023-02-06 18:00:00', '2023-02-06'),
                                                                                                                 ('출근', 'emp07', '2023-01-07 09:00:00', '2023-01-07 18:00:00', '2023-01-07'),
                                                                                                                 ('출근', 'emp07', '2023-02-07 09:00:00', '2023-02-07 18:00:00', '2023-02-07'),
                                                                                                                 ('결근', 'emp08', '2023-01-08 09:00:00', '2023-01-08 18:00:00', '2023-01-08'),
                                                                                                                 ('출근', 'emp08', '2023-02-08 09:00:00', '2023-02-08 18:00:00', '2023-02-08'),
                                                                                                                 ('결근', 'emp09', '2023-01-09 09:00:00', '2023-01-09 18:00:00', '2023-01-09'),
                                                                                                                 ('출근', 'emp09', '2023-02-09 09:00:00', '2023-02-09 18:00:00', '2023-02-09'),

                                                                                                      ('출근', 'emp10', '2023-01-10 09:00:00', '2023-01-10 18:00:00', '2023-01-10');

select * from attendance_info;

SELECT regular_time_adjustment_history_id, target_date, adjusted_start_time, adjusted_end_time, reason, regular_time_adjustment_time, employee_id FROM regular_time_adjustment_history WHERE employee_id = 'emp01' AND target_date = '11/03/2023 00:00:00.000';

select * from regular_time_adjustment_history;

select *
from attendance_approval;
#
INSERT INTO attendance_appeal_request
(status, reason, attendance_info_id, appealed_start_time, appealed_end_time, employee_id, attendance_appeal_request_time, reason_for_rejection)
VALUES
    ('PENDING', '지각으로 인한 근태 조정 요청', 1, '2023-11-03 09:10:00', '2023-11-03 18:10:00', 'emp01', NOW(), NULL),
    ('REJECTED', '조기 퇴근에 대한 조정 요청', 2, '2023-11-04 08:50:00', '2023-11-04 16:00:00', 'emp02', NOW(), '정당한 이유 없음'),
    ('APPROVED', '병가로 인한 전일 근무시간 조정', 3, '2023-11-05 00:00:00', '2023-11-05 00:00:00', 'emp03', NOW(), NULL),
    ('PENDING', '외출로 인한 근태 조정 요청', 4, '2023-11-06 08:30:00', '2023-11-06 15:30:00', 'emp04', NOW(), NULL),
    ('APPROVED', '업무상 필요에 의한 조정', 5, '2023-11-07 10:30:00', '2023-11-07 19:30:00', 'emp05', NOW(), NULL),
    ('REJECTED', '개인 사유에 의한 근태 조정 요청', 6, '2023-11-08 09:00:00', '2023-11-08 17:00:00', 'emp06', NOW(), '근거 불충분'),
    ('PENDING', '교통사고로 인한 조정 요청', 7, '2023-11-09 08:45:00', '2023-11-09 17:45:00', 'emp07', NOW(), NULL),
    ('APPROVED', '대중교통 지연에 따른 출근시간 조정', 8, '2023-11-10 08:40:00', '2023-11-10 17:40:00', 'emp08', NOW(), NULL),
    ('REJECTED', '예상치 못한 개인 사정으로 인한 조정 요청', 9, '2023-11-11 08:55:00', '2023-11-11 17:55:00', 'emp09', NOW(), '증빙 자료 미제출'),
    ('PENDING', '아동 돌봄으로 인한 조정 요청', 10, '2023-11-12 09:05:00', '2023-11-12 18:05:00', 'emp10', NOW(), NULL);



select *
from attendance_appeal_request;

INSERT INTO employee (employee_id, password, name, attendance_manager, hire_year)
VALUES
    ('1001', 'pass123', '김철수', false, '2023-01-10'),
    ('1002', 'qwerty', '박영희', true, '2022-02-20'),
    ('1003', '123abc', '이민준', false, '2021-03-15'),
    ('1004', 'pass456', '최지은', false, '2020-04-08'),
    ('1005', 'azerty', '황보경', true, '2019-05-21'),
    ('1006', 'pass789', '김하준', false, '2018-06-30'),
    ('1007', '789xyz', '손서연', false, '2017-07-14'),
    ('1008', 'pass000', '정우성', true, '2016-08-23'),
    ('1009', 'abc123', '한지민', false, '2015-09-01'),
    ('1010', '654321', '박보검', false, '2014-10-18');

select *
from employee ;

# INSERT INTO regular_time_adjustment_history
# (target_date, adjusted_start_time, adjusted_end_time, reason, regular_time_adjustment_time, employee_id)
# VALUES
#     ('2023-11-01', '09:00:00', '18:00:00', '근무 시간 조정', '2023-11-01 09:00:00', 'emp01');

#
#
# select *
# from attendance_info;
#
# select * from attendance_approval;
# SELECT * FROM attendance_status_category WHERE `key` = '지각';
#
# select *
# from attendance_approval;
#
# select attendance_approval_date,employee_id from attendance_approval;
#
#
# INSERT INTO attendance_approval (attendance_info_id, attendance_approval_date, employee_id)
# VALUES (1, NOW(), 'emp01');
#
# select * from  attendance_approval;
# SELECT
#     e.employee_id,
#     e.name,
#     a.attendance_approval_date
# FROM
#     attendance_approval a
#         JOIN
#     employee e ON a.employee_id = e.employee_id
# WHERE
#         e.employee_id = 'emp01';
#
#
# UPDATE attendance_info
# SET attendance_status_category = '지각'
# WHERE employee_id = 'emp09' AND attendance_date = '2023-01-09';
#
#
# select * from attendance_info;
#
# INSERT INTO attendance_approval(attendance_info_id)
#
#
#
# SELECT * from attendance_info;
# #이건 출퇴근시간 데이터 적용해볼 친구
# INSERT INTO attendance_info (attendance_status_category, employee_id, attendance_date) VALUES
#                                                                                            ('출근', 'emp01', '2023-01-01'),
#                                                                                            ('출근', 'emp01', '2023-02-01'),
#                                                                                            ('출근', 'emp02', '2023-01-02'),
#                                                                                            ('출근', 'emp02', '2023-02-02'),
#                                                                                            ('출근', 'emp03', '2023-01-03'),
#                                                                                            ('출근', 'emp03', '2023-02-03'),
#                                                                                            ('출근', 'emp04', '2023-01-04'),
#                                                                                            ('출근', 'emp04', '2023-02-04'),
#                                                                                            ('출근', 'emp05', '2023-01-05'),
#                                                                                            ('출근', 'emp05', '2023-02-05'),
#                                                                                            ('출근', 'emp06', '2023-01-06'),
#                                                                                            ('출근', 'emp06', '2023-02-06'),
#                                                                                            ('출근', 'emp07', '2023-01-07'),
#                                                                                            ('출근', 'emp07', '2023-02-07'),
#                                                                                            ('출근', 'emp08', '2023-01-08'),
#                                                                                            ('출근', 'emp08', '2023-02-08'),
#                                                                                            ('출근', 'emp09', '2023-01-09'),
#                                                                                            ('출근', 'emp09', '2023-02-09'),
#                                                                                            ('출근', 'emp10', '2023-01-10');
# select * from attendance_info;
#
#
#
