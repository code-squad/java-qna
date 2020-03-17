# 질문답변 게시판
## 진행 방법
* 질문답변 게시판에 대한 html template은 src/main/resources 디렉토리의 static에서 확인할 수 있다. html template을 통해 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 코드스쿼드의 온라인 코드 리뷰 과정](https://github.com/code-squad/codesquad-docs/blob/master/codereview/README.md)
* [동영상으로 살펴보는 코드스쿼드의 온라인 코드 리뷰 과정](https://youtu.be/a5c9ku-_fok)


## Step1 구현 사항

1. 회원 가입 기능 구현
    - 회원 가입을 누르면, /users/form URL 전송하고, GetMapping을 통하여 해당 URL을 전달 받음
    - 유저 생성 완료 후, 회원 가입 버튼을 누르면 /users/create URL 전송하고, PostMapping을 통해서 users 리스트에 값을 전달하고, 
    그 값을  /users로 redirect
2. 회원 목록 기능 구현
    - users URL에서는 users 리스트에 있는 user를 출력
    - 해당 유저 아이디를 클릭했을 시 프로필 페이지로 이동하도록 URL 매핑
3. 회원 프로필 기능 구현
    - 회원 리스트에서 각 유저의 userId를 URL로 입력받아서, users 리스트에서 해당 user의 값을 profile.html로 전달하여
      유저의 상세 정보를 출력하도록 구현
4. 회원 정보 수정 기능 구현
    - 회원정보 수정을 누르면, 우선 회원정보 수정을 위한 Login 페이지로 이동하여 회원 정보와 비밀번호가 매칭될 경우 회원 아이디를 제외하고
      비밀번호, 이름, 이메일 정보를 수정 가능한 페이지로 이동하게 함
    - 비밀번호, 이름, 이메일 정보를 받아 올 때, 아무 값도 입력되지 않는 경우 해당 속성은 변경하지 않도록 구현
    - 회원 정보가 수정되면, 회원 리스트에서 변경된 회원 정보를 확인할 수 있도록 구
5. 질문 작성 기능 구현현
    - 질문하기를 누르면, 질문 페이지로 이동할 수 있도록 구현
    - 질문을 작성하면 title, writer, content값을 questions 리스트에 전달하도록 구현
    - 질문의 작성 시간은 question 클래스가 생성 될 때, 생성자에서 시간 변수 writtenTime을 생성
6. 질문 리스트 index 페이지에 구현
    - questions 리스트에 존재하는 질문을 index 페이지에 작성된 시간과 함께 출력하도록 구현
    - 인덱스 값은 questions 리스트의 size + 1을 갖도록 구현
7. 질문 상세 보기 기능 구현
    - questionIndex를 기준으로 questions 리스트에서 해당 인덱스의 question 클래스를 model에 저장한 후, show.html로 전달하여
      질문의 상세 정보를 출력하도록 구
8. HTML 중복 제거현
    - {{mustache}} handlebars를 활용하여 중복되는 header부분, navigationBar부분을 중복 제거 하였습니다.

### step1 질문 사항

- RequestMapping에서 Method를 통해서 GetMapping, PostMapping을 모두 컨트롤 가능한데, 모든 Mapping 기능을 RequestMapping으로 변경하여도 좋은것인가요?

- 정적인 페이지를 동적인 페이지로 변경하는 과정에서 경로 설정을 하는데 처음 하다보니 혼란이 생겼습니다.
    - 제가 생각할 때는, templates에서 static 디렉토리 내부의 css나 다른 경로에 있는 html파일에 접근할 경우 ../static/css/... 이런식으로 접근하거나 같은 폴더 내에 존재할 경우 
    /form과 같이 바로 접근 할 수 있다고 생각했는데 에러가 발생했습니다. 어떤 별도의 규칙이 존재하는지 궁금합니다.

- 회원 정보를 수정할 때, 사용되는 Login을 기존의 Login.html을 그대로 복사하여 별도의 changeUserInfoLogin.html을 만들었습니다.
    - 두가지로 나눈 이유는 1. 그냥 로그인 2. 회원정보 수정시 로그인 두 경우 로그인 했을 때, 나오는 페이지가 다를 수 있다고 생각했기 때문인데,
      굳이 별도의 페이지를 만들지 않고, 기존의 Login.html에서 각 상황별로 다른 URL을 돌려줄 수 있는지 궁금합니다. 
------------------------------------------------------------

## Step2 구현

------------------------------------------------------------

## Step3 구현

------------------------------------------------------------

## Step4 구현

1. 회원, 질문, 답변 사이의 관계를 매핑하였습니다.
	- ManyToOne을 활용하여 각 클래스별 관계를 매핑하여 참조하도록 구현하였습니다.

2. 질문에 답변 기능 추가
	- 질문 상세보기 페이지에서 답변을 달 수 있는 기능을 추가하였습니다.
	- 작성자가 답변을 삭제할 수 있도록 하였습니다.

3. 질문을 삭제할 때, 삭제가 아닌, 출력하지 않는것으로 변경
	- 질문을 삭제할 때, 기존에는 해당 질문을 저장소에서 삭제하도록 구현하였습니다.
	- Question Class에 boolean type의 변수를 추가하여 HTML단에서 출력 여부를 판단하도록 하였습니다.
	- 답변이 달려있는 경우에는 각 답변의 작성자를 비교하고, 모두 일치하거나 답변이 없는 경우에만 질문을 삭제할 수 있도록 구현하였습니다.

### 질문 사항

	- 질문의 출력 여부를 HTML에서 mustache를 활용하여 결정하도록 하였는데, 메소드단에서 별도의 처리를 하는것이 옳은 방법인가요??
		- mustache, handlebar를 다양하게 사용하는것이 좋은것인가요?
	
	- GET,POST,PUT,DELETE 다양한 매핑을 활용할 때, 하나의 URL에 모든 메소드를 통일하여 사용하는것이 좋은가요?
		- @POSTMAPPING("/{id}"), @PUTMAPPING("/{id}"), @DELETEMAPPING("/{id}") --> 지금 사용하고 있는 방식
		- @POSTMAPPING("/{id}/postworking"), @PUTMAPPING("/{id}/putworking"), @DELETEMAPPING("/{id}/deleteworking") --> 이런 방식을 사용하는것이 좋은가요?
		
	- 다른 사용자의 정보를 수정하는 경우 403에러를 출력하도록 구현하였는데, 단순하게 404로 변경하여 사용하여도 문제가 없을까요? 404가 403을 대신하여 사용 가능할 것 같다는 생각이 들었습니다.

------------------------------------------------------------
