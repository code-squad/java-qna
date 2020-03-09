INSERT INTO USER (id, created_time, modified_time, user_id, password, name, email) VALUES (1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'master', '1234', '관리자', 'master@gmail.com');
INSERT INTO USER (id, created_time, modified_time, user_id, password, name, email) VALUES (2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'guest', '1234', '손님', 'guest@gmail.com');

INSERT INTO QUESTION (id, created_time, modified_time, contents, title,count_of_answer, writer_id) VALUES (1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '테스트 질문입니다', 'guest의 테스트 질문', 4 ,2);
INSERT INTO QUESTION (id, created_time, modified_time, contents, title,count_of_answer, writer_id) VALUES (2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '마스터의 테스트 질문', 'master 테스트 질문2',0, 1);
INSERT INTO QUESTION (id, created_time, modified_time, contents, title,count_of_answer, writer_id) VALUES (3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '페이지테스트1', '페이지테스트',0, 1);
INSERT INTO QUESTION (id, created_time, modified_time, contents, title,count_of_answer, writer_id) VALUES (4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '페이지테스트2', '페이지테스트',0, 1);
INSERT INTO QUESTION (id, created_time, modified_time, contents, title,count_of_answer, writer_id) VALUES (5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '페이지테스트3', '페이지테스트',0, 1);
INSERT INTO QUESTION (id, created_time, modified_time, contents, title,count_of_answer, writer_id) VALUES (6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '페이지테스트4', '페이지테스트',0, 1);
INSERT INTO QUESTION (id, created_time, modified_time, contents, title,count_of_answer, writer_id) VALUES (7, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '페이지테스트5', '페이지테스트',0, 1);
INSERT INTO QUESTION (id, created_time, modified_time, contents, title,count_of_answer, writer_id) VALUES (8, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '페이지테스트6', '페이지테스트',0, 1);
INSERT INTO QUESTION (id, created_time, modified_time, contents, title,count_of_answer, writer_id) VALUES (9, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '페이지테스트7', '페이지테스트',0, 1);
INSERT INTO QUESTION (id, created_time, modified_time, contents, title,count_of_answer, writer_id) VALUES (10, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '페이지테스트8', '페이지테스트',0, 1);
INSERT INTO QUESTION (id, created_time, modified_time, contents, title,count_of_answer, writer_id) VALUES (11, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '페이지테스트9', '페이지테스트',0, 1);
INSERT INTO QUESTION (id, created_time, modified_time, contents, title,count_of_answer, writer_id) VALUES (12, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '페이지테스트10', '페이지테스트',0, 1);
INSERT INTO QUESTION (id, created_time, modified_time, contents, title,count_of_answer, writer_id) VALUES (13, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '페이지테스트11', '페이지테스트',0, 1);
INSERT INTO QUESTION (id, created_time, modified_time, contents, title,count_of_answer, writer_id) VALUES (14, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '페이지테스트12', '페이지테스트',0, 1);
INSERT INTO QUESTION (id, created_time, modified_time, contents, title,count_of_answer, writer_id) VALUES (15, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '페이지테스트13', '페이지테스트',0, 1);
INSERT INTO QUESTION (id, created_time, modified_time, contents, title,count_of_answer, writer_id) VALUES (16, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '페이지테스트14', '페이지테스트',0, 1);
INSERT INTO QUESTION (id, created_time, modified_time, contents, title,count_of_answer, writer_id) VALUES (17, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '페이지테스트15', '페이지테스트',0, 1);
INSERT INTO QUESTION (id, created_time, modified_time, contents, title,count_of_answer, writer_id) VALUES (18, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '페이지테스트16', '페이지테스트',0, 1);
INSERT INTO QUESTION (id, created_time, modified_time, contents, title,count_of_answer, writer_id) VALUES (19, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '페이지테스트17', '페이지테스트',0, 1);

INSERT INTO ANSWER (id, created_time, modified_time, contents, question_id, writer_id) VALUES (1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '테스트 댓글 입니다1', 1, 1);
INSERT INTO ANSWER (id, created_time, modified_time, contents, question_id, writer_id) VALUES (2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '테스트 댓글 입니다2', 1, 2);

INSERT INTO ANSWER (id, created_time, modified_time, contents, question_id, writer_id) VALUES (3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '테스트 댓글 입니다3', 1, 1);
INSERT INTO ANSWER (id, created_time, modified_time, contents, question_id, writer_id) VALUES (4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '테스트 댓글 입니다4', 1, 2);

