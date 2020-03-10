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

## step4. 로그인 구현

### # step3 Refactoring

- CommonUtils 변경
    - 로직의 명시성 강화를 위해 각 get 메소드에 OrError() 를 추가 ... complete
    - 각 소스에 static import 를 추가 ... complete

### # 기능 구현

- 회원과 질문간의 관계 매핑 및 생성일 추가
    - User와 Question 간의 관계를 매핑한다.   
        - 기존 userId 를 사용하던 방법에서 User 객체로 변경 ... complete
        - hidden input 추가로 userId, id 분리 ... complete
    - Question에 생성일을 추가한다. ... complete

-  질문 상세보기 기능
    - 질문 상세보기 기능 구현 ... complete

-  질문 수정, 삭제 기능 구현
    - PUT(수정), DELETE(삭제) HTTP method를 활용해 수정, 삭제 기능을 구현 ... complete

-  수정/삭제 기능에 대한 보안 처리 및 LocalDateTime 설정
    - Back End 프로그래밍에서 정말 중요한 보안 관련된 기능 구현 ... compelte
    - JPA에서 LocalDateTime을 DB 데이터타입과 제대로 매핑하지 못하는 이슈 해결 ... compelte

- 답변 추가 및 목록 기능 구현
    - 답변 기능을 담당할 Answer를 추가하고, Question, User와 매핑 ... compelete
    - Question에 Answer를 @OneToMany로 매핑. 이와 같이 매핑함으로써 질문 상세보기 화면에서 답변 목록이 동작하는 과정 공유 ... compelete

- QuestionController 중복 코드 제거 리팩토링
    - QuestionController에서 보안 처리를 위해 구현한 중복 코드를 제거 ... compelete
    - 중복을 Exception을 활용한 제거와 Result와 같은 새로운 클래스르 추가해 제거 ... compelete

- 질문 삭제하기 실습(선택)
    - 질문 데이터를 완전히 삭제하는 것이 아니라 데이터의 상태를 삭제 상태(deleted - boolean type)로 변경한다. ... complete
    - 질문을 삭제할 때 답변 또한 삭제해야 하며, 답변의 삭제 또한 삭제 상태(deleted)를 변경한다. ... complete
    - 로그인 사용자와 질문한 사람이 같은 경우 삭제 가능하다. ... complete
    - 답변이 없는 경우 삭제가 가능하다. ... complete
    - 질문자와 답변 글의 모든 답변자 같은 경우 삭제가 가능하다. ... complete
    - 질문자와 답변자가 다른 경우 답변을 삭제할 수 없다. ... complete
