insert into employee values ('test','test','test','0',"2020-01-01");


INSERT INTO employee (employee_id, password, name, attendance_manager, hire_year) VALUES
                                                                                      ('emp001', 'pass1234', '김철수', 0, '2018-01-01'),
                                                                                      ('emp002', 'pass5678', '이영희', 1, '2019-01-01'),
                                                                                      ('emp003', 'pass9101', '박진영', 0, '2020-01-01'),
                                                                                      ('emp004', 'pass1122', '최유나', 0, '2017-01-01'),

                                                                                      ('emp006', 'pass3344', '정태욱', 1, '2021-01-01');
select *
from employee;

INSERT INTO attendance_status_category (`key`) VALUES ('출근');

select *
from attendance_status_category;


INSERT INTO attendance_info (attendance_status_category, employee_id) VALUES('출근','emp003');
INSERT INTO attendance_info (attendance_status_category, employee_id) VALUES('출근','emp001');
INSERT INTO attendance_info (attendance_status_category, employee_id) VALUES('출근','emp002');


select *
from attendance_info;

# UPDATE attendance_info
# SET start_time = NOW()
# WHERE employee_id = 'emp001';


select *
from attendance_info;
