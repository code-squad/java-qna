
### 0. Heroku

- https://wooody92.herokuapp.com/

### 1. 구현 기능

- 세션을 활용 한 아래 기능 구현
  - 로그인
  - 개인정보 수정
  - 로그인 상태에서 질문, 수정, 삭제 기능 
- @ManyToOn 매핑을 이용한 User-Question 연결
- 테스트 데이터 추가
- Step2 피드백 리팩토링
  - LocalDateTime class 사용
  - 불필요 주석제거
  - wrapper class -> primitive type 변경
  - JpaRepository와 CrudRepository의 차이
- 401 unauthorized, 404 not found 에 대한 에러 페이지 추가

### 2. 회고

어디서부터 어떻게 시작할까 막막할 때, pobi님의 동영상 강의가 방향을 잡아줘서 참 좋은 것 같다. 이번 스텝에서는 에러페이지 html 생성 후 예외처리로 해당 에러 url로 연결시켜 주었는데, 다음 스텝에서는 spring boot framework의 error controller class 학습 후 활용해봐야 겠다.

-------
# STEP 3
# 인증 기반으로 구현하는 동영상

## [로그인 기능 구현](https://youtu.be/-J9f_LQILCY)

- 로그인 성공과 실패에 대한 처리 구현
- 로그인이 성공할 경우 세션에 로그인 상태 저장

## [로그인 상태에 따른 메뉴 처리 및 로그아웃 ](https://youtu.be/9xmTAmyv_ic)

- 로그인 유무에 따라 상단 메뉴 처리.
  - 로그인 상태이면 개인정보수정과 로그아웃 메뉴, 로그아웃 상태이면 로그인과 회원가입 메뉴가 나타나도록 처리함.
- 로그아웃 기능 구현함

## [자기 자신의 정보만 수정](https://youtu.be/HfW5kvsaAEA)

- 로그인한 사용자의 경우에 한해 자기 자신의 정보만 수정 가능하도록 구현

## [중복 제거, clean code, 쿼리 보기 설정](https://youtu.be/DaqWKDvdmAk)

- 개발 과정에서 발생한 중복 코드를 제거
- SQL 쿼리를 볼 수 있도록 설정

## [질문하기, 질문 목록 기능 구현](https://youtu.be/aaC07qy3JXQ)

- 로그인한 사용자에 대한 질문 가능하도록 구현
- 질문 목록 기능 구현

------

# 로그인 기능 구현

## 요구사항

> 로그인이 가능해야 한다.
>
> 현재 상태가 로그인 상태이면 상단 메뉴가 “로그아웃”, “개인정보수정”이 나타나야 하며, 로그아웃 상태이면 상단 메뉴가 “로그인”, “회원가입”이 나타나야 한다.

------

## 로그인 기능 구현 힌트

- 로그인이 성공하는 경우 HttpSession에 로그인 정보 추가

```
session.setAttribute(“sessionedUser", user);
코드복사
```

- Spring MVC에서 HttpSession 메소드의 인자로 전달 가능

```java
@PostMapping("/login")
public String login(String userId, String password, HttpSession session) {
   // 로그인 로직 구현
}
코드복사
```

- UserRepository에서 userId에 해당하는 데이터 조회

```java
public interface UserRepository extends JpaRepository<User, Long>{
    User findByUserId(String userId);
}
코드복사
```

------

## 로그인에 따른 메뉴 처리 구현 힌트

#### 세션 설정

- Mustache에서 HttpSession 데이터를 접근하기 위한 설정(application.properties)

```
handlebars.expose-session-attributes=true
코드복사
```

- URL에 jsessionid가 추가되는 이슈를 해결하는 설정

```
// spring boot 1.5
server.session.tracking-modes=cookie

// spring boot 2.x
server.servlet.session.tracking-modes=cookie
코드복사
```

#### mustache/handlebars에서 if/else

```
{{^sessionedUser}}
    [[HTML 구문]
{{/sessionedUser}}
{{#sessionedUser}}
    [[HTML 구문]
{{/sessionedUser}}
코드복사
```

------

# 테스트 데이터 추가

로그인 기능을 테스트하기 위해 매번 회원가입을 먼저 해야 한다.

로그인 기능을 테스트하는데 어려움이 있다. 테스트 데이터를 미리 추가한 후 개발을 하면 좋겠다.

## 테스트 데이터 추가 방법

- src/main/resources 폴더에 data.sql 파일을 추가한다.
- 사용자 데이터를 다음과 같이 insert sql 쿼리를 추가한다.

```
INSERT INTO USER (id, user_id, password, name, email) VALUES (1, 'javajigi', 'test', '자바지기', 'javajigi@slipp.net');
INSERT INTO USER (id, user_id, password, name, email) VALUES (2, 'sanjigi', 'test', '산지기', 'sanjigi@slipp.net');
코드복사
```

- 권한 체크에 대한 테스트를 위해 2명 이상의 테스트 데이터를 추가한다.

------

# 개인정보 수정

## 요구사항

> 회원가입한 사용자의 정보를 수정할 수 있어야 한다.
>
> 이름, 이메일만 수정할 수 있으며, 사용자 아이디는 수정할 수 없다.
>
> 비밀번호가 일치하는 경우에만 수정 가능하다.

------

#### 힌트

- HttpSession에 저장된 User 데이터를 가져온다.

```
Object value = session.getAttribute(”sessionedUser");
if (value != null) {
    User user = (User)value;
}
코드복사
```

- 로그인한 사용자와 수정하는 계정의 id가 같은 경우만 수정하도록 한다.
- 다른 사용자의 정보를 수정하려는 경우 에러 페이지를 만든 후 에러 메시지를 출력한다.

------

# 질문 기능 구현 실습

## 요구사항

> 사용자는 질문을 할 수 있으며, 모든 질문을 볼 수 있다.
>
> 단, 질문을 할 수 있는 사람은 로그인 사용자만 할 수 있다.
>
> 질문한 사람은 자신의 글을 수정/삭제할 수 있다.

------

## 질문하기 구현 힌트

- 로그인한 사용자는 질문하기 화면에 접근한다.
- 로그인한 사용자가 누구인지 알 수 있기 때문에 질문하기 화면에서 글쓴이 입력 필드가 삭제해도 된다.
- 로그인하지 않은 사용자는 로그인 페이지로 이동한다.
- Question의 글쓴이 값은 User의 name 값을 가지는 것으로 구현한다.

------

## 질문 수정하기 구현 힌트

- 전체적인 흐름은 개인정보수정의 흐름과 같다.
- 수정화면 접근/수정하기 모두 로그인 사용자와 글쓴이의 사용자 아이디가 같은 경우에만 가능하다.
- 로그인하지 않은 사용자 또는 자신의 글이 아닌 경우 "다른 사람의 글을 수정할 수 없다."와 같은 에러 메시지를 출력하는 페이지로 이동하도록 한다.
- @PutMapping을 사용해 매핑한다.
  - html에서 form submit을 할 때 ``과 같이 PUT method를 값으로 전송한다.

------

## 질문 삭제하기 구현 힌트

- 삭제하기는 로그인 사용자와 글쓴이의 사용자 아이디가 같은 경우에만 가능하다.
- 로그인하지 않은 사용자 또는 자신의 글이 아닌 경우 로그인 화면으로 이동한다.
- @DeleteMapping을 사용해 매핑하고 구현한다.
  - html에서 form submit을 할 때 ``와 같이 PUT method를 값으로 전송한다.

------

# User와 Question 연결 실습(선택)

## 요구사항

> 현재 Question의 글쓴이는 User의 name 값을 가지는 것으로 구현했다.
>
> 이와 같이 구현하는 경우 User의 name을 수정하는 경우 Question의 글쓴이와 다른 값을 가지는 문제가 발생한다.
> 이 문제를 해결하기 위해 User의 name이 변경될 때마다 Question의 writer 값을 수정할 수도 있지만 이와 같이 구현할 경우 writer가 같은 이름을 가지는 경우 문제가 될 수 있다.
>
> 이 같은 문제 상황에 대해 원론적으로 문제가 발생하지 않도록 해결한다.

## 힌트

#### User의 id를 저장하는 방법

- User의 primary key인 id 값을 Question에 저장한다.
- Question을 조회할 때 id 값을 통해 User도 같이 조회한다.

#### @ManyToOne 매핑을 사용하는 방법

- Question에 다음과 같이 @ManyToOne 매핑을 한다.

```java
@Entity
public class Question {
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
	private User writer;
	
	[...]
}
코드복사
```

- 위와 같이 매핑할 경우 Question을 조회할 때 User도 같이 조회한다.

------

# Answer 구현하기 실습(선택)

## 요구사항

> 사용자는 질문 상세보기 화면에서 답변 목록을 볼 수 있다.
> 로그인한 사용자는 답변을 추가할 수 있다.
> 자신이 쓴 답변을 삭제할 수 있다.

## 힌트

- Answer를 추가하고 DB와의 매핑을 한다.
- Question은 Answer와 one-to-many 관계이다. Answer에 @ManyToOne 애노테이션을 활용해 매핑한다.

```java
@Entity
public class Answer {
  @ManyToOne
  @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
  private Question question;

  [...]
}
코드복사
```

- Question에 종속되어 있는 Answer 목록을 조회하는 메소드를 AnswerRepository에 추가한다.
  - findByQuestionId(questionId)와 같은 메소드를 추가한다.
- 답변 기능 구현을 담당할 AnswerController를 추가하고 구현한다.
- 답변은 질문에 종속되기 때문에 URL 매핑을 다음과 같이 할 수 있다.
  - `/questions/{questionId}/answers/{id}`