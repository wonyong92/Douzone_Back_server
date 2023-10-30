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
#사원조회
select * from employee;


INSERT INTO attendance_status_category (`key`)
VALUES ('출근');
INSERT INTO attendance_status_category (`key`)
VALUES ('근태이상');
INSERT INTO attendance_status_category (`key`)
VALUES ('결근');
INSERT INTO attendance_status_category (`key`)
VALUES ('지각');

SELECT * FROM attendance_status_category;




#사원 출근 퇴근 정보 예시데이터
INSERT INTO attendance_info (attendance_status_category, employee_id, start_time, end_time, attendance_date) VALUES
                                                                                                                 ('근태이상', 'emp01', '2023-01-01 09:00:00', '2023-01-01 18:00:00', '2023-01-01'),
                                                                                                                 ('근태이상', 'emp01', '2023-02-01 09:00:00', '2023-02-01 18:00:00', '2023-02-01'),
                                                                                                                 ('출근', 'emp02', '2023-01-02 09:00:00', '2023-01-02 18:00:00', '2023-01-02'),
                                                                                                                 ('결근', 'emp02', '2023-02-02 09:00:00', '2023-02-02 18:00:00', '2023-02-02'),
                                                                          ('출근', 'emp03', '2023-01-03 09:00:00', '2023-01-03 18:00:00', '2023-01-03'),
                                                                                                                 ('출근', 'emp03', '2023-02-03 09:00:00', '2023-02-03 18:00:00', '2023-02-03'),
                                                                                                                 ('근태이상', 'emp04', '2023-01-04 09:00:00', '2023-01-04 18:00:00', '2023-01-04'),
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


select *
from attendance_info;

select * from attendance_approval;
SELECT * FROM attendance_status_category WHERE `key` = '지각';

select *
from attendance_approval;

select attendance_approval_date,employee_id from attendance_approval;


INSERT INTO attendance_approval (attendance_info_id, attendance_approval_date, employee_id)
VALUES (1, NOW(), 'emp01');

select * from  attendance_approval;
SELECT
    e.employee_id,
    e.name,
    a.attendance_approval_date
FROM
    attendance_approval a
        JOIN
    employee e ON a.employee_id = e.employee_id
WHERE
        e.employee_id = 'emp01';


UPDATE attendance_info
SET attendance_status_category = '지각'
WHERE employee_id = 'emp09' AND attendance_date = '2023-01-09';


select * from attendance_info;

INSERT INTO attendance_approval(attendance_info_id)



SELECT * from attendance_info;
#이건 출퇴근시간 데이터 적용해볼 친구
INSERT INTO attendance_info (attendance_status_category, employee_id, attendance_date) VALUES
                                                                                           ('출근', 'emp01', '2023-01-01'),
                                                                                           ('출근', 'emp01', '2023-02-01'),
                                                                                           ('출근', 'emp02', '2023-01-02'),
                                                                                           ('출근', 'emp02', '2023-02-02'),
                                                                                           ('출근', 'emp03', '2023-01-03'),
                                                                                           ('출근', 'emp03', '2023-02-03'),
                                                                                           ('출근', 'emp04', '2023-01-04'),
                                                                                           ('출근', 'emp04', '2023-02-04'),
                                                                                           ('출근', 'emp05', '2023-01-05'),
                                                                                           ('출근', 'emp05', '2023-02-05'),
                                                                                           ('출근', 'emp06', '2023-01-06'),
                                                                                           ('출근', 'emp06', '2023-02-06'),
                                                                                           ('출근', 'emp07', '2023-01-07'),
                                                                                           ('출근', 'emp07', '2023-02-07'),
                                                                                           ('출근', 'emp08', '2023-01-08'),
                                                                                           ('출근', 'emp08', '2023-02-08'),
                                                                                           ('출근', 'emp09', '2023-01-09'),
                                                                                           ('출근', 'emp09', '2023-02-09'),
                                                                                           ('출근', 'emp10', '2023-01-10');
select * from attendance_info;



