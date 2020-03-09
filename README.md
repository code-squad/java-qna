# Step4 
[배포 url : https://hyunjun2.herokuapp.com/](https://hyunjun2.herokuapp.com/)

## Step4 진행하며 배운점
- 호눅스가 ORM은 객체지향과 DB에 대해 잘 알아야한다고 했는데 그 이유를 알 것 같다. 예를 들어, findByDeletedFalse()라는 메서드는 DB에서 쿼리를 조회할 수 있어야 이해하고 쓸 수 있다. 
- auto reload는 컨트롤러를 새롭게 생성하는 건 반영하지 않는다. ->  HotSwapAgent라는 플러그인을 사용하면 될 거 같은데, DCEVM에서 동작한다고 한다. DCEVM은 확장된 JVM이라고 한다. 아직 사용은 안함.
[https://plugins.jetbrains.com/plugin/9552-hotswapagent](https://plugins.jetbrains.com/plugin/9552-hotswapagent)

## 질문 수정, 삭제 기능 구현 
- textarea에 수정하려는 데이터 넣으려면 태그 안에 넣어준다. ex) <>{{contents}}<> 
- DB에서 CURRENT_TIMESTAMP() 사용하면 현재 시간 들어감. 

## 답변 기능 구현 
- Answer 객체에서 @ManyToOne으로 User 객체와 매핑.
- Answer와 Question의 contents에 @Lob 어노테이션 추가하면 DB 타입이 확장.
- Answer는 Question에 종속되어 있기 때문에 AnswerController에서 @RequestMapping("/question/{questionId}/answers})로 대체 가능.
- Question에서 List<Answer> answers를 @OneToMany로 묶으면 View에는 question만 전달.
- String.format("rediret:/questions/%d", questionId) 로 표현 가능. 

## 리팩토링 
- 반복되는 로그인 유저 확인 작업을 리팩토링
- 접근 권한이 없으면 로그인 페이지로 이동하는데 이 때, 에러메세지 출력 유도. login_failed.html에서 alert form을 복사해서 사용.

## 질문 삭제하기
- 질문과 답변 삭제 시 DB에서 삭제하지 않고 deleted : boolean 상태로 삭제 표현. 
- 답변이 없는 경우 삭제 가능 : DB에서 찾은 Question의 answers가 isEmpty()인 경우 삭제. 
- 질문자와 답변글의 모든 답변자(writer)가 같은 경우 삭제 가능.
- 답변 삭제 한 후 deleted = true로 변경. show에는 deleted가 false인 answers만 전달. 
