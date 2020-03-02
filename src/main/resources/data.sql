INSERT INTO USER(user_id, password, name, email) VALUES( 'ellie', '1234', '엘리', 'ellie@gmail.com');
INSERT INTO USER( user_id, password, name, email) VALUES( 'amuge', '4321', '김아무개', 'amuge@gmail.com');
INSERT INTO QUESTION( contents, created_at, title, writer_id) VALUES('스프링이 뭔가요', '2020-02-28 02:17:24.444', '스프링', 2 );
INSERT INTO ANSWER( contents, created_at,  question_id, writer_id) VALUES( '몰라요', '2020-02-28 02:17:24.444', 1, 1 );
