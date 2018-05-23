INSERT INTO USER (ID, USER_ID, PASSWORD, NAME , EMAIL, create_date) VALUES (1, 'jaeyeon93', '12345', '재연', 'jeayeon93@naver.com', current_timestamp());

INSERT INTO USER (ID, USER_ID, PASSWORD, NAME , EMAIL, create_date) VALUES (2, 'jimmy', '12345', '지미', 'foodsksms@naver.com', current_timestamp());

insert into question (id, writer_id, title, contents, create_date, count_of_answer) VALUES (1, 1, '제목1', '내용1', current_timestamp(), 0);

insert into question (id, writer_id, title, contents, create_date, count_of_answer) VALUES (2, 2, 'jimmy가 쓴', '내용2', current_timestamp(), 0);
