# 질문답변 게시판
## 진행 방법
* 질문답변 게시판에 대한 html template은 src/main/resources 디렉토리의 static에서 확인할 수 있다. html template을 통해 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 코드스쿼드의 온라인 코드 리뷰 과정](https://github.com/code-squad/codesquad-docs/blob/master/codereview/README.md)
* [동영상으로 살펴보는 코드스쿼드의 온라인 코드 리뷰 과정](https://youtu.be/a5c9ku-_fok)


----------------------


# 스프링 부트로 QA 게시판 구현 1
[Heroku 링크](https://codesquad-qnaboard.herokuapp.com/)

## step2. 데이터베이스 활용

#### H2 DB 설정
- build.gradle
```java
dependencies {
    ...
    compile("org.springframework.boot:spring-boot-starter-data-jpa")
    compile("com.h2database:h2")
    ...
}
```
- application.properties
```java
# DB Connection 설정
spring.datasource.url=jdbc:h2:mem://localhost/~/java-qna;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
# 실행 쿼리 보기 설정
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
# 테이블 자동 생성 설정
## 서버를 시작하는 시점에 DB 테이블을 drop후에 다시 생성하도록 설정하는 방법.
spring.jpa.hibernate.ddl-auto=create-drop
# h2 db console에 접근 설정
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

### # 기능 구현
- User 클래스를 DB 테이블에 매핑 ... complete
- 사용자 데이터에 대한 CRUD 구현 ... complete
- Controller에서 Repository 사용
    - 사용자 데이터 추가 ... complete
    - 사용자 목록 조회 ... complete
    - 사용자 조회 ... complete
    - 질문 데이터 조회 ... complete
    - 질문 목록 조회 ... complete
    - 질문 상세 조회 ... complete
- 회원정보 수정 ... complete
