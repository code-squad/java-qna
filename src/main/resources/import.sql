INSERT INTO USER (id, user_id, password, name, email) VALUES (1, 'dsoop', 'test', 'daesoop', 'kimdaesoop@slipp.net');
INSERT INTO USER (id, user_id, password, name, email) VALUES (2, 'kuro', 'test', 'kuro', 'kuro@slipp.net');

INSERT INTO QUESTION (id, writer_id, title, contents, create_date, answer_count) VALUES (1, 1, 'title 1', 'content 1', CURRENT_TIMESTAMP, 0);
INSERT INTO QUESTION (id, writer_id, title, contents, create_date, answer_count) VALUES (2, 2, 'title 2', 'content 2', CURRENT_TIMESTAMP, 0);
INSERT INTO QUESTION (id, writer_id, title, contents, create_date, answer_count) VALUES (3, 1, 'title 3', 'content 3', CURRENT_TIMESTAMP, 0);