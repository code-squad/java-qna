INSERT INTO USER (id, user_id, password, name, email) VALUES (1, 'jar100', 'test', '백경훈', 'jrs111@naver.com');
INSERT INTO USER (id, user_id, password, name, email) VALUES (2, 'peter', 'test', '피터', 'jar10038@naver.com');
INSERT INTO QUESTION (id, writer_id, title, contents, date, deleted) VALUES (1, 1, 'test', '이게 될까요?', '2018-11-26 00:17:16',FALSE);
INSERT INTO QUESTION (id, writer_id, title, contents, date, deleted) VALUES (2, 2, '됩니다', '이게 될까요?', '2018-11-26 00:17:16',FALSE);
INSERT INTO ANSWER (id, question_id, user_id, contents, date, deleted) VALUES (1,1,2,'나만 나왔으면 좋겠다.', '2018-11-26 00:17:16',FALSE);
INSERT INTO ANSWER (id, question_id, user_id, contents, date, deleted) VALUES (2,2,1,'피터의 질문에 나와야한다', '2018-11-26 00:17:16',FALSE);
INSERT INTO ANSWER (id, question_id, user_id, contents, date, deleted) VALUES (3,1,1,'나는 jar100에 나와야한다', '2018-11-26 00:17:16',FALSE);


