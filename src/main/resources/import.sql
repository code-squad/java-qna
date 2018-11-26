INSERT INTO USER (id, user_id, password, name, email) VALUES (1, 'master', '1', '관리자', 'master@slipp.net');
INSERT INTO USER (id, user_id, password, name, email) VALUES (2, 'sanjigi', 'test', '산지기', 'sanjigi@slipp.net');
INSERT INTO QUESTION (id, writer_id, title, contents, timer, deleted) VALUES (1, 1, '타이틀1', '내용1', CURRENT_TIMESTAMP(), 0);
INSERT INTO QUESTION (id, writer_id, title, contents, timer, deleted) VALUES (2, 2, '타이틀2', '내용2', CURRENT_TIMESTAMP(), 0);