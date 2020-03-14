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



## step2

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





## step3

> 목표

- [x] 로그인 기능 구현 1

- [x] 로그인 상태에 따른 메뉴 처리, 로그아웃
- [x] 로그인한 자기 자신의 정보만 수정하도록
- [x] 코드 중복제거
- [ ] clean code
- [x] 쿼리 보기
- [x] 로그한 사용자에 한 해, 질문이 가능하도록
- [x] 질문 목록 기능 구현



> 강의

```handlebars
{{^user}} //user session이 없으면 아래 코드를 실행
...
{{/user}}

{{#user}} //user session이 있으면 아래 코드를 실행
...
{{/user}}
```

- 기억해야할 handlerbar 문법ㅎㅎ

- model 에 전달되는 data와 session에 전달되는 데이터의 이름을 다르게 설정해 줘야함. 그렇지 않으면 오류나니까.

- 정상적으로 작동하는 기능에 대한 구현 보다는, 예외에 대한 처리가 더 중요하다. controller에서
- util class는 보통 `static` 

- 유저의 비밀번호를 비교하려 할때, 객체에서 데이터를 꺼내서 넘어온 비밀번호와 비교하는 것 보다는, 해당 객체한테 일을 시키려고 해야함.
  - 생각보다 손이 많이 가는 작업.
  - 이걸 손 쉽게 만들어주는 프레임워크 없을까?

- `@Autowired`
  - Spring framework한테 아래 해당하는 클래스를 알아서 주입하라고 알려주는 것.
  - 현재 필드 내에서는 주입하는 것을 IDE에서 추천하지 않네.

- `Http`는 무상태프로토콜. 즉 어떠한 상태도 저장하지 않는다.
  - 그런데, 쿠키, 세션을 저장하는 거 아닌가...?
- 세션 갱신을 보려면 removeAttribute가 아닌 invalidate를 써야할듯.



> 정리

#### 301 code vs 302 code

- 301 : 요청한 페이지가 **영구적**으로 새로운 장소로 이동되었음을 알려주는 코드
- 302 : 요청한 페이지의 이동은 잠시 일시적이라는 것을 브라우저에게 알려줌
- 302로 알려준다면, 브라우저는 다음에 방문할 때도 똑같은, 주소로 접속하게 될것임.
- 이건 별로 좋지 않은 선택, 새로운 주소를 브라우저에게 알려줘야함
- 왜 이부분에 대해 신경써야할까?
  - 검색엔진 랭킹을 측정할 때 차이가 날 수 있음.
  - 새로운 주소로 카운팅 되는 게 낫겠지? 이전 주소보다는.
  - 즉 비즈니스적 문제에 해당한다.



> 참고

- https://www.hochmanconsultants.com/301-vs-302-redirect/
- https://soul0.tistory.com/269



#### Session vs Cookie

- 쿠키
  - 서버가 클라이언트에게 처음 보냄 (클라이언트가 저장함)
  - 이름, 값, 만료날짜(쿠키 저장기간이 정해져 있음)
    - 클라이언트는 *최대 300개*, *하나의 도메인 20개의 값*만.
  - 어디에다가? Set-Cookie header값에다가, 상태를 유지하고 싶은 값을 담아서
  - 클라이언트 요청을 보낼때 마다Set-Cookie에 받았던 값을 설정하여 보냄.
  - 이 값으로 서버는 이전 사용자인지 판단함.
  - 즉 서버가 우선 사용자를 식별할 수 있는 값(세션)을 주고
  - 클라이언트가 요청 할 때마다 받은 세션을 쿠키에 넣어서 보냄으로써 서로 인식함.

- 세션

  - 클라이언트가 서버에 접속하면 session ID가 발급되고
  - 서버는 이걸 쿠키에 저장하고(JSESSIONID) 전송하고
  - 이걸 받은 클라이언트는 다시 요청할 때 JSESSIONID를 쿠키에 넣어서 보냄. 나 이전에 접속했던 사용자야 라고 알려줌
  - 일정 기간 동안 같은 브라우저(클라이언트) 로 부터 오는 요청을 상태로 인식하교 유지하는 기술????

- 쿠키 vs 세션

  - 저장 위치의 차이가 있음.

    1. 쿠키는 클라이언트쪽에
    2. 세션은 서버쪽에

  - 보안적인 측면에서도

    1. 쿠키는 클라이언트쪽이기 때문에 손실 혹은 변질의 위험성을 무시할 수 없음.
    2. 세션은 서버 쪽이니까 그나마 낫지.

  - <u>만료시간(LifeCycle)</u>

    1. 쿠키는 만료시간이 있지만, 브라우저를 종료해도 클라이언트 쪽에 남아있을 가능성 있고
    2. 세션은 브라우저가 종료되면 아예 없어짐.

  - 왜 세션만 사용하지 않을까?

    - 서버 쪽에 부담이 될 수 있으니까.

    

> 참고

- https://jeong-pro.tistory.com/80



#### JPA query

- https://arahansa.github.io/docs_spring/jpa.html#jpa.query-methods
- 따로 `@Query` annotation을 지정하지 않아도, 이름만으로 쿼리를 **추정** 해줌.
  - 생각보다 신박한 기능.





> 학습 체크 리스트

1. MVC 패턴에 대해 설명하고, model view controller 각각의 역할에 대해 설명하시오.
   - MVC 패턴
     - 웹서비스를 구현하기에 필요한 가장 큰 로직 3가지를 나눠서 추상화한 패턴
     - 역할에 따라 model, view, controller로 나눠서 개발에 가독성과 재사용성을 높히기 위한 패턴이지 않을까 싶음.
   - model은 개발자가 정의한 자원. controller, view에서 model 명을 이용하여 필요할 때마다 찾아서 매칭하는 식으로 사용된다.
   - view는 display를 담당하는 역할. controller를 통해 넘어온 데이터를 자신에게 정의된 형태로 보여주는 역할을 담당하고 있다.
   - controller 는 dispatcherServlet에서 handlermapping으로 넘어온 url 요청을 처리하는 역할을 담당. 요청한 로직에 따라 적절한 model data를 담아서, view에게 넘겨주는 중간다리 역할임.

2. 웹 클라이언트에서 요청을 보내고, 웹 서버에서 응답을 받기까지의 과정에 대해 최대한 구체적으로 설명하시오.
   - 웹서버로 가는 데, 정적인 요청이면 여기서 바로 응답을 보내고
   - 동적인 요청이면 WAS를 통해 Spring에 할당된 dispatcherServlet을 통해서
   - HandlerMapping이 요청온 URL을 분석하여 Controller를 mapping 해주고,
   - controller는 클라이언트의 요청에 필요한 model을 찾아 비즈니르 로직을 실행하고, 이걸 view resolver 에게 넘겨주면,
   - view resolver는 어떤 view를 클라이언트에게 보낼지 결정하게 되고,
   - 결정된 view가 응답으로 클라이언트에게 감.

3. 이번 미션에서 공부한 주제 중에서 학습하기 가장 어려웠던 것은 어떤 것인가요?
   - Spring Boot, 그리고 JPA는 생각보다 많은 부분이 추상적이라 느꼈습니다.. 이부분을 이용하기 위해서 공부해야하는 것이 상대적으로 방대하다고 생각했고, 필요한 부분만 찾아서 하였는데 이 방향이 맞는지 조금 의문점이 들었습니다.

4. 그 외에 학습하기 어렵거나 시간이 오래 걸린 것은 무엇이고, 어떻게 극복했나요?
   - Model 객체에 어노테이션과, DB와의 맵핑 부분.
   - 생소한 언어들이 많아, 이해하기가 힘들었고 아직도 부족하다고 생각합니다.
   - 우선 이해할 수 있는 부분만, 그리고 너무 붙잡고 있지 않으려 시도했습니다.
   - 그리고 이해한 부분은 저 자신만의 언어로 표현하고자 해봤습니다.



## step4

> 목표

- [x] 회원, 질문간 관계 매핑
- [x] 생성일 추가
- [x] 질문 상세보기 기능
- [x] 질문 수정, 삭제 기능 구현
- [x] 수정, 삭제에 대한 보안 처리
- [x] LocalDateTime
  - [x] BaseTimeEntity를 통해 해결.
- [x] 답변 포인트 기능
- [x] 리팩토링
  - [x] 중복 코드 제거



> 강의

- User domain 내에서는 다른 관계를 맺는 것은 추천하지 않는다. 
  - 아마도 삭제 문제?
- 객체에서 데이터를 꺼내는 로직은 지양해야함.
- Domain code는 hascode와 equals를 구현하는 습관을 가져야함.
  - 어떤 방식으로 구현하는 것이 좋을까?
- @ManyToONe
  - 해당 클래스가 Many, 아래 정의되는 변수가 One
  - 참조를 하면 해당 클래스의 Id가 자동적으로 맵핑됨.
- 테스트할 때, `html` 태그도 같이 테스를 해보자.
  - 왜? 보안상 문제가 발생하는지 
- exception을 model에 전달해줘서 template에 뿌려줄 수 있음.
- 중복코드를 항상 어떻게 제거할 수 있을지 생각해야함.
- User  id 변수와  userId 변수가 헷갈릴 때가 많음. 
  - 이 중 하나 이름을 바꿔야 하지 않을까 생각됨.



> 정리

##### @Generate Value

- Auto : user database platform에 의해 알아서 설정됨. MySQL ,SQLite , MsSQL은 identity , Oracle, PostgreSQL은 sequence
- Sequence : 연속적, Oracle, PostgreSQL
- Identity : row만큼 id를 생성함.
  - MySQL/SQLite => `AUTO_INCREMENT`
  - MSSQL => `IDENTITY`
  - PostgreSQL => `SERIAL`

##### @JoinColumn

- name : 매핑할 외래키 이름 : `필드명+_+참조하는 테이블의 기본키 컬럼명`
- referencedColumnName : 외래 키가 참조하는 대상 테이블의 컬럼명
- FetchType
  - EAGER : 빨리
  - Lazy : 느리게

##### @lob

- Large Object
- 주로 많은 양의 데이터를 저장할 때 사용
- BLOB, CLOB가 있음.
- BLOB for binary data, ex)image...
- CLOB for text data



## step5

> 목표

- AJAX 활용
  - [x] 답변 수정
  - [x] 답변 삭제
  - [x] 답변 수(포인트)
- 리팩토링
  - [x] 도메인
- Swagger
  - [x] 문서화
  - [x] 정리
- 기타
  - [x] 도메인 클래스 `equals()`,` hashcode()` 정의하기



> 강의

- `e.preventdefault()`
  - 전송기능을 막았다. 전송안됨을 확인함.
- 데이터 베이스에 쿼리를 날리면 날릴 수록 성능상 좋지 않음.
- 추상 클래스에도 public 으로 선언하지 않은 것은 template에서 접근할 수 없음.
- http://localhost:8080/swagger-ui.html
  - 스웨거 주소
- JavaScript 문법이 이해가 되는듯 싶으면서도, 잘 모르겠음.





> 정리

- https://stackoverflow.com/questions/15261456/how-do-i-disable-fail-on-empty-beans-in-jackson
- https://jhkang-tech.tistory.com/92
- https://thoughts-on-java.org/ultimate-guide-to-implementing-equals-and-hashcode-with-hibernate/
- https://developer.mozilla.org/en-US/docs/Web/API/XMLHttpRequest/Using_XMLHttpRequest

- JPA 확실히 강력하고 편하지만, 사용하면서 제대로 모르고 사용하고 있다는 생각이 강하게 듬.

##### XHR

- XMLHttpRequest

- Ajax와 같이 동작하는 요청. 

- 예시

  ```js
  function reqListener () {
    console.log(this.responseText);
  }
  
  var oReq = new XMLHttpRequest();
  oReq.addEventListener("load", reqListener);
  oReq.open("GET", "http://www.example.org/example.txt");
  oReq.send();
  ```

- 동기적, 비동기적 요청 모두 보낼수 있는 듯.

- 기본적으로는 비동기적.



##### API

- Application Programming Interface
- 데이터에 대한 집합을 뜻하는 듯.
- 요청하면 **보통 Json** 형식으로 데이터를 돌려주고.



- 웹, 모바일 백엔드가 하는일 은 같다.

- 요즘 트랜드는 최초 접속시 서비스 이용에 필요한 자원(이미지등 파일)을 모두 받고, 그 이후에 요청에는 모두 ajax를 이용하여 Json or XML을 통해서 통신하는 것이 트랜드.



## step6

> 목표

- 페이지 나누기
  - [x] 한페이지당 15개씩
  - [x] 생성일 기준 내림차순

- 페이지 목록...



> 정리

- MaxPage를 통해서 프론트에서 제어하는 방식으로,  paing을 동적으로 해보자
  - 안됨.
- 페이지 리스트를 서버와 교류 없이 동적으로 html을 만드는 코드를 작성하려 했지만 실패
- 왜?
  - 이벤트 바인딩, 버블링, 캡처링에 대한 개념 부족.
  - 어떻게 진행되는 지 모르겠음.
- 굉장히 하드코딩으로 구현하여, 아마도 다른 예제 상황이 있다면 작동 안될 가능성 높음 --> 안좋은 코드 ㅠㅠ

- Failed to retrieve application JMX service URL
  - 오류 때문에 , class가 로드 되지 않거나, DB 맵핑에 문제가 생기는 경우가 있었음.
  - it isn't possible to get local JMX connector address if Spring Boot application and IntelliJ IDEA JVMs have different bitness 라는데 , 정확히 어떤 것 때문인지는 이해가 안된다.





> 셀프 체크 리스트

##### ORM과 JPA의 관계, JPA와 Hibernate의 관계에 대해 이해했는가?

- ORM
  - Object Relation Mapping
  - 객체 관계 맵핑.
  - 즉 자바 객체가 맵핑되어 DB 테이블에 등록되는 느낌
  - DB 탐색시, Query를 직접 작성하지 않아도 됨.

- JPA, Java Persistence API, 자바 어플리케이션에서 관계형 데이터베이스 사용 방식을 정의한 **인터페이스**
- JPA는 라이브러리 아님. 단지 정의되어있음. 어떻게 사용하면 되는지

- Hiberante는 JPA 인터페이스의 구현체
  - 미리 구현해놓은 것임.
  - 맘에 안들면 인터페이스로 부터 나만의 구현체를 만들어도 됨.

- JPA, Hibernate, Spring Data JPA

  ![](https://suhwan.dev/images/jpa_hibernate_repository/overall_design.png)



- 참고
  - https://suhwan.dev/2019/02/24/jpa-vs-hibernate-vs-spring-data-jpa/
  - https://victorydntmd.tistory.com/195



##### Maven, Gradle과 같은 빌드 도구의 역할에 대해 이해했는가?

- Maven

  - groupId, artifactId, version을 통해서 프로젝트를 식별한다.

  - groupId : 조직의 고유 아이디. 일반적으로 도메인.

  - artifactId : 프로젝트를 식별하는 유일한 아이디.

  - version. 프로젝트 현재 버전. 

    - 개발이 완료되었으면 1.0, 2.0..
    - 개발중이면 접미사로 SNAPSHOT을 사용

  - 기본 디렉토리 구조

    - src/main/java : 배포되는 자바 소스 코드 디렉토리
    - scr/main/resources : 배포되는 설정 파일 디렉토리
    - src/main/webapp : 웹 정적자원 html, css, javascript를 관리하는 디렉토리

  - 원격 저장소, 로컬저장소

    ![](https://firebasestorage.googleapis.com/v0/b/nextstep-real.appspot.com/o/lesson-attachments%2F-KhznCj3wRoDrQIEqsXG%2Fmaven%20repository.JPG?alt=media&token=244503d6-ba83-44b2-aacb-bcfc1ff10cf7)

  - 메이븐 빌드 도구 역할가 왜 필요해? 

    - 프로젝트 빌드를 쉽게할 수 있음.
    - 다른 의존성을 쉽게 추가할 수 있음.
    - 의존성들의 업데이트가 쉬워짐.
    - 프로젝트 outputs 타입 통일?
    - 즉 종합하자면 프로젝트 관리(빌드, 업데이트, 공유) 등을 편하고 오류 없게 하기 위해서.

    

  - 라이프 사이클
    1. compile : 소스 코드 컴파일 하고
    2. test : 테스트 코드 실행, 실패하면 빌드 실패로 생각
    3. package : pom.xml에 설정된 방식(jar,war,ear등)으로 압축
    4. install : 로컬 저장소에 압축한 파일을 배포
    5. deploy : 원격 저장소에 압축한 파일을 배표.

  - clean
    - 메이븐 빌드를 통해 생성된 모든 산출물을 삭제.
    - 즉 target 디렉토리 삭제
  - site
      
      - 프로젝트에 대한 문서 사이트를 생성.



##### Cookie와 Session의 차이점

- 가장 큰 차이점은 세션은 서버쪽에서 저장되고, 쿠키는 클라이언트의 브라우저에 저장됨.

- 쿠키

  - bit of data, maximum 4KB
  - 서버로 부터 set Cookie 요청이 와서, 한번 설정되면, 모든 페이지 요청에 포함됨.
  - 만료시간을 설정할 수 있음.

- 세션

  - collection of data

  - 서버에서 세션이 생성되면, 쿠키에 포함되어져서 클라이언트 쪽으로 전송됨.
  - 상대적으로 쿠키보다는 많은 양의 데이터를 저장할 수 있음.
  - 브라우저가 닫히면, 삭제됨.

- 참고

  - https://www.tutorialspoint.com/What-is-the-difference-between-session-and-cookies

  - https://www.guru99.com/difference-between-cookie-session.html



