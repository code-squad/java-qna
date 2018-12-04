INSERT INTO USER (ID, USER_ID, password, name, email) VALUES (1, 'Dom', 'test', 'dom', 'sechun0215@slipp.net');
INSERT INTO USER (id, user_id, password, name, email) VALUES (2, 'sehun', 'test', 'sehun', 'ksh1564@slipp.net');

INSERT INTO QUESTION (id, user_id, title, contents, create_date, deleted) VALUES (1, 2, 'test', 'test contents', current_timestamp(), false);
INSERT INTO QUESTION (id, user_id, title, contents, create_date, deleted) VALUES (2, 1, 'test2', 'test contents2', current_timestamp(), false);
INSERT INTO QUESTION (id, user_id, title, contents, create_date, deleted) VALUES (3, 2, 'test3', 'test contents3', current_timestamp(), false);
INSERT INTO QUESTION (id, user_id, title, contents, create_date, deleted) VALUES (4, 1, 'test4', 'test contents4', current_timestamp(), false);
INSERT INTO QUESTION (id, user_id, title, contents, create_date, deleted) VALUES (5, 2, 'test5', 'test contents5', current_timestamp(), false);
INSERT INTO QUESTION (id, user_id, title, contents, create_date, deleted) VALUES (6, 1, 'test6', 'test contents6', current_timestamp(), false);
INSERT INTO QUESTION (id, user_id, title, contents, create_date, deleted) VALUES (7, 2, 'test7', 'test contents7', current_timestamp(), false);
INSERT INTO QUESTION (id, user_id, title, contents, create_date, deleted) VALUES (8, 1, 'test8', 'test contents8', current_timestamp(), false);
INSERT INTO QUESTION (id, user_id, title, contents, create_date, deleted) VALUES (9, 2, 'test9', 'test contents9', current_timestamp(), false);
INSERT INTO QUESTION (id, user_id, title, contents, create_date, deleted) VALUES (10, 1, 'test10', 'test contents10', current_timestamp(), false);
INSERT INTO QUESTION (id, user_id, title, contents, create_date, deleted) VALUES (11, 2, 'test11', 'test contents11', current_timestamp(), false);
INSERT INTO QUESTION (id, user_id, title, contents, create_date, deleted) VALUES (12, 1, 'test12', 'test contents12', current_timestamp(), false);
INSERT INTO QUESTION (id, user_id, title, contents, create_date, deleted) VALUES (13, 2, 'test13', 'test contents13', current_timestamp(), false);
INSERT INTO QUESTION (id, user_id, title, contents, create_date, deleted) VALUES (14, 1, 'test14', 'test contents14', current_timestamp(), false);
INSERT INTO QUESTION (id, user_id, title, contents, create_date, deleted) VALUES (15, 2, 'test15', 'test contents15', current_timestamp(), false);
INSERT INTO QUESTION (id, user_id, title, contents, create_date, deleted) VALUES (16, 1, 'test16', 'test contents16', current_timestamp(), false);
INSERT INTO QUESTION (id, user_id, title, contents, create_date, deleted) VALUES (17, 2, 'test17', 'test contents17', current_timestamp(), false);
INSERT INTO QUESTION (id, user_id, title, contents, create_date, deleted) VALUES (18, 1, 'test18', 'test contents18', current_timestamp(), false);
INSERT INTO QUESTION (id, user_id, title, contents, create_date, deleted) VALUES (19, 2, 'test19', 'test contents19', current_timestamp(), false);
INSERT INTO QUESTION (id, user_id, title, contents, create_date, deleted) VALUES (20, 1, 'test20', 'test contents20', current_timestamp(), false);



INSERT INTO ANSWER (id, question_id, user_id, contents, create_date, deleted) VALUES (1, 1, 2, 'Non Deleted', current_timestamp(), false);
--INSERT INTO ANSWER (id, question_id, user_id, contents, deleted) VALUES (2, 1, 1, 'Deleted', true);
INSERT INTO ANSWER (id, question_id, user_id, contents, create_date, deleted) VALUES (3, 1, 1, 'Non Deleted', current_timestamp(), false);
INSERT INTO ANSWER (id, question_id, user_id, contents, create_date, deleted) VALUES (4, 1, 2, 'Deleted', current_timestamp(), true);
INSERT INTO ANSWER (id, question_id, user_id, contents, create_date, deleted) VALUES (5, 1, 2, 'Non Deleted', current_timestamp(), false);
INSERT INTO ANSWER (id, question_id, user_id, contents, create_date, deleted) VALUES (6, 1, 2, 'Non Deleted', current_timestamp(), false);
