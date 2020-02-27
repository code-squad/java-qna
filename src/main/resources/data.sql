INSERT INTO USER (id, user_id, user_password, user_name, user_email) VALUES (null, 'dion', 'dion123', '디온', 'dion@codesquad.kr');
INSERT INTO USER (id, user_id, user_password, user_name, user_email) VALUES (null, 'codesquad', 'db1004', '코드스쿼드', 'master@codesquad.kr');
INSERT INTO QUESTION (id, writer_id, title, contents, created_date_time, updated_date_time, is_deleted) VALUES(null, 1, '음...', '테스트', NOW(), NOW(), false);
INSERT INTO ANSWER(id, comment, created_date_time, updated_date_time, question_id, writer_id) VALUES (null, '댓글입니다', NOW(), NOW(), 1, 2);
INSERT INTO ANSWER(id, comment, created_date_time, updated_date_time, question_id, writer_id) VALUES (null, '아 그런가요?', NOW(), NOW(), 1, 1);
INSERT INTO ANSWER(id, comment, created_date_time, updated_date_time, question_id, writer_id) VALUES (null, '네', NOW(), NOW(), 1, 2);
INSERT INTO ANSWER(id, comment, created_date_time, updated_date_time, question_id, writer_id) VALUES (null, '알겠습니다. 감사합니다.', NOW(), NOW(), 1, 1);
