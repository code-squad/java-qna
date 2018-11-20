INSERT INTO USER (ID, USER_ID, password, name, email) VALUES (1, 'Dom', 'test', 'dom', 'javajigi@slipp.net');
INSERT INTO USER (id, user_id, password, name, email) VALUES (2, 'sehun', 'test', 'sehun', 'sanjigi@slipp.net');

INSERT INTO QUESTION (id, user_id, title, contents) VALUES (1, 2, 'test', 'test contents');


INSERT INTO ANSWER (id, question_id, user_id, contents) VALUES (1, 1, 2, 'test contents');
