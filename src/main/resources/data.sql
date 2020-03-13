INSERT INTO USER(user_id, password, name, email, created_at) VALUES( 'ellie', '1234', '엘리', 'ellie@gmail.com', CURRENT_TIMESTAMP());
INSERT INTO USER( user_id, password, name, email, created_at) VALUES( 'amuge', '4321', '김아무개', 'amuge@gmail.com', CURRENT_TIMESTAMP());
INSERT INTO QUESTION( contents, created_at, title, writer_id, deleted,  count_of_answer) VALUES('스프링이 뭔가요', CURRENT_TIMESTAMP(), '스프링', 2 , false, 2);
INSERT INTO ANSWER( contents, created_at,  question_id, writer_id, deleted) VALUES( '몰라요', CURRENT_TIMESTAMP(), 1, 1 , false);
INSERT INTO ANSWER( contents, created_at,  question_id, writer_id, deleted) VALUES( '삭제한 댓글!', CURRENT_TIMESTAMP(), 1, 1 , true);
INSERT INTO ANSWER( contents, created_at,  question_id, writer_id, deleted) VALUES( '댓글!', CURRENT_TIMESTAMP(), 1, 1 , false);
