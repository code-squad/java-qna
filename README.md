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

- 로그인 기능 구현 1
- 로그인 상태에 따른 메뉴 처리, 로그아웃
- 로그인한 자기 자신의 정보만 수정하도록
- 코드 중복제거
- clean code
- 쿼리 보기
- 로그한 사용자에 한 해, 질문이 가능하도록
- 질문 목록 기능 구현



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







> 정리

#### 301 code vs 302 code



#### Session vs Cookie



#### JPA query

- https://arahansa.github.io/docs_spring/jpa.html#jpa.query-methods
- 따로 `@Query` annotation을 지정하지 않아도, 이름만으로 쿼리를 **추정** 해줌.
- 그 예시

| Keyword             | Sample                                                       | JPQL snippet                                                 |
| :------------------ | :----------------------------------------------------------- | :----------------------------------------------------------- |
| `And`               | `findByLastnameAndFirstname`                                 | `… where x.lastname = ?1 and x.firstname = ?2`               |
| `Or`                | `findByLastnameOrFirstname`                                  | `… where x.lastname = ?1 or x.firstname = ?2`                |
| `Is,Equals`         | `findByFirstname`,`findByFirstnameIs`,`findByFirstnameEquals` | `… where x.firstname = 1?`                                   |
| `Between`           | `findByStartDateBetween`                                     | `… where x.startDate between 1? and ?2`                      |
| `LessThan`          | `findByAgeLessThan`                                          | `… where x.age < ?1`                                         |
| `LessThanEqual`     | `findByAgeLessThanEqual`                                     | `… where x.age ⇐ ?1`                                         |
| `GreaterThan`       | `findByAgeGreaterThan`                                       | `… where x.age > ?1`                                         |
| `GreaterThanEqual`  | `findByAgeGreaterThanEqual`                                  | `… where x.age >= ?1`                                        |
| `After`             | `findByStartDateAfter`                                       | `… where x.startDate > ?1`                                   |
| `Before`            | `findByStartDateBefore`                                      | `… where x.startDate < ?1`                                   |
| `IsNull`            | `findByAgeIsNull`                                            | `… where x.age is null`                                      |
| `IsNotNull,NotNull` | `findByAge(Is)NotNull`                                       | `… where x.age not null`                                     |
| `Like`              | `findByFirstnameLike`                                        | `… where x.firstname like ?1`                                |
| `NotLike`           | `findByFirstnameNotLike`                                     | `… where x.firstname not like ?1`                            |
| `StartingWith`      | `findByFirstnameStartingWith`                                | `… where x.firstname like ?1` (parameter bound with appended `%`) |
| `EndingWith`        | `findByFirstnameEndingWith`                                  | `… where x.firstname like ?1` (parameter bound with prepended `%`) |
| `Containing`        | `findByFirstnameContaining`                                  | `… where x.firstname like ?1` (parameter bound wrapped in `%`) |
| `OrderBy`           | `findByAgeOrderByLastnameDesc`                               | `… where x.age = ?1 order by x.lastname desc`                |
| `Not`               | `findByLastnameNot`                                          | `… where x.lastname <> ?1`                                   |
| `In`                | `findByAgeIn(Collection ages)`                               | `… where x.age in ?1`                                        |
| `NotIn`             | `findByAgeNotIn(Collection age)`                             | `… where x.age not in ?1`                                    |
| `True`              | `findByActiveTrue()`                                         | `… where x.active = true`                                    |
| `False`             | `findByActiveFalse()`                                        | `… where x.active = false`                                   |
| `IgnoreCase`        | `findByFirstnameIgnoreCase`                                  | `… where UPPER(x.firstame) = UPPER(?1)`                      |

