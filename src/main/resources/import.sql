INSERT INTO USER (ID, USER_ID, PASSWORD, NAME, EMAIL) VALUES (1, 'javajigi', 'test', '재성', 'javajigi@slipp.net');
INSERT INTO USER (ID, USER_ID, PASSWORD, NAME, EMAIL) VALUES (2, 'sanjigi', 'test', '산지기', 'sanjigi@slipp.net');

INSERT INTO QUESTION (id, writer_id, title, contents, create_date) VALUES (1, 1, '국내에서', '얍얍', CURRENT_TIMESTAMP());
INSERT INTO QUESTION (id, writer_id, title, contents, create_date) VALUES (2, 2, 'wefwefe', 'wefwefe', CURRENT_TIMESTAMP());
