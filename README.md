# Step4 

## 구현하며 배운점
- 이전에 ORM은 객체지향과 DB에 대해 잘 알아야한다고 했는데 그 이유를 알 것 같다. 예를 들어, findByDeletedFalse()라는 메서드는 DB에서 쿼리를 조회할 수 있어야 이해하고 쓸 수 있다. 

## 구현할 것 
- Question에 @OneToMany로 Answer와 매핑한다. -> Question 속성에 answer를 만들어서 mapping 하나?  
- 질문 삭제하기 실습 (step3)에 비해 기능 업그레이드. 질문 데이터를 삭제하지 않고 질문의 속성에 deleted를 추가해서 true/false로 바꾼 뒤 저장한다. -> 그럼 QuestionRepo에서 question을 찾을 때 deleted가 false인 것만 찾는 메서드 추가? 

## 진행하며 배운 것
- auto reload는 컨트롤러를 새롭게 생성하는 건 반영하지 않는다. ->  HotSwapAgent라는 플러그인을 사용하면 될 거 같은데, DCEVM에서 동작한다고 한다. DCEVM은 확장된 JVM이라고 한다. 아직 사용은 안함.
[https://plugins.jetbrains.com/plugin/9552-hotswapagent](https://plugins.jetbrains.com/plugin/9552-hotswapagent)


## 질문 수정, 삭제 기능 구현 
- textarea에 수정하려는 데이터 넣으려면 태그 안에 넣어준다. ex) <>{{contents}}<> 
- isLoginUser(User user)가 아니라 sesstion을 넣어서 해결하도록 만들기.
- hashcode()와 Equal() 재정의 필요, user.isSameWriter(User loginUser)를 체크할 때, loginUser가 들어가기 때문에 항상 false가 뜬다. 이 때 equal()와 hashcode()를 재정의해야 한다.
- Jpa에서 LocalDateTime을 DB에 넣을 때 TimeStamp로 만들어서 넣어주기. 근데 자동으로 되는 이유는? 
- DB에서 CURRENT_TIMESTAMP() 사용하면 현재 시간 들어감. 

## 답변 기능 구현 
- Answer 객체에 @ManyToOne으로 User 객체와 매핑.
- Answer와 Question의 contents에 @Lob 어노테이션 추가하면 DB 타입이 길어진다.
- Answer는 Question에 종속되어 있기 때문에 AnswerController에서 @RequestMapping("/question/{questionId}/answers}로 대체할 수 있다. )
- Question에서 List<Answer> answers를 @OneToMany로 묶으면 View에는 question만 전달하면 된다.
- String.format("rediret:/questions/%d", questionId) 로 표현 가능. 

## 리팩토링 
- 반복되는 로그인 유저 확인 작업을 리팩토링
- 접근 권한이 없으면 로그인 페이지로 이동하는데 이 때, 에러메세지 출력 유도. login_failed.html에서 alert form을 복사해서 사용.

## 질문 삭제하기 
- DB에서 삭제하지 않고 deleted : boolean 상태로 데이터 유무를 알아야 한다. List<Question> findAllByDeleted()를 QuestionRepository에 추가. 0 
- 답변이 없는 경우 삭제 가능 : DB에서 찾은 Question의 answers가 isEmpty()인 경우 삭제 0 
- 질문자와 답변글의 모든 답변자(writer)가 같은 경우 삭제 가능. -> 반복문 돌며 찾기? 0
- 질문 삭제 시 답변도 삭제할 때 deleted : boolean를 변경한다.  0
- 답변 삭제 한 후 deleted = true;로 변경. show에는 deleted가 false인 answers만 넘긴다. 

findByDeletedFalse() -> 인자 필요없이 Deleted가 false인 애들을 가져온다?
