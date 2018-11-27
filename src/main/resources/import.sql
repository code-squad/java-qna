INSERT INTO USER (id, user_id, password, name, email) VALUES (1, 'user1', '1', '유저1', '1@1');
INSERT INTO USER (id, user_id, password, name, email) VALUES (2, 'user2', '2', '유저2', '2@2');

INSERT INTO QUESTION (id, writer_id, title, contents, create_date) VALUES (1, 1, '제목', '내용', CURRENT_TIMESTAMP);