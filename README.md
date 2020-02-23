# 질문답변 게시판
## 진행 방법
* 질문답변 게시판에 대한 html template은 src/main/resources 디렉토리의 static에서 확인할 수 있다. html template을 통해 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 코드스쿼드의 온라인 코드 리뷰 과정](https://github.com/code-squad/codesquad-docs/blob/master/codereview/README.md)
* [동영상으로 살펴보는 코드스쿼드의 온라인 코드 리뷰 과정](https://youtu.be/a5c9ku-_fok)

---

[헤로쿠](https://sheltered-lake-66847.herokuapp.com/)

# STEP1

## 요구사항

- 회원가입, 사용자 목록 기능 구현
- 질문하기, 질문 목록 기능 구현

## 진행과정

1. handlebar template-engine과 Spring Boot가 정상적으로 동작하는지 확인하기 위하여, 간단한 Application을 만들었습니다.
2. Spring Tutorial에 따라서 devtools 의존성을 추가하였습니다.
3. Live Reload시 static 파일도 같이 reload되도록 하기 위하여 `application.properties`를 수정하였습니다.
4. User Controller를 추가하고, /users/form 요청이 들어오면 회원가입 창이 출력되도록 하였습니다.
5. User Controller에 생성하는 기능 추가하기
6. User들의 정보를 출력하는 기능 추가
7. User의 정보를 출력하는 기능 추가
8. 공통 부분을 Template으로 만들어서 추출하기
9. 비슷한 화면인 질문 화면을 동적페이지로 만들기
10. Question의 목록을 출력하는 화면 만들기
11. Question의 상세 정보를 출력하는 화면 만들기
12. heroku 배포하기 (https://sheltered-lake-66847.herokuapp.com/)
13. 사용자 정보 수정기능 추가하기

## STEP1 리뷰된 내용 수정

1. Honux가 제안한 대로 Create 시에 User를 직접 받는 방식이 동작해서 해당 방식이 더 소스가 깔끔한 것 같아 변경하였습니다.
   `@RequiredParam` 을 적용해보았는데 `@RequiredParam`은 name 필드명과 메소드의 매개변수명이 일치해야해서 불가능 한 것 같습니다.
2. 여러 시간대를 한꺼번에 대응할 필요가 없다면 `ZonedDateTime` 보다는 `LocalDateTime` 를 사용해달라고 하셔서 이에 대해서 알아보고 변경하였습니다.
   또 필드명을 `dateTime` 에서 `createdDateTime` 으로 수정하였습니다. 그리고 Format된 형태로 표시해주기 위해서 `getFormattedCreatedDateTime()` 메서드를 생성하였습니다.
   honux의 조언대로 `{{formattedCreatedDateTime}}` 으로 호출했더니 동작하였습니다.
3. replyCount보다는 reply에 대한 자료구조를 가지는게 좋을 것 같아 수정하였습니다. (Reply에 대한 명확한 설계가 없어 Object타입을 저장하도록 하였습니다.)

## STEP2 준비

1. spring-data-jpa 의존성 추가 및 h2 database의 의존성을 추가하였습니다.
2. db에 대한 설정을 해주었습니다. 스프링 부트 진짜 짱인듯.. 👍
3. JPA를 사용한 코드로 일부를 변경하였습니다. findById를 하는 과정에서 Optional 객체를 리턴해주어서 존재하지 않을시에 orElseThrow로 예외를 전달하는 방식으로 처리하였습니다.
4. User Controller의 내용을 JPA를 사용한 코드로 변경하였습니다. Hibernate는 save시에 insert와 update를 알아서 해주는 것을 보고 놀랐습니다.
5. Update시에 PUT 메소드로 전송할 수 있도록 하였습니다. 설정하는게 조금 귀찮았습니다.
   - [참고1](https://hue9010.github.io/spring/Spring-MVC-PUT,-DELETE-%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0/)
   - [참고2](http://honeymon.io/tech/2019/11/06/spring-boot-2.2.html)
   둘 다 적용해야 합니다. 💩
6. Question 부분을 JPA를 사용하여 리팩토링하였습니다.
7. 기존 코드를 리팩토링 하였습니다.
   - Common Class에 private 생성자를 추가하였습니다. (Util성 클래스이므로)
   - 공통으로 사용되는 Error Page String을 Common 클래스로 이동하였습니다.
   - 영속성 엔티티를 바로 Request를 받아서 Save 하지 않도록 하였습니다.
      - 오류를 발생시킬 가능성이 있기 때문입니다.
   - user와 users가 혼용되어 있어 users로 통일하였습니다.
   - questions와 qna를 questions로 통일하였습니다.

## STEP2 리뷰 사항 적용

1. 빈 블록을 깔끔하게 닫아주었습니다.
2. private method에 `@PathVariable` 애노테이션이 메소드 추출과정에서 자동으로 붙어서 제거하였습니다.
3. User 생성시 User를 파라미터로 직접 받도록 수정하였습니다. (검증로직은 어떻게 추가할지 고민해 보려고 합니다.)
4. DateTimeFormatter를 static field로 추출하였습니다.

# STEP3

1. 로그인 페이지를 template으로 옮기고 login session 기능을 구현했습니다.
2. 로그인 여부에 따라 navbar에 표시되는 메뉴가 다르게 표시되도록 하였습니다.
3. 테스트 데이터를 추가하였습니다.
4. 로그인한 사용자 이외의 사용자를 수정하려고 하는 경우 에러페이지를 출력하도록 하였습니다.
5. 질문하기 버튼이 로그인된 사용자에게만 표시되도록 변경하였습니다.
