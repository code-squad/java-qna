
### Heroku

- https://wooody92.herokuapp.com/

### 0. 구현 기능

- 답변 추가하기
- 답변 삭제하기
- 질문 삭제하기
  - 질문, 답변 데이터를 완전히 삭제하는 것이 아니라 데이터의 상태를 삭제 상태(deleted - boolean type)로 변경
- 로깅 라이브러리 사용 (Log4j2 기초)
- 피드백 반영

### 1. 보완 예정

- Exception 사용 목적이 명확히맞게 새로 정의 후 변경하기
- toString -> toStringBuilder class 활용 변경하기
- 댓글 갯수 기능 추가하기 -> @Formula 활용하기

### 2. 피드백 (step3)

1. 일반적으로 **필드의 getter는 필드 class 그대로 반환하는 것이 컨벤션**이다. 원하는 포맷으로 반환하고자 한다면 새로 메서드를 추가하는 것이 바람직 하다.

2. **유틸성 메서드의 경우 static import**하여 사용하면 코드 가독성을 높일 수 있다. (HttpSessionUtils.isLogin -> isLogin)

3. **Optional을 사용**하여 저장소의 해당 값 null 검사와 조회를 동시에 처리할 수 있다. (findById.get -> findById.orElseThrow)

4. NullPointerException 같은 경우는 버그로 인식되는 것이기에, try-catch로 정상 플로우를 타게하는 것이아니고 **해당 부분의 null 처리를 진행하고 필요하다면 다른 예외를 발생시켜 처리**하도록 한다. (Optional 처리와 일맥상통)

5. 예외처리 관련 참고 블로그

   ```
   https://cheese10yun.github.io/spring-guide-exception/
   ```

6. 객체가 **양방향 연관관계에서 toString Override 시 stackOverflow 이슈 주의**해야 한다. toStringBuilder 클래스 등으로 해결 할 수 있다.

7. JpaRepository.delete에서 equals, hashmap 이 없음에도 객체를 인식하고 지우는 원리? 우선 해당 entity 조회 후 결과값을 제거하는 것으로 보이는데 잘모르겠음..

   ```
   https://jojoldu.tistory.com/235
   ```

-------
# Step 4

## [회원과 질문간의 관계 매핑 및 생성일 추가](https://youtu.be/ByHw1gVQOe4)

- User와 Question 간의 관계를 매핑한다. User는 너무 많은 곳에 사용되기 때문에 User에서 관계를 매핑하기 보다는 Question에서 @ManyToOne 관계를 매핑하고 있다.
- Question에 생성일을 추가한다.

## [질문 상세보기 기능](https://youtu.be/T9DWlpWMlF4)

- 질문 상세보기 기능 구현

## [질문 수정, 삭제 기능 구현](https://youtu.be/asCxX-eUSvU)

- PUT(수정), DELETE(삭제) HTTP method를 활용해 수정, 삭제 기능을 구현

## [수정/삭제 기능에 대한 보안 처리 및 LocalDateTime 설정](https://youtu.be/UMEmYw7EJ7g)

- Back End 프로그래밍에서 정말 중요한 보안 관련된 기능 구현
- JPA에서 LocalDateTime을 DB 데이터타입과 제대로 매핑하지 못하는 이슈 해결

## [답변 추가 및 목록 기능 구현](https://youtu.be/GvVFQom_SGs)

- 답변 기능을 담당할 Answer를 추가하고, Question, User와 매핑
- Question에 Answer를 @OneToMany로 매핑. 이와 같이 매핑함으로써 질문 상세보기 화면에서 답변 목록이 동작하는 과정 공유

## [QuestionController 중복 코드 제거 리팩토링](https://youtu.be/g-nsT3NRK2o)

- QuestionController에서 보안 처리를 위해 구현한 중복 코드를 제거
- 중복을 Exception을 활용한 제거와 Result와 같은 새로운 클래스르 추가해 제거

------

# User와 Question 연결

## 요구사항

> 현재 Question의 글쓴이는 User의 name 값을 가지는 것으로 구현했다.
>
> 이와 같이 구현하는 경우 User의 name을 수정하는 경우 Question의 글쓴이와 다른 값을 가지는 문제가 발생한다.
> 이 문제를 해결하기 위해 User의 name이 변경될 때마다 Question의 writer 값을 수정할 수도 있지만 이와 같이 구현할 경우 writer가 같은 이름을 가지는 경우 문제가 될 수 있다.
>
> 이 같은 문제 상황에 대해 원론적으로 문제가 발생하지 않도록 해결한다.

## 힌트

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

# 답변 추가하기

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

## @OneToMany 매핑 전략

- Question이 Answer와 @OneToMany 관계를 가지도록 매핑한다.
  - 다음 설정의 “question”은 Answer에서 @ManyToOne으로 매핑한 필드의 이름이다.

```
@OneToMany(mappedBy="question")
코드복사
```

------

## 답변 삭제하기

> 자신이 쓴 답변에 한해 삭제할 수 있다.

#### 힌트

- 답변은 질문에 종속되기 때문에 URL 매핑을 다음과 같이 할 수 있다.
  - "/questions/{questionId}/answers/{id}" URL로 @DeleteMapping 애노테이션으로 매핑한다.
- HTML은 기본으로 GET과 POST만 지원한다. DELETE를 지원하지 않기 때문에 꼼수를 써야 한다.
  - 삭제 버튼을 클릭하는 태그에서 _method 이름의 hidden 값으로 DELETE를 전달해야 서버측에서 @DeleteMapping을 사용할 수 있다.

------

## 질문 삭제하기 실습(선택)

#### 요구사항

> 질문 삭제 기능을 구현한다. 질문 삭제 기능의 요구사항은 다음과 같다.

- 질문 데이터를 완전히 삭제하는 것이 아니라 데이터의 상태를 삭제 상태(deleted - boolean type)로 변경한다.
- 로그인 사용자와 질문한 사람이 같은 경우 삭제 가능하다.
- 답변이 없는 경우 삭제가 가능하다.
- 질문자와 답변 글의 모든 답변자 같은 경우 삭제가 가능하다.
- 질문을 삭제할 때 답변 또한 삭제해야 하며, 답변의 삭제 또한 삭제 상태(deleted)를 변경한다.
- 질문자와 답변자가 다른 경우 답변을 삭제할 수 없다.
