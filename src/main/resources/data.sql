INSERT INTO USER (id, user_id, password, name, email) VALUES (1, 'master', '1234', '관리자', 'master@gmail.com');
INSERT INTO USER (id, user_id, password, name, email) VALUES (2, 'guest', '1234', '손님', 'guest@gmail.com');

INSERT INTO QUESTION (id, created_time, modified_time, contents, title,count_of_answer, writer_id) VALUES (1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '테스트 질문입니다', 'guest의 테스트 질문', 4 ,2);
INSERT INTO QUESTION (id, created_time, modified_time, contents, title,count_of_answer, writer_id) VALUES (2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '마스터의 테스트 질문', 'master 테스트 질문2',0, 1);

INSERT INTO ANSWER (id, created_time, modified_time, contents, question_id, writer_id) VALUES (1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '테스트 댓글 입니다1', 1, 1);
INSERT INTO ANSWER (id, created_time, modified_time, contents, question_id, writer_id) VALUES (2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '테스트 댓글 입니다2', 1, 2);

INSERT INTO ANSWER (id, created_time, modified_time, contents, question_id, writer_id) VALUES (3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '테스트 댓글 입니다3', 1, 1);
INSERT INTO ANSWER (id, created_time, modified_time, contents, question_id, writer_id) VALUES (4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '테스트 댓글 입니다4', 1, 2);

