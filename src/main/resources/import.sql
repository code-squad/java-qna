INSERT INTO USER (id, user_id, password, name, email) VALUES (1, 'suhyunsim', 'test', '수봉', 'susu@slipp.net');
INSERT INTO USER (id, user_id, password, name, email) VALUES (2, 'jihyunsim', 'test', '지봉', 'jiji@slipp.net');

INSERT INTO QUESTION (id, writer_id, title, contents, date_time) VALUES(1, 1,'suhyunsim글','테스트용 질문입니다. 질문1', current_timestamp());
INSERT INTO QUESTION (id, writer_id, title, contents, date_time) VALUES(2, 2,'jihyunsim글','테스트용 질문입니다. 질문2', current_timestamp());
