INSERT INTO USER (id, user_id, password, name, email) VALUES (1, 'finn', 'test', '동선', 'kokof21@naver.com');
INSERT INTO USER (id, user_id, password, name, email) VALUES (2, 'pobi', 'test', '자바자기', 'javajigi@naver.com');

INSERT INTO QUESTION (id, writer_id, title, contents, created_date, modified_date, deleted, count_of_answer) VALUES (1, 1, '핀의 첫번째 질문', '핀의 첫번째 질문의 내용입니다.', current_timestamp, current_timestamp, 0, 1);
INSERT INTO QUESTION (id, writer_id, title, contents, created_date, modified_date, deleted, count_of_answer) VALUES (2, 1, '핀의 두번째 질문', '핀의 두번째 질문의 내용입니다.', current_timestamp, current_timestamp, 0, 2);
INSERT INTO QUESTION (id, writer_id, title, contents, created_date, modified_date, deleted, count_of_answer) VALUES (3, 2, '포비의 첫번째 질문', '포비의 첫번째 질문의 내용입니다.', current_timestamp, current_timestamp, 0, 0);
INSERT INTO QUESTION (id, writer_id, title, contents, created_date, modified_date, deleted, count_of_answer) VALUES (4, 1, '포비의 삭제된글', '안보이지롱', current_timestamp, current_timestamp, 1, 0);

INSERT INTO ANSWER (id, writer_id, question_id, contents, created_date, modified_date, deleted) VALUES (1, 1, 2, '핀의 두번째 질문글에 대한 핀의 코멘트입니다.', current_timestamp, current_timestamp, 0);
INSERT INTO ANSWER (id, writer_id, question_id, contents, created_date, modified_date, deleted) VALUES (2, 2, 2, '핀의 두번째 질문글에 대한 포비의 추가 코멘트입니다.', current_timestamp, current_timestamp, 0);
INSERT INTO ANSWER (id, writer_id, question_id, contents, created_date, modified_date, deleted) VALUES (3, 2, 1, '핀의 첫번째 질문글에 대한 포비의 코멘트입니니다.', current_timestamp, current_timestamp, 0);
INSERT INTO ANSWER (id, writer_id, question_id, contents, created_date, modified_date, deleted) VALUES (4, 2, 3, '포비의 첫번째 질문글에 삭제된 포비의 코멘트입니다.', current_timestamp, current_timestamp, 1);
