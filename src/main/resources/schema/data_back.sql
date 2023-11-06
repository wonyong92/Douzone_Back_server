insert into employee
values ('200001011', 'test', 'test1', false, '2000-01-01');
insert into employee
values ('200001012', 'test', 'test2', true, '2000-01-01');
insert into employee
values ('200001013', 'test', 'test3', true, '2000-01-01');
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
insert into douzone_test.attendance_appeal_request_status (attendance_appeal_request_status_key)
values ('permitted'),
       ('rejected'),
       ('requested');
insert into attendance_status_category
values ('abnormal'),
       ('pending'),
       ('normal');
insert into douzone_test.attendance_info (attendance_info_id, attendance_status_category, employee_id, start_time,
                                          end_time, attendance_date)
values (1, 'abnormal', '200001012', '2023-10-31 09:24:57', '2023-10-31 16:24:59', '2023-10-31');
insert into douzone_test.attendance_appeal_request (status, reason, attendance_info_id, appealed_start_time,
                                                    appealed_end_time, employee_id, attendance_appeal_request_time,
                                                    reason_for_rejection, attendance_appeal_request_id)
values ('requested', 'dddd', 1, '14:17:49', '16:17:58', '200001011', '2023-10-31 16:18:10', null, 1);