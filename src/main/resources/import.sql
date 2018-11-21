INSERT INTO USER (id, user_id, password, name, email) VALUES (1, 'master', '1', '관리자', 'master@slipp.net');
INSERT INTO USER (id, user_id, password, name, email) VALUES (2, 'sanjigi', 'test', '산지기', 'sanjigi@slipp.net');
INSERT INTO QUESTION (question_id, writer_id, title, contents, timer) VALUES (1, 1, '타이틀1', '내용1', CURRENT_TIMESTAMP());
INSERT INTO QUESTION (question_id, writer_id, title, contents, timer) VALUES (2, 2, '타이틀2', '내용2', CURRENT_TIMESTAMP());