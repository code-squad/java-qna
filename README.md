# 질문답변 게시판
## 진행 방법
* 질문답변 게시판에 대한 html template은 src/main/resources 디렉토리의 static에서 확인할 수 있다. html template을 통해 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 코드스쿼드의 온라인 코드 리뷰 과정](https://github.com/code-squad/codesquad-docs/blob/master/codereview/README.md)
* [동영상으로 살펴보는 코드스쿼드의 온라인 코드 리뷰 과정](https://youtu.be/a5c9ku-_fok)


## Step1
* UserController : 사용자 관리 기능 구현을 담당
    * createUser() : static/user/form.html에서 사용자가 가입 요청(post 요청)을 처리
    * showUserList() : 가입 유저를 출력
    * showUserProfile() : userId가 맞는지 확인 후 가입 유저의 프로필을 보여줌
    * modifyUserProfile() : 프로필 수정부분
    * updateUserProfile() : 프로필 수정한것을 업데이트 하는 부분

* User : 사용자 데이터 저장 클래스
    * userId, password, name, email

* QuestionController : 질문 관리 기능 구현을 담당
    * createQuestion : 질문 작성 요청(post 요청)을 처리
    * showQuestionList : 질문 리스트 보여줌
    * showQuestionDetail : 질문 상세 보기

* Question : 질문 데이터 저장 클래스
    * writer, title, contents, time, id

## Step2
* H2 Database 연결
    * application.properties, build.gradle 수정으로 손쉽게 연결 가능
* UserController : 사용자 관리 기능 구현을 담당
    * createUser() : static/user/form.html에서 사용자가 가입 요청(post 요청)을 처리
    * showUserList() : 가입 유저를 출력
    * showUserProfile() : userId가 맞는지 확인 후 가입 유저의 프로필을 보여줌
    * modifyUserProfile() : 프로필 수정부분
    * updateUserProfile() : 프로필 수정한것을 업데이트 하는 부분
    * goForm() : 중복제거를 위해 static에 있던 form.html을 template에 옮겨서 연결
    * goLoginForm() : 중복제거를 위해 static에 있던 login.html을 template에 옮겨서 연결

* User : 사용자 데이터 저장 클래스
    * id, userId, password, name, email

* UserRepository : User 부분 데이터베이스 CRUD 사용을 위한 인터페이스

* QuestionController : 질문 관리 기능 구현을 담당
    * createQuestion : 질문 작성 요청(post 요청)을 처리
    * showQuestionList : 질문 리스트 보여줌
    * showQuestionDetail : 질문 상세 보기
    * goForm() : 중복제거를 위해 static에 있던 form.html을 template에 옮겨서 연결

* Question : 질문 데이터 저장 클래스
    * id, writer, title, contents, time

* QuestionRepository : Question 부분 데이터베이스 CRUD 사용을 위한 인터페이스

## Step3
* 로그인, 로그아웃 기능 구현 및 로그인 상태에 따른 메뉴 처리
* 자기 자신의 정보만 수정 가능(로그인 하지 않으면 로그인 페이지로 이동)
* HttpSession을 활용 하여 로그인 기능 구현

* 질문 하기(로그인 한 유저만 질문 가능)
* 질문 목록 보기(아무나 다 볼 수 있음)
* 자기 자신의 질문만 수정 가능(로그인 하지 않으면 로그인 페이지로 이동)
* 자기 자신의 질문만 삭제 가능(로그인 하지 않으면 로그인 페이지로 이동)

* HttpSessionUtils 클래스를 만들어 중복을 줄이긴 했으나
매 요청마다 로그인 여부를 검사하는 부분이 중복되는데 어떤식으로 중복제거를 해야 할 지 모르겠다.

* step4를 진행 하면서 중복 코드를 줄여보자

## Step4
* 요구사항
* 회원과 질문간의 관계 매핑 및 생성일 추가
* 질문 상세보기 기능
* 질문 수정, 삭제 기능 구현
* 수정/삭제 기능에 대한 보안 처리 및 LocalDateTime 설정
* 답변 추가 및 목록 기능 구현
* QuestionController 중복 코드 제거 리팩토링

## 질문 삭제하기 실습(선택)
* 요구사항
* 질문 삭제 기능을 구현한다. 질문 삭제 기능의 요구사항은 다음과 같다.
1. 질문 데이터를 완전히 삭제하는 것이 아니라 데이터의 상태를 삭제 상태(deleted - boolean type)로 변경한다.
2. 로그인 사용자와 질문한 사람이 같은 경우 삭제 가능하다.
3. 답변이 없는 경우 삭제가 가능하다.
4. 질문자와 답변 글의 모든 답변자 같은 경우 삭제가 가능하다.
5. 질문을 삭제할 때 답변 또한 삭제해야 하며, 답변의 삭제 또한 삭제 상태(deleted)를 변경한다.
6. 질문자와 답변자가 다른 경우 답변을 삭제할 수 없다.

1번 요구사항에서 데이터베이스의 데이터를 삭제하는게 아니라 deleted의 상태를 보고 
false 상태이면 view에 보여주고 true면 view에 안보여주고 이런 식으로 구현 했는데 
요구사항을 잘 이해 한건지.. 모르겠다.

* 질문 삭제 요청시 Question.java에 deleteQuestion() 함수를 수행
``` 
    public void deleteQuestion() {
        if (!canDeleteAnswer()) {
            throw new IllegalStateException("삭제 할 수 없습니다.");
        }
        this.deleted = true;
        this.answers.forEach(Answer::delete);
    }

    public boolean canDeleteAnswer() {
        if (answers.isEmpty()) {
            return true;
        }

        return answers.stream()
                .filter(answer -> !answer.getDeleted())
                .allMatch(answer -> answer.getWriter().equals(writer));
    }
```
* deleteQuestion() : 답변 삭제 가능한지 체크 후(canDeleteAnswer) 삭제 가능하면 question의 deleted를 true로 바꾸고 question이 가지고 있는 answers들을 각각 삭제한다.  

* canDeleteAnswer() : answer가 비어있으면 질문 삭제 가능하니 true반환 아니면
answers들의 deleted가 false인 것들을 찾아 질문 작성자와 답변 작성자가 같은지를 체크해서
한명이라도 다르면 질문을 삭제 할 수 없으므로 false를 날린다.