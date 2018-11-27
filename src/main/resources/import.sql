INSERT INTO USER (id, user_id, password, name, email) VALUES (1, 'finn', 'test', '동선', 'kokof21@naver.com');
INSERT INTO USER (id, user_id, password, name, email) VALUES (2, 'pobi', 'test', '자바자기', 'javajigi@naver.com');

INSERT INTO QUESTION (id, writer_id, title, contents, created_date, updated_date) VALUES (1, '1', '핀의 첫번째 질문', '핀의 첫번째 질문의 내용입니다.', current_timestamp, current_timestamp);
INSERT INTO QUESTION (id, writer_id, title, contents, created_date, updated_date) VALUES (2, '1', '핀의 두번째 질문', '핀의 두번째 질문의 내용입니다.', current_timestamp, current_timestamp);
INSERT INTO QUESTION (id, writer_id, title, contents, created_date, updated_date) VALUES (3, '2', '포비의 첫번째 질문', '포비의 첫번째 질문의 내용입니다.', current_timestamp, current_timestamp);