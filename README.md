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

     

