insert into employee
values ('200001011', 'test', 'test', false, '2000-01-01');
insert into employee
values ('200001012', 'test', 'test', true, '2000-01-01');
insert into admin
values ('admin', 'admin');
# insert into auth values ('200001011','192.168.0.1',NOW(),null);
insert into vacation_request_state_category
values ('requested');
insert into vacation_request_state_category
values ('permitted');
insert into vacation_request_state_category
values ('rejected');
insert into vacation_category
values ('normal', 1);
# insert into douzone_test.vacation_request (vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity, vacation_start_date, vacation_end_date, reason, vacation_request_time, reason_for_rejection)
# values  ( 'normal', '200001012', 'requested', 1, '2023-10-28', '2023-10-29', 'dddd', '2023-10-30 15:32:15', 'fdddd');