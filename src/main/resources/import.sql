INSERT INTO USER (ID, USER_ID, PASSWORD, NAME, EMAIL, CREATE_DATE) VALUES (1, 'dino', '0000', 'joseph', 'joe@naver.com', CURRENT_TIMESTAMP());
INSERT INTO USER (ID, USER_ID, PASSWORD, NAME, EMAIL, CREATE_DATE) VALUES (2, 'joseph', '0000', '천씨', 'joe@naver.com', CURRENT_TIMESTAMP());

INSERT INTO QUESTION (id, writer_id, title, contents, create_date, count_of_answer) VALUES (1, 1, '국내에서 Ruby on Rails', '2006년 Ruby 어쩌구', CURRENT_TIMESTAMP(), 0);