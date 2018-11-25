INSERT INTO USER (id, user_id, password, name, email) VALUES (1, 'brad903', '1234', '브래드', 'brad903@naver.com');
INSERT INTO USER (id, user_id, password, name, email) VALUES (2, 'leejh903', '1234', '이정현', 'leejh903@gmail.com');
INSERT INTO USER (id, user_id, password, name, email) VALUES (3, 'aaa', '1234', '에이에이', 'aaa@gmail.com');
INSERT INTO USER (id, user_id, password, name, email) VALUES (4, 'bbb', '1234', '비비비', 'bbb@gmail.com');
INSERT INTO USER (id, user_id, password, name, email) VALUES (5, 'ccc', '1234', '씨씨씨', 'ccc@gmail.com');
INSERT INTO USER (id, user_id, password, name, email) VALUES (6, 'ddd', '1234', '디디디', 'ddd@gmail.com');
INSERT INTO USER (id, user_id, password, name, email) VALUES (7, 'eee', '1234', '이이이', 'eee@gmail.com');

INSERT INTO QUESTION (id, user_id, title, contents, create_date) VALUES (1, 1, '질문있습니다1', '예전에는 매번 id/pwd를 서버에서 보내 클라이언트 브라우저가 가지고 있다가(쿠키- http 상태값) 요청합니다. 그런데 보안적으로 id/pwd가 요청되는게 좋지 않겠죠.', timestamp '2017-10-12 21:22:23');
INSERT INTO QUESTION (id, user_id, title, contents, create_date) VALUES (2, 2, '질문있습니다2', 'at org.springframework orm.jpa.AbstractEntityManagerFactoryBean.buildNativeEntityManagerFactory(AbstractEntityManagerFactoryBean.java:390) [spring-orm-5.1.2.RELEASE.jar:5.1.2.RELEASE]', timestamp '2018-11-01 11:22:23');
INSERT INTO QUESTION (id, user_id, title, contents, create_date) VALUES (3, 3, '질문있습니다3', 'at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.afterPropertiesSet(AbstractEntityManagerFactoryBean.java:377) [spring-orm-5.1.2.RELEASE.jar:5.1.2.RELEASE]', timestamp '2018-11-22 11:22:23');
INSERT INTO QUESTION (id, user_id, title, contents, create_date) VALUES (4, 4, '질문있습니다4', '예Caused by: org.h2.jdbc.JdbcSQLException: Data conversion error converting "user_id"; SQL statement:.', timestamp '2017-09-01 09:22:23');
INSERT INTO QUESTION (id, user_id, title, contents, create_date) VALUES (5, 5, '질문있습니다5', '자폐장애등록 신청을 하였는데 등급외가 나왔습니다. 이의신청을 했지만 다시 등급외가 나왔습니다. 그런데 등급외의 사유가 - 증세는 확인되나 자폐가 아닌 다른 정신과적인 타질환에 의한 것으로 의심된다 - 입니다. 의심의 근거는 병원을 찾는게 늦은편인 초등학교 4학년에 찾았고, 그 당시 특별한 치료를 하지 않았기 때문입니다. - 그때 검사한 대학병원의 진료기록서에 K-PIC 검사에서 자폐', timestamp '2018-11-09 13:22:23');
INSERT INTO QUESTION (id, user_id, title, contents, create_date) VALUES (6, 6, '질문있습니다6', '안녕하세요? 현재 아파트 전세 거주중이며, 2018년 12월 05일 계약 만료 및 이사 날짜입니다. 문제는... 현재 저희 집이 나가질 않았습니다. 여기 근처에 집값이 좀 떨어진것도 없지 않아 있지만 애가탑니다. 그래서 대체적으로 부동산 업을 하시거나 지식이 있으신분들께 몇 가지 문의드립니다. .', current_timestamp);
INSERT INTO QUESTION (id, user_id, title, contents, create_date) VALUES (7, 7, '질문있습니다7', '안녕하세요. 방송대 지도교수 추천서를 받아야되는 경우 어떻게 하나요? 추천서 초안을 제가 영문으로 작성해간 다음 교수님의 자필을 요구하는 건가요?', current_timestamp);
