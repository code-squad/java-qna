INSERT INTO USER (ID, USER_ID, password, name, email) VALUES (1, 'Dom', 'test', 'dom', 'sechun0215@slipp.net');
INSERT INTO USER (id, user_id, password, name, email) VALUES (2, 'sehun', 'test', 'sehun', 'ksh1564@slipp.net');

INSERT INTO QUESTION (id, user_id, title, contents, create_date, deleted) VALUES (1, 2, 'test', 'test contents', current_timestamp(), false);
INSERT INTO QUESTION (id, user_id, title, contents, create_date, deleted) VALUES (2, 1, 'test2', 'test contents2', current_timestamp(), false);


INSERT INTO ANSWER (id, question_id, user_id, contents, deleted) VALUES (1, 1, 2, 'Non Deleted', false);
--INSERT INTO ANSWER (id, question_id, user_id, contents, deleted) VALUES (2, 1, 1, 'Deleted', true);
INSERT INTO ANSWER (id, question_id, user_id, contents, deleted) VALUES (3, 1, 1, 'Non Deleted', false);
INSERT INTO ANSWER (id, question_id, user_id, contents, deleted) VALUES (4, 1, 2, 'Deleted', true);
INSERT INTO ANSWER (id, question_id, user_id, contents, deleted) VALUES (5, 1, 2, 'Non Deleted', false);
INSERT INTO ANSWER (id, question_id, user_id, contents, deleted) VALUES (6, 1, 2, 'Non Deleted', false);
