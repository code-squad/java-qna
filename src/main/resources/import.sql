INSERT INTO USER (id, user_id, password, name, email, create_date, modified_date) VALUES (1, 'choising', 'test', '최싱', '111@slipp.net', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO USER (id, user_id, password, name, email, create_date, modified_date) VALUES (2, 'seungmin', 'test', '최승민', '222i@slipp.net', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

INSERT INTO QUESTION (id, writer_id, title, contents, deleted, create_date, modified_date) VALUES (1, 1, '첫번째글', '내용없음', false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO QUESTION (id, writer_id, title, contents, deleted, create_date, modified_date) VALUES (2, 2, '두번째글', '내용은없음', false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
--
INSERT INTO ANSWER (id, writer_id, question_id, contents, deleted, create_date, modified_date) VALUES (1, 1, 2, 'answerTest', false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO ANSWER (id, writer_id, question_id, contents, deleted, create_date, modified_date) VALUES (2, 1, 2, 'answerTestTestTest', false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());


