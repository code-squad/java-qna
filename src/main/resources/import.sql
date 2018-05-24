INSERT INTO USER (ID, USER_ID, PASSWORD, NAME, EMAIL, CREATE_DATE) VALUES (1, 'javajigi', 'test', '재성', 'javajigi@slipp.net', CURRENT_TIMESTAMP());
INSERT INTO USER (ID, USER_ID, PASSWORD, NAME, EMAIL, CREATE_DATE) VALUES (2, 'sanjigi', 'test', '산지기', 'sanjigi@slipp.net', CURRENT_TIMESTAMP());

INSERT INTO QUESTION (id, writer_id, title, contents, create_date, count_of_answer) VALUES (1, 1, '국내에서', '얍얍', CURRENT_TIMESTAMP(), 0);
INSERT INTO QUESTION (id, writer_id, title, contents, create_date, count_of_answer) VALUES (2, 2, 'wefwefe', 'wefwefe', CURRENT_TIMESTAMP(), 0);
