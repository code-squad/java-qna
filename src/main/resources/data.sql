INSERT INTO USER (user_id_fk, user_id, password, name, email) VALUES (1, 'doby', '1', '마구니를쫓는자', 'lkhlkh09@slipp.net');
INSERT INTO USER (user_id_fk, user_id, password, name, email) VALUES (2, 'admin', '1', '마구니가가득한자', 'sanjigi@slipp.net');


INSERT INTO QUESTION (question_id_fk, contents, created_date_time, title, writer, user_id_fk, deleted)
    VALUES (1, 'Auto Inserted Contents - First', '2018-12-02 20:45:28.685', 'Auto Inserted Title - First', 'admin', 2, 0);
INSERT INTO QUESTION (question_id_fk, contents, created_date_time, title, writer, user_id_fk, deleted)
    VALUES (2, 'Auto Inserted Contents - Second', '2018-12-02 20:50:28.685', 'Auto Inserted Title - Second', 'admin', 2, 0);

INSERT INTO COMMENT (comment_id, contents, created_date_time, question_id_fk, user_id_fk, identification)
    VALUES (1, 'Auto Inserted Comments - First', '2018-12-02 20:47:28.685', 1, 2, 1);
INSERT INTO COMMENT (comment_id, contents, created_date_time, question_id_fk, user_id_fk, identification)
    VALUES (2, 'Auto Inserted Comments - Second', '2018-12-02 20:48:28.685', 1, 1, 1);
