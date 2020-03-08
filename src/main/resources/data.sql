INSERT INTO USER (id, user_id, user_password, user_name, user_email, user_profile_image)
VALUES (null, 'dion', 'dion123', '디온', 'dion@codesquad.kr',
        'https://img.velog.io/images/dion/profile/4bbeba74-8503-4766-9dd3-86d5490b8e92/arno-smit-sKJ7zSylUao-unsplash.jpg?w=240');
INSERT INTO USER (id, user_id, user_password, user_name, user_email, user_profile_image)
VALUES (null, 'codesquad', 'db1004', '코드스쿼드', 'master@codesquad.kr', 'https://codesquad.kr/img/company/codesquad2.png');
INSERT INTO QUESTION (id, writer_id, title, contents, created_date_time, updated_date_time, is_deleted)
VALUES (null, 1, '음...', '테스트', NOW(), NOW(), false);
INSERT INTO QUESTION (id, writer_id, title, contents, created_date_time, updated_date_time, is_deleted)
VALUES (null, 2, '지워진 글', '지워졌으니 못봐요.', NOW(), NOW(), true);
INSERT INTO ANSWER(id, comment, created_date_time, updated_date_time, question_id, writer_id, is_deleted)
VALUES (null, '댓글입니다', NOW(), NOW(), 1, 2, false);
INSERT INTO ANSWER(id, comment, created_date_time, updated_date_time, question_id, writer_id, is_deleted)
VALUES (null, '실수로 지웠습니다', NOW(), NOW(), 1, 1, true);
INSERT INTO ANSWER(id, comment, created_date_time, updated_date_time, question_id, writer_id, is_deleted)
VALUES (null, '아 그런가요?', NOW(), NOW(), 1, 1, false);
INSERT INTO ANSWER(id, comment, created_date_time, updated_date_time, question_id, writer_id, is_deleted)
VALUES (null, '네', NOW(), NOW(), 1, 2, false);
INSERT INTO ANSWER(id, comment, created_date_time, updated_date_time, question_id, writer_id, is_deleted)
VALUES (null, '알겠습니다. 감사합니다.', NOW(), NOW(), 1, 1, false);
