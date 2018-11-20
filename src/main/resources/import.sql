INSERT INTO USER (id, user_id, password, name, email) VALUES (1, 'brad903', '1234', '브래드', 'brad903@naver.com');
INSERT INTO USER (id, user_id, password, name, email) VALUES (2, 'leejh903', '1234', '이정현', 'leejh903@gmail.com');

INSERT INTO QUESTION (index, title, writer, contents, id, time) VALUES (1, '질문있습니다1', 'brad', '예전에는 매번 id/pwd를 서버에서 보내 클라이언트 브라우저가 가지고 있다가(쿠키- http 상태값) 요청합니다. 그런데 보안적으로 id/pwd가 요청되는게 좋지 않겠죠.', 1, '2018-11-02 13:12');
INSERT INTO QUESTION (index, title, writer, contents, id, time) VALUES (2, '질문있쇼', 'leejh903', 'html부터 가져오고 이 내용을 위에서부터 하나씩 읽어가면서 필요한 부분에 대해 서버에 그때그때 요청합니다(웹은 서버로부터 모든 것을 하나씩 요청하는 방식) ↔︎ 모바일앱(앱을 설치할 때 자원이 한번에 다운받음, 데이터만 서버에서 받음)', 2, '2017-10-02 15:12');
