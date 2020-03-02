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

## step3. 로그인 구현

### # 기본 세팅
### # step2 Refactoring
- 2개의 path 를 한개의 메소드로 mapping 하는 방법 ... working?
- error 처리/페이지에 대한 설정. ... complete
    - CustomErrorController 추가
    - 403 처리 Exception 추가 (ForiddenException)
    - errorPage.html 추가
    - 400, 403, 404, 500 에러에 대한 이미지 추가
- DateTimeFormatter 한번만 선언 ... complete
- Controller returnView 의 변수 선언 지양 ... complete
- Optional 사용시 get() 사용 지양 (강제 Unwrapping) ... complete
- lombok 을 통한 logging 간소화 ... complete

### # Handlebars
https://github.com/Hyune-c/TIL/blob/master/Spring/Handlebars.md

### # log 가독성 높이기
- log4j2.properties
```
appender.console.layout.pattern=%style{%d{yyyy-MM-dd hh:mm:ss:SSS}} %highlight{%-5level }[%style{%t}{bright,blue}] %style{%C{1.}}{bright,yellow}: %msg%n%throwable"
```

- Grep Console 
    - 커스텀 하이라이트 가능

### # 기능 구현
- Handlebars 전환 ... compelte
- 초기 데이터 만들기 
    - .../resource/data.sql 생성 ... complete
- 로그인 기능 구현
    - 로그인 상태이면 상단 메뉴에 “로그아웃”, “개인정보수정” 이 나타나야 합니다. ... complete
    - 로그아웃 상태이면 상단 메뉴에 “로그인”, “회원가입” 이 나타나야 한다. ... complete
- 개인정보 수정
    - 이름, 이메일은 수정할 수 있으며, 아이디는 수정할 수 없습니다. ... complete
    - 글 수정은 비밀번호가 일치하는 경우에만 가능합니다. ... complete
- 질문 기능 구현 실습
    - 질문은 모두가 볼 수 있습니다. ... complete
    - 질문 작성은 로그인한 사용자만 가능합니다. ... complete
    - 글 수정/삭제는 자신의 것만 가능합니다. ... compelte
- User와 Question 연결 실습(선택)
    - 현재 Question의 글쓴이는 User의 name 값을 가지는 것으로 구현했다.  
    이와 같이 구현하는 경우 User의 name을 수정하는 경우 Question의 글쓴이와 다른 값을 가지는 문제가 발생한다.  
    이 문제를 해결하기 위해 User의 name이 변경될 때마다 Question의 writer 값을 수정할 수도 있지만 이와 같이 구현할 경우 writer가 같은 이름을 가지는 경우 문제가 될 수 있다.  
    이 같은 문제 상황에 대해 원론적으로 문제가 발생하지 않도록 해결한다. 
    - writer 대신 변경할 수 없는 userId 를 사용함으로서 해결하였습니다. ... complete
- Answer 구현하기 실습(선택) ... working
    - 답변 목록은 모두가 볼 수 있습니다.
    - 답변 작성은 로그인한 사용자만 가능합니다.
    - 답변 삭제는 자신의 것만 가능합니다.    
