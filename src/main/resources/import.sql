INSERT INTO USER (p_id, user_id, password, name, email, create_date) VALUES (1, 'asd', 'asd', '자바지기', 'javajigi@slipp.net', current_timestamp());
INSERT INTO USER (p_id, user_id, password, name, email, create_date) VALUES (2, 'forever', '123', '김태수', 'ajrwk384@naver.com', current_timestamp());
INSERT INTO QUESTION (p_id, contents, modified_date, deleted, title, writer_p_id, answers_size) VALUES (1, '안녕', current_timestamp(), false, '질문없어요', 1, 0);
INSERT INTO QUESTION (p_id, contents, modified_date, deleted, title, writer_p_id, answers_size) VALUES (2, '안녕하세요', '2018-11-26 14:57:58.482', false, '질문있어요', 2, 0);
