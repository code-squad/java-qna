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

# Step 1

### 배포

[헤로쿠 배포 URL](https://shrouded-cove-08217.herokuapp.com)



### 구현한 기능

1. 회원가입 - `JOIN US`
2. 회원 목록 보기 - `LIST`
3. 회원 프로필 정보보기 - `PROFILE` , `LIST`에서 `NAME`을 클릭해서 봐야 정보 확인 가능
4. HTML 중복 제거
5. 질문하기 - `QnA` > `질문하기`
6. 질문 목록 보기 - `QnA`
7. 질문 상세 보기 - `QnA` > `제목`



### 업데이트 계획

- [x] 회원정보 수정

- [ ] log4js 적용

- [ ] HTML 중복 제거 - URL과 html 연결하여 제거

* [x] 질문하기 폼 디자인 수정

---

# Step 2

### 구현한 기능

1. 회원 정보를 DB에 저장 및 조회 - `UserRepository`

2. 질문 데이터를 DB에 저장 및 조회 - `QuestionRepostory`

3. 회원 정보 수정 - `User`클래스의 `update()`

   1. 회원 목록에서 회원가입한 사용자의 정보를 수정할 수 있어야 한다.

   2. 비밀번호, 이름, 이메일만 수정할 수 있으며, 사용자 아이디는 수정할 수 없다.

   3. 비밀번호가 일치하는 경우에만 수정 가능하다. -  `User`클래스의 `checkPassword()`

      비밀번호가 맞지 않은 경우 `ResponseStatusExceptio(HttpStatus.FORBIDDEN)` 에러 처리



### 업데이트 계획

- [ ] 비밀번호가 맞지 않은 경우, HTTPStatus Code를 403 으로 돌려주도록 수정
- [ ] 존재하지 않는 회원 조회 시, 404 돌려주도록 수정



> 질문 : JpaRepository의 getOne() vs CurdRepository의 findById() 중 어떤 것을 사용할지 선택의 기준이 궁금합니다.

사용자 정보 조회 : UserController → getOne(id)
질문 상세보기 : QuestionController → findById(id)
제가 참고한 자료는 → [Difference between getOne and findById in Spring Data JPA?](https://www.javacodemonk.com/difference-between-getone-and-findbyid-in-spring-data-jpa-3a96c3ff) 인데,
결국 id로 조회해온 객체를 모델에 담으려면 값을 가져와야하기 때문에, 지연로딩과 즉시로딩 차이가 없는 건가요?? 처음에는 현재 id로 찾아온 객체로 속성값에 엑세스할 필요가 없으므로 getOne()을 사용하는 것이 맞는(좀 더 좋은 성능)건가 생각했었습니다. (대량의 데이터를 가지고 있다는 가정하에....)

3단계에서는 어느 사용자가 작성한 질문인지 User 정보를 담고 있어야 하는데 이때는 user정보를 getOne()으로 값을 가져오는 것과 findById()로 가져오는 것의 차이가 있나요??

> 답 : 거의 대부분 findById를 사용합니다.

지연로딩과 즉시로딩의 차이 이전에 반환값이 Optional 이라는 것 때문에 더 선호되는 것 같습니다.
Optional 로 받게 되면 값이 없을때 혹은 있을때의 처리하는 로직을 만들기 용이합니다.

findOne은 저도 막 찾아봤는데 일단 참조만 넘어오고 메모리를 로드하지 않고 있다는 점이 성능상 유리할 수는 있겠으나 얼만큼 더 유리한지 생각해 볼 필요는 있겠네요^^



# Refactor with Code Review

[Refactor] step2 코드리뷰 수정

1. boolean 체크 메서드 네이밍 수정 : checkPassword → matchPassword

2. 주석달린 `코드` 제거

3. 불필요한(사용하지 않는) 주석 제거

4. 자바 빈 메서드의 형태는 변경하지 않는다는 컨벤션에 따라 수정

   setCreatedTime() 세터는 기본 형태를 유지하도록 하고, 현재 시간을 담아주는 setCreatedTimeNow() 메소드를 추가하여 생성자에서 값을 넣어주도록 수정

5. 기존 비밀번호 확인 메서드에서 넘겨받는 인자를 User객체로 수정

   메서드로 넘겨지는 인자의 정보에 따라 형성되는 객체간의 의존도를 고려하여 User객체 정보를 넘겨주는 것이 선호됨
   업데이트하기 위한 새로운 회원정보를 가지고 있는 User객체의 네이밍을 user → sessionUser 로 수정



---


# Step 3

### 구현한 기능

1. 로그인 기능 구현 - 세션에 저장

2. 세션을 담당하는 별도의 유틸 클래스 생성 - HttpSessionUtils

3. 리팩토링 - 객체로 부터 데이터를 꺼내서 쓰는 getter 로직을 객체가 일을 하도록 수정

   `matchId`, `matchPassword` , `matchUser` etc..

4. 질문하기, 수정&삭제 기능

5. User와 Question 연결

   Question에서 User를 바라보도록 관계를 설정 `@ManyToOne`

   ```java
   @ManyToOne
   @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
   private User writer;
   ```

6. Answer 구현

   - 로그인한 사용자는 답변을 추가할 수 있다.
   - 자신이 쓴 답변을 삭제할 수 있다.

   * 답변 - 유저 관계 설정

     한명의 사용자가 여러개의 답변을 달 수 있기 때문에 `@ManyToOne` 

     ```java
     @ManyToOne
     @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
     private User writer;
     ```

   * 답변 - 질문 관계 설정

     답변은 무조건 질문글에 달리는 comment 이기 때문에 질문 하나에 여러개의 답변이 달린다. `@ManyToOne`

     ```java
     @ManyToOne
     @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
     private Question question;
     ```

   * `QuestionController`에서 `findByQuestionId(questionId)` 를 구현해서 현재 질문 id에 달려있는 답변의 목록을 가져와서 VIEW에 전달한다.

     `Question`에서 답변의 갯수를 가지고 있도록 구현

     ```java
     model.addAttribute("answers", answerRepository.findAllByQuestionId(id));
     ```



---

### 질문 답변 수 구할 때 이슈 (code review)

```java
currentQuestion.increaseAnswersCount();
```

1. increaseAnswersCount() 는 나중에 답변개수를 간편하게 보여줄 때 유용한 기능 같지만, 데이터 정합성이 굉장히 취약한 점이 있습니다. 스프링의 요청은 멀티 스레드로 받게 되는데(동시에 여러개를 처리합니다.)

   만약 하나의 질문에 (많이 과장해서) 1억명이 동시에 답변을 작성한다면 Question의 answerCount필드는 1억이 아닐 가능성이 많습니다. 이유를 한번 고민해 보시면 많은 도움이 되실 거에요!

2. 그리고 이 메서드를 그대로 사용한다면 increaseAnswerCount를 컨트롤러에서 호출하는 것보다는 처럼 작성하는 방법도 있을 수 있겠습니다.

```java
public Answer(User writer, Question question, String comment) {
        this.writer = writer;
        this.question = question;
        this.comment = comment;
        this.createdTime = LocalDateTime.now();

        this.question.increaseAnswersCount();
}
```

3. 한 가지 더! 난이도가 조금 있는 내용입니다..

   분명 조회한 question 객체의 필드를 변경했는데(답변수) 다시 저장하지 않아도 될까요?
   다시 저장하지 않고도 db 업데이트가 되었다면 (꼭 확인해보세요!!) 이유는 무엇일까요?

   → 다시 저장하지 않았는데 데이터베이스를 확인한 결과 알아서 update가 되어있다!!!! 왜지?? 

`answerRepository.save(answer);` 를 실행하면 Question 테이블이 Answer과 연동되어 있기 때문에 QuestionRepository의 업데이트된 필드값을 가지고 update쿼리를 알아서 날려주고있다.

(댓글이 달린 해당 게시물뿐만 아니라 Question테이블에 담겨있는 다른 게시글이 수정되어도 같이 update가 실행되지만, 전혀 매핑되어있지 않은 테이블의 값이 수정되는 경우에는 해당 테이블의 값에 대해서만 쿼리가 실행되었다.)

⇒ 엔티티 매니저가 엔티티가 변경이 일어나면 이를 자동 감지하여 데이터베이스에 반영하기 때문이라는 것을 알게되었습니다.
[참고-JPA 변경 감지와 스프링 데이터](https://medium.com/@SlackBeck/jpa-변경-감지와-스프링-데이터-2e01ad594b82)

![image-20200310161905524](https://tva1.sinaimg.cn/large/00831rSTgy1gcouw8fnhyj30zs0u014p.jpg)





#### 답변 수 성능 개선

Question이 Answer을 가지고 있도록 OneToMany 관계를 맺어주고 매핑해준다.
답변 수를 구하기위해 answers 리스트의 size()를 이용하면 불필요하게 연관 엔티티를 사용하기때문에 데이터가 많아질수록 SQL실행 속도가 느려질 뿐만 아니라 데이터를 담는 컬렉션도 많은 메모리를 사용하기 때문에 점점 성능이 떨어지는 이슈가 생긴다.
따라서 다른 연관 엔터티 속성을 사용하지 않고 카운트만 따로 조회하기위해 JPQL 카운트 쿼리를 만들어 실행하도록 하였다.

[참고-JPA 변경 감지와 스프링 데이터](https://medium.com/@SlackBeck/jpa-변경-감지와-스프링-데이터-2e01ad594b82)



---

# Step4

### 구현한 기능

1. 질문 삭제 구현
   - [x] 질문 데이터를 완전히 삭제하는 것이 아니라 데이터의 상태를 삭제 상태(deleted - boolean type)로 변경한다.
   - [x] 로그인 사용자와 질문한 사람이 같은 경우 삭제 가능하다.
   - [x] 답변이 없는 경우 삭제가 가능하다.
   - [x] 질문자와 답변 글의 모든 답변자 같은 경우 삭제가 가능하다.
   - [x] 질문을 삭제할 때 답변 또한 삭제해야 하며, 답변의 삭제 또한 삭제 상태(deleted)를 변경한다.
   - [x] 질문자와 답변자가 다른 경우 답변을 삭제할 수 없다.



### 질문글 목록을 가져오는 조회 쿼리 메소드를 생성

> 검색 : jpa crudrepository select where
>
> → [SpringBoot JPA 예제](https://jdm.kr/blog/121)
>
> → [메소드 이름 기반 작성법](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation)

JPA 처리를 담당하는 Repository 중 `CrudRepository`는 관리되는 엔티티 클래스에 대해 정교한 CRUD 기능을 제공한다. 

특정 조건에 맞는 조회쿼리를 이용하는 방법은 2가지가 있다.

1. @Query 어노테이션을 이용
2. 메소드 이름 기반 작성법으로 쿼리 생성

| `True`  | `findByActiveTrue()`  | `… where x.active = true`  |
| ------- | --------------------- | -------------------------- |
| `False` | `findByActiveFalse()` | `… where x.active = false` |

Question 데이터 중 Deleted 컬럼의 값이 false 인 경우만 가져오도록 한다.

where deleted = false ⇒ `findByDeletedFalse`

```java
public interface QuestionRepostory extends CrudRepository<Question, Long> {
    List<Question> findByDeletedFalse();
}
```

```java
// QuestionController.java
@GetMapping("")
public String viewQnaList(Model model) {
  model.addAttribute("questions", questionRepostory.findByDeletedFalse());
  return "/questions/list";
}
```



---

### QuestionController 중복 코드 제거 리팩토링

중복 코드

1. 로그인한 유저인지 체크

2. 글쓴 유저인지 확인

3. 글쓴 유저가 아닌경우 페이지를 리다이렉트하던 코드를 사용자에게 에러메시지를 넘겨주도록 수정

   (사용자가 요청에 대한 결과가 어떤 상태인지 알 수 있도록 exception을 이용해서 구현)



리팩토링

1. Exception을 이용해서 중복 제거

​				권한이 있는지 여부를 확인하는 메소드(`hasPermission`)를 생성

2. 권한만 체크해서 가져오는 것이 아니라 아예 결과를 return해주도록 별도의 클래스 생성 (결과 객체)

3. try ~ catch 문을 없애주기위해 `@ExceptionHandler` 어노테이션으로 전역에 발생한 에러를 잡아서 처리해주도록 한다.

​	`@ExceptionHandler` 는 별도로 catch되지 않은 전역에 발생한 에러를 잡아준다.



* 존재하지 않는 데이터 요청 시 404 리턴

![image-20200312114719399](https://tva1.sinaimg.cn/large/00831rSTgy1gcqya2tlroj30y40u04ct.jpg)



* 권한없는 요청 시 403 리턴 

![image-20200312114519607](https://tva1.sinaimg.cn/large/00831rSTgy1gcqy802qpuj30y70u07jj.jpg)



### Todo list

- [ ] `ValidationResult` 객체로 리턴한 부분은 200 상태코드를 넘기고 있다. 상태코드 바꿔주기

  ![image-20200312113839839](https://tva1.sinaimg.cn/large/00831rSTgy1gcqy14uxt8j30y80u0k5r.jpg)

- [ ] @ControllerAdvice를 이용한 Exception 헨들링

  클리스마다 `@ExceptionHandler`로 에러 헨들링하는 코드가 중복되어 있어서 전역에서 발생하는 에러를 잡아주기위해 별도의 컨트롤러 이용

- [ ] 에러메시지 enum으로 만들기

