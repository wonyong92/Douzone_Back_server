insert into employee values ('test','test','test','0',"2020-01-01");


INSERT INTO employee (employee_id, password, name, attendance_manager, hire_year) VALUES

                                                                                      ('emp02', 'pass3344', '김김아', 1, '2021-01-01');

select *
from employee;

INSERT INTO attendance_status_category (`key`) VALUES ('출근');

select *
from attendance_status_category;


# INSERT INTO attendance_info (attendance_status_category, employee_id) VALUES('출근','emp002');
# INSERT INTO attendance_info (attendance_status_category, employee_id) VALUES('출근','emp001');
# INSERT INTO attendance_info (attendance_status_category, employee_id) VALUES('출근','emp007');
# INSERT INTO attendance_info (attendance_status_category, employee_id) VALUES('출근','emp005');
# INSERT INTO attendance_info (attendance_status_category, employee_id) VALUES('출근','emp0010');
INSERT INTO attendance_info (attendance_status_category, employee_id) VALUES('출근','emp02');




select *
from attendance_info;

# UPDATE attendance_info
# SET start_time = NOW()
# WHERE employee_id = 'emp001';

UPDATE attendance_info
SET end_time = now()
WHERE employee_id = 'emp01';


select *
from attendance_info;
