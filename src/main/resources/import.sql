INSERT INTO USER (id, user_id, password, name, email,create_date) VALUES (1, 'jar100', 'test', '백경훈', 'jrs111@naver.com', CURRENT_TIMESTAMP);
INSERT INTO USER (id, user_id, password, name, email,create_date) VALUES (2, 'peter', 'test', '피터', 'jar10038@naver.com', '2018-11-26 00:17:16');

--
--
--
INSERT INTO QUESTION (id, writer_id, title, contents, create_date, count_of_answer, deleted, modified_date) VALUES (1, 1, 'testTitle입니다.', 'testContent입니다.', CURRENT_TIMESTAMP, 2, false ,CURRENT_TIMESTAMP);
INSERT INTO QUESTION (id, writer_id, title, contents, create_date, deleted, count_of_answer) VALUES (2, 2, '됩니다', '이게 될까요?', '2018-11-26 00:17:16',FALSE, 1);

INSERT INTO ANSWER (id, question_id, user_id, contents, create_date, modified_date, deleted) VALUES (1,1,2,'나만 나왔으면 좋겠다.',CURRENT_TIMESTAMP , CURRENT_TIMESTAMP, FALSE);
INSERT INTO ANSWER (id, question_id, user_id, contents, create_date, modified_date, deleted) VALUES (2,2,1,'피터의 질문에 나와야한다', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE);
INSERT INTO ANSWER (id, question_id, user_id, contents, create_date, modified_date, deleted) VALUES (3,1,1,'나는 jar100에 나와야한다', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE);
--
--
