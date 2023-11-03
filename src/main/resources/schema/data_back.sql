-- insert into employee values ('123','123','name1',false,'2023-01-01 ');
-- insert into employee values ('456','456','name2',false,'2023-01-02');
-- insert into vacation_category values("a",2);
-- insert into vacation_request(vacation_category_key, employee_id, result, vacation_quantity, vacation_start_time, vacation_end_time, vacation_related_start_time, vacation_related_end_time, vacation_request_time, reason) values("a","123","a",1,"2023-01-01","2023-01-10","09:00:00","09:00:00","2023-01-01 18:00:00","a");

insert into employee values ("1234",'testpw','name1',false,'2023-01-01');
insert into employee values ("5678",'testpw2','name2',false,'2023-01-01');
insert into employee values ("234",'testpw3','name3',false,'2023-01-01');
insert into employee values ("345",'testpw4','name4',false,'2023-01-01');
insert into employee values ("456",'testpw5','name5',false,'2023-01-01');
insert into employee values ("567",'testpw6','name6',false,'2023-01-01');
insert into employee values ("678",'testpw7','name7',false,'2023-01-01');
insert into employee values ("789",'testpw8','name8',false,'2023-01-01');
insert into employee values ("890",'testpw9','name9',false,'2023-01-01');
insert into employee values ("4567",'testpw10','name10',false,'2023-01-01');
insert into employee values ("651",'testpw11','name11',false,'2023-01-01');




insert into vacation_category values("a",2);
insert into vacation_request_state_category values ("승인");
insert into vacation_request_state_category values ("반려");
insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity, vacation_start_date, vacation_end_date,  vacation_request_time, reason) values("a","1234","승인",1,"2023-01-01","2023-01-10","2023-01-01 15:00:00","a");
insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity, vacation_start_date, vacation_end_date,  vacation_request_time, reason) values("a","5678","승인",1,"2023-01-01","2023-01-10","2023-01-01 16:00:00","a");
insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity, vacation_start_date, vacation_end_date,  vacation_request_time, reason) values("a","1234","승인",1,"2023-03-01","2023-03-10","2023-03-01 15:00:00","a");
insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity, vacation_start_date, vacation_end_date,  vacation_request_time, reason) values("a","5678","승인",1,"2023-03-01","2023-03-10","2023-03-01 15:00:00","a");
insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity, vacation_start_date, vacation_end_date,  vacation_request_time, reason,reason_for_rejection) values("a","1234","반려",1,"2023-05-01","2023-05-10","2023-05-01 15:00:00","a","안돼 돌아가");
insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity, vacation_start_date, vacation_end_date,  vacation_request_time, reason,reason_for_rejection) values("a","5678","반려",1,"2023-05-01","2023-05-10","2023-05-01 15:00:00","a","안돼 돌아가요");
insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity, vacation_start_date, vacation_end_date,  vacation_request_time, reason,reason_for_rejection) values("a","1234","반려",1,"2023-06-01","2023-06-10","2023-06-01 15:00:00","a","안돼 돌아가");
insert into vacation_request(vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity, vacation_start_date, vacation_end_date,  vacation_request_time, reason,reason_for_rejection) values("a","5678","반려",1,"2023-07-01","2023-07-10","2023-07-01 15:00:00","a","안돼 돌아가요");

insert into vacation_quantity_setting(freshman,senior,setting_time,employee_id) values(11,14,"2023-10-26 09:00:00","1234");
/*insert into vacation_quantity_setting(freshman,senior,setting_time,employee_id) values(12,14,"2023-10-27 09:00:00","1234");
insert into vacation_quantity_setting(freshman,senior,setting_time,employee_id) values(13,24,"2023-10-28 09:00:00","1234");
insert into vacation_quantity_setting(freshman,senior,setting_time,employee_id) values(14,24,"2023-10-29 09:00:00","5678");
insert into vacation_quantity_setting(freshman,senior,setting_time,employee_id) values(15,24,"2023-10-16 09:00:00","1234");
insert into vacation_quantity_setting(freshman,senior,setting_time,employee_id) values(16,24,"2023-10-6 09:00:00","1234");
insert into vacation_quantity_setting(freshman,senior,setting_time,employee_id) values(17,24,"2023-10-1 09:00:00","1234");
insert into vacation_quantity_setting(freshman,senior,setting_time,employee_id) values(18,24,"2023-10-2 09:00:00","1234");
insert into vacation_quantity_setting(freshman,senior,setting_time,employee_id) values(19,24,"2023-10-3 09:00:00","1234");
insert into vacation_quantity_setting(freshman,senior,setting_time,employee_id) values(21,24,"2023-10-4 09:00:00","1234");
insert into vacation_quantity_setting(freshman,senior,setting_time,employee_id) values(21,34,"2023-10-5 09:00:00","1234");
insert into vacation_quantity_setting(freshman,senior,setting_time,employee_id) values(21,44,"2023-10-7 09:00:00","1234");
insert into vacation_quantity_setting(freshman,senior,setting_time,employee_id) values(20,24,"2023-10-8 09:00:00","5678");
*/

INSERT INTO regular_time_adjustment_history(TARGET_DATE, adjusted_start_time,adjusted_end_time,reason,regular_time_adjustment_time,employee_id) values("2023-01-01","09:00:00","16:00:00","집에 일찍 가고싶어서","2022-12-31 09:00:00","1234");
INSERT INTO regular_time_adjustment_history(TARGET_DATE, adjusted_start_time,adjusted_end_time,reason,regular_time_adjustment_time,employee_id) values("2023-02-01","09:00:00","16:00:00","집에 일찍 가고싶어서","2022-12-31 09:00:00","1234");
INSERT INTO regular_time_adjustment_history(TARGET_DATE, adjusted_start_time,adjusted_end_time,reason,regular_time_adjustment_time,employee_id) values("2023-03-01","09:00:00","16:00:00","집에 일찍 가고싶어서","2022-12-31 09:00:00","1234");
INSERT INTO regular_time_adjustment_history(TARGET_DATE, adjusted_start_time,adjusted_end_time,reason,regular_time_adjustment_time,employee_id) values("2023-04-01","09:00:00","16:00:00","집에 일찍 가고싶어서","2022-12-31 09:00:00","1234");
INSERT INTO regular_time_adjustment_history(TARGET_DATE, adjusted_start_time,adjusted_end_time,reason,regular_time_adjustment_time,employee_id) values("2023-05-01","09:00:00","16:00:00","집에 일찍 가고싶어서","2022-12-31 09:00:00","1234");
INSERT INTO regular_time_adjustment_history(TARGET_DATE, adjusted_start_time,adjusted_end_time,reason,regular_time_adjustment_time,employee_id) values("2023-06-01","09:00:00","16:00:00","집에 일찍 가고싶어서","2022-12-31 09:00:00","1234");
INSERT INTO regular_time_adjustment_history(TARGET_DATE, adjusted_start_time,adjusted_end_time,reason,regular_time_adjustment_time,employee_id) values("2023-07-01","09:00:00","16:00:00","집에 일찍 가고싶어서","2022-12-31 09:00:00","1234");
INSERT INTO regular_time_adjustment_history(TARGET_DATE, adjusted_start_time,adjusted_end_time,reason,regular_time_adjustment_time,employee_id) values("2023-08-01","09:00:00","16:00:00","집에 일찍 가고싶어서","2022-12-31 09:00:00","1234");
INSERT INTO regular_time_adjustment_history(TARGET_DATE, adjusted_start_time,adjusted_end_time,reason,regular_time_adjustment_time,employee_id) values("2023-09-01","09:00:00","16:00:00","집에 일찍 가고싶어서","2022-12-31 09:00:00","1234");
INSERT INTO regular_time_adjustment_history(TARGET_DATE, adjusted_start_time,adjusted_end_time,reason,regular_time_adjustment_time,employee_id) values("2023-10-01","09:00:00","16:00:00","집에 일찍 가고싶어서","2022-12-31 09:00:00","1234");
INSERT INTO regular_time_adjustment_history(TARGET_DATE, adjusted_start_time,adjusted_end_time,reason,regular_time_adjustment_time,employee_id) values("2023-11-01","09:00:00","16:00:00","집에 일찍 가고싶어서","2022-12-31 09:00:00","1234");
INSERT INTO regular_time_adjustment_history(TARGET_DATE, adjusted_start_time,adjusted_end_time,reason,regular_time_adjustment_time,employee_id) values("2023-12-01","09:00:00","16:00:00","집에 일찍 가고싶어서","2022-12-31 09:00:00","5678");
