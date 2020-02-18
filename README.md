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
## step1. 회원 가입 및 사용자 목록 기능 구현

### # 기본 세팅 
- fork 된 git 주소를 clone 으로 가져옵니다. 
- step1 branch 를 만듭니다.
- remote repository 추가하기

### # 기능 구현
#### 회원가입 기능 구현
- 회원하기 페이지는 static/user/form.html을 사용한다. ... complete
- static에 있는 html을 templates로 이동한다. ... complete
  - /user/form.html 파일을 이동해줍니다.
  - UserController 에 form 매핑을 추가해줍니다. 
- 사용자 관리 기능 구현을 담당할 UserController를 추가하고 애노테이션 매핑한다. ... complete
- @Controller 애노테이션 추가 ... complete
- 회원가입하기 요청(POST 요청)을 처리할 메소드를 추가하고 매핑한다. ... complete
  - /static/user/form.html 의 question form method 를 get 에서 post 로 바꿔줍니다.
- @PostMapping 추가하고 URL 매핑한다. ... complete
  - UserController 에 /user/create 매핑을 추가해줍니다. 
- 사용자가 전달한 값을 User 클래스를 생성해 저장한다. ... complete
- 회원가입할 때 전달한 값을 저장할 수 있는 필드를 생성한 후 setter와 getter 메소드를 생성한다. ... complete
- 사용자 목록을 관리하는 ArrayList를 생성한 후 앞에서 생성한 User 인스턴스를 ArrayList에 저장한다. ... complete
    - userList 를 만들어 관리합니다.
- 사용자 추가를 완료한 후 사용자 목록 페이지("redirect:/users")로 이동한다. ... complete

#### 회원목록 기능 구현
- 회원목록 페이지는 static/user/list.html을 사용한다. ... complete
- static에 있는 html을 templates로 이동한다. ... complete
- Controller 클래스는 회원가입하기 과정에서 추가한 UserController를 그대로 사용한다. ... complete
- 회원목록 요청(GET 요청)을 처리할 메소드를 추가하고 매핑한다. ... complete
- @GetMapping을 추가하고 URL 매핑한다. ... complete
  - UserController 에 /list 매핑을 추가해줍니다.
- Model을 메소드의 인자로 받은 후 Model에 사용자 목록을 users라는 이름으로 전달한다. ... complete
- 사용자 목록을 user/list.html로 전달하기 위해 메소드 반환 값을 "user/list"로 한다. ... complete
- user/list.html 에서 사용자 목록을 출력한다. ... complete
- user/list.html 에서 사용자 목록 전체를 조회하는 방법은 다음과 같다. ... complete

#### 회원 프로필 정보보기
- 회원 프로필 정보 보기의 구현 흐름은 다음과 같다. ... complete
- 회원 프로필 보기 페이지는 static/user/profile.html을 사용한다. ... complete
- static에 있는 html을 templates로 이동한다. ... complete
- 앞 단계의 사용자 목록 html인 user/list.html 파일에 닉네임을 클릭하면 프로필 페이지로 이동하도록 한다. ... complete
- html에서 페이지 이동은 <a /> 태그를 이용해 가능하다. ... complete
- `<a href="/users/{{userId}}" />` 와 같이 구현한다. ... complete
- Controller 클래스는 앞 단계에서 사용한 UserController를 그대로 사용한다. ... complete
- 회원프로필 요청(GET 요청)을 처리할 메소드를 추가하고 매핑한다. ... complete
- @GetMapping을 추가하고 URL 매핑한다. ... complete
- URL은 "/users/{userId}"와 같이 매핑한다. ... complete
- URL을 통해 전달한 사용자 아이디 값은 @PathVariable 애노테이션을 활용해 전달 받을 수 있다. ... complete
- ArrayList에 저장되어 있는 사용자 중 사용자 아이디와 일치하는 User 데이터를 Model에 저장한다. ... complete
- user/profile.html 에서는 Controller에서 전달한 User 데이터를 활용해 사용자 정보를 출력한다. ... complete

#### HTML의 중복 제거
- index.html, /user/form.html, /qna/form.html 코드를 보면 header, navigation bar, footer 부분에 많은 중복 코드가 있다. 중복 코드를 제거한다. ... complete
- URL과 html 쉽게 연결하기
    - base package 아래에 config와 같은 새로운 패키지 생성한다.
    - MvcConfig 이름으로 클래스를 생성해 다음과 같은 형태로 구현한다.