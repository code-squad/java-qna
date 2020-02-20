## step1

> 목표

- 회원가입 및 사용자 목록 기능 구현



> 강의

- mustache == template engine
- Input tag 의 name에 어떤 값 이 있는 가에 따라서, 서버측에서 받아들이수 있음.
- controller를 통한 mapping을 기본적으로 resource/templates 에 되어있음.



> 정리

- IP를 통해서 어떻게 해당 홈페이지가 운영되는 서버에 도착할 수 있을까?
  ![](https://firebasestorage.googleapis.com/v0/b/nextstep-real.appspot.com/o/lesson-attachments%2F-KpOwxr2PBZkaRejxU49%2Fclient-server2.PNG?alt=media&token=882a86cb-3af0-4bf7-957b-e915e9bebdc3)

## step2

- DNS들을 거쳐가면서, DNS들에게, 너 해당 URL에 해당하는 주소 알고 있니? 물어, 물어보면서 찾아감.
- 기본적으로 찾아가는 포트는 80



- HTTP 프로토콜

  ![](https://www3.ntu.edu.sg/home/ehchua/programming/webprogramming/images/HTTP_Steps.png)

  - 프로토콜은 약속
  - 어떤 내용을 써서 보내겠다라는?

- scheme

  - 스킴, http, ftp, file, mailto

- http get을 통해 접근하는 요청은 DB에 내용을 바꿔서는 안된다.

- 기억해야할 status code

  1. 2xx : 성공
  2. 3xx : 성공했지만, 무언가 추가동작이 필요
  3. 4xx : 요청 오류임. 클라이언트에서 뭔가 문제적 동작을 했음
  4. 5xx : 서버상 오류. 요청은 제대로 들어왔으나, 서버에서 뭔가 문제가 있었음.

  

> 목표

- [x] DB 설정
- [x] DB 기반 회원가입, 목록 기능 구현
- [x] DB 기반 개인정보 수정 기능 구현
- [x] heroku 배포
  - https://cs-java-qna.herokuapp.com/



> 강의

- Entity 선언하게 되면, 각각 맵핑되는 컬럼마나 setter, getter가 모두 필요한 것 같음. 이부분을 어떻게 줄일 수 없을까?

- 템플릿 엔진에서 {{}} 을 통해 찾아주는 것을, domain class get method을 통해서인 것 같음.

- 유저 수정 시, 찾은 유저를 삭제하고 다시 넣을 경우 id가 증가. 뭔가 쓸데 없으니까 이를 막아보자.



> 정리

- CRUD(create, read or retrieve, update, delete)
  - 생성, 읽고, 업데이트하고, 지우고
- ORM?
  - Object Relational Mapping
  - Java Object 와 DB table mapping을 쉽게 도와주는 **프레임워크**
  - ex) Hiberante, OpenJPA..

- 기억해야할 annotation

  1. @Enumerated : 자바 enum 타입과 매칭

  2. @Temporal : 날짜 타입과 매칭

  3. @Lob : DB의 BLOB, CLOB?

     - BLOB : Binary Large Object, 이미지, 사운드, 비디오 같은 멀티미디어객체
     - CLOB : 문자 대형 객체 

     - [참고](https://stepping.tistory.com/30)

- JPA의 특징은, 항상 DB서버와 의존관계를 가진 상태임.