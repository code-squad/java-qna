INSERT INTO USER (ID, USER_ID, PASSWORD, NAME, EMAIL) VALUES (1,'javajigi', 'test', '재성', 'javajigi@slipp.net')
INSERT INTO USER (ID, USER_ID, PASSWORD, NAME, EMAIL) VALUES (2,'han95210', '1234', '한울', 'han95210@naver.com')

INSERT INTO QUESTION (id, writer_id, title, contents, created_date) VALUES (1, 1,'국내에서', 'Ruby', CURRENT_TIMESTAMP());
INSERT INTO QUESTION (id, writer_id, title, contents, created_date) VALUES (2, 2,'외국에서', 'JAVA', CURRENT_TIMESTAMP());
