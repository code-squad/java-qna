### Heroku
> https://wooody92.herokuapp.com/

-----
# STEP1. 회원 가입, 사용자 조회, 질문하기 구현

## 실습 진행 방법

- [질문답변 게시판에 대한 github 저장소](https://github.com/code-squad/java-qna)를 기반으로 실습을 진행한다.
- 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
- 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
- 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

------

## 영상과의 차이점

- 배포는 heroku를 사용한다.
- 템플릿 엔진은 handlebars를 사용한다.
- 로깅 라이브러리는 log4j2를 사용한다.

------

## 회원가입, 사용자 목록 기능 구현 동영상

- [MVC 간단 설명 및 mustache 초간단 설명](https://youtu.be/v3xoltxPnOI)
- [회원가입 기능 구현](https://youtu.be/UQ2wPndQ4-0)
- [회원 목록 기능 구현](https://youtu.be/UJMFPj0JRK0)
- [회원가입, 사용자 목록 기능을 원격 서버에 배포하기](https://youtu.be/9z25blnH67M)
- 두 번째 반복 주기에서 구현한 기능(회원가입, 사용자 목록)을 원격 서버에 배포하는 과정을 다룬다.
- [이전 상태로 원복 후 반복 연습](https://youtu.be/SRehiX49wuA)
- 각 반복주기는 한 번의 연습으로 끝나는 것이 아니라 여러 번 반복해야 의미가 있다. 두 번째 반복주기를 연습 가능한 상태로 원복하는 방법을 다룬다.

------

## 힌트를 활용한 회원가입, 사용자 목록 기능 구현

### 각 기능에 따른 url과 메소드 convention

- 먼저 각 기능에 대한 대표 url을 결정한다.
- 예를 들어 회원관리와 관련한 기능의 대표 URL은 "/users"와 같이 설계한다.
- 기능의 세부 기능에 대해 일반적으로 구현하는 기능(목록, 상세보기, 수정, 삭제)에 대해서는 URL convention을 결정한 후 사용할 것을 추천한다.
- 예를 들어 todo 앱에 대한 URL convention을 다음과 같은 기준으로 잡을 수 있다.

------

| url                     | 기능                        |
| ----------------------- | --------------------------- |
| GET /todos              | List all todos              |
| POST /todos             | Create a new todo           |
| GET /todos/:id          | Get a todo                  |
| PUT /todos/:id          | Update a todo               |
| DELETE /todos/:id       | Delete a todo and its items |
| GET /todos/:id/items    | Get a todo item             |
| PUT /todos/:id/items    | Update a todo item          |
| DELETE /todos/:id/items | Delete a todo item          |

------

## template engine 추가 설정

### template engine이란

- HTML 문법 만으로는 if/for/while과 같은 프로그래밍이 가능하지 않다. 즉, HTML만 활용하는 경우 항상 같은 데이터(정적인)만 서비스가 가능하다.
- 사용자에 따라 다른 HTML, 데이터에 따라 다른 HTML을 제공하려면 if/for/while과 같은 프로그래밍이 가능해야 한다. 이와 같이 서로 다른 HTML(동적인)을 제공하기 위한 도구가 template engine이다.
- template engine의 역할은 JSP, ASP, PHP가 하는 역할과 같다고 생각하면 된다.

------

### handlebars.java인 경우

```java
handlebars.suffix=.html
handlebars.cache=false
코드복사
```

- https://github.com/allegro/handlebars-spring-boot-starter
- https://github.com/jknack/handlebars.java
- https://jknack.github.io/handlebars.java/

------

### mustache인 경우

- 정적인(static) HTML은 static 폴더에서 관리하고 동적인(dynamic) HTML은 templates 디렉토리에서 관리한다.
- html 소스 코드를 수정할 경우 매번 서버를 재시작해야 한다. 이 같은 단점을 보완하기 위해 application.properties 파일에 다음과 같이 설정한다.

```java
spring.mustache.cache=false
코드복사
```

------

### mustache 기본 문법

- https://mustache.github.io/mustache.5.html

------

## 회원가입 기능 구현

### 힌트

- 회원가입 기능의 구현 흐름은 다음과 같다.

![user_form.PNG](https://firebasestorage.googleapis.com/v0/b/nextstep-real.appspot.com/o/lesson-attachments%2F-KpD87bCLSmNUE0rO_tp%2Fuser_form.PNG?alt=media&token=2cc95a2f-0f79-4a32-b1d1-25d1928e9669)

------

- 회원하기 페이지는 static/user/form.html을 사용한다.
- static에 있는 html을 templates로 이동한다.
- 사용자 관리 기능 구현을 담당할 UserController를 추가하고 애노테이션 매핑한다.
- @Controller 애노테이션 추가
- 회원가입하기 요청(POST 요청)을 처리할 메소드를 추가하고 매핑한다.
- @PostMapping 추가하고 URL 매핑한다.
- 사용자가 전달한 값을 User 클래스를 생성해 저장한다.
- 회원가입할 때 전달한 값을 저장할 수 있는 필드를 생성한 후 setter와 getter 메소드를 생성한다.
- 사용자 목록을 관리하는 ArrayList를 생성한 후 앞에서 생성한 User 인스턴스를 ArrayList에 저장한다.
- 사용자 추가를 완료한 후 사용자 목록 페이지("redirect:/users")로 이동한다.

------

## 회원목록 기능 구현

- 회원목록 기능의 구현 흐름은 다음과 같다.

![user_list.PNG](https://firebasestorage.googleapis.com/v0/b/nextstep-real.appspot.com/o/lesson-attachments%2F-KpD87bCLSmNUE0rO_tp%2Fuser_list.PNG?alt=media&token=8d56e9a5-1dca-4c39-9a4e-634e606ceb58)

------

- 회원목록 페이지는 static/user/list.html을 사용한다.
- static에 있는 html을 templates로 이동한다.
- Controller 클래스는 회원가입하기 과정에서 추가한 UserController를 그대로 사용한다.
- 회원목록 요청(GET 요청)을 처리할 메소드를 추가하고 매핑한다.
- @GetMapping을 추가하고 URL 매핑한다.
- Model을 메소드의 인자로 받은 후 Model에 사용자 목록을 users라는 이름으로 전달한다.
- 사용자 목록을 user/list.html로 전달하기 위해 메소드 반환 값을 "user/list"로 한다.
- user/list.html 에서 사용자 목록을 출력한다.
- user/list.html 에서 사용자 목록 전체를 조회하는 방법은 다음과 같다.

```html
{{#users}}
 // 데이터 조회
{{/users}}
코드복사
```

------

## 회원 프로필 정보보기

- 회원 프로필 정보 보기의 구현 흐름은 다음과 같다.

![user_profile.PNG](https://firebasestorage.googleapis.com/v0/b/nextstep-real.appspot.com/o/lesson-attachments%2F-KpD87bCLSmNUE0rO_tp%2Fuser_profile.PNG?alt=media&token=2bf4e3e7-95b0-4a0b-9113-61294f05651b)

------

- 회원 프로필 보기 페이지는 static/user/profile.html을 사용한다.
- static에 있는 html을 templates로 이동한다.
- 앞 단계의 사용자 목록 html인 user/list.html 파일에 닉네임을 클릭하면 프로필 페이지로 이동하도록 한다.
- html에서 페이지 이동은 `` 태그를 이용해 가능하다.
- ``와 같이 구현한다.
- Controller 클래스는 앞 단계에서 사용한 UserController를 그대로 사용한다.
- 회원프로필 요청(GET 요청)을 처리할 메소드를 추가하고 매핑한다.

------

- @GetMapping을 추가하고 URL 매핑한다.
- URL은 "/users/{userId}"와 같이 매핑한다.
- URL을 통해 전달한 사용자 아이디 값은 @PathVariable 애노테이션을 활용해 전달 받을 수 있다.
- ArrayList에 저장되어 있는 사용자 중 사용자 아이디와 일치하는 User 데이터를 Model에 저장한다.
- user/profile.html 에서는 Controller에서 전달한 User 데이터를 활용해 사용자 정보를 출력한다.

------

## HTML의 중복 제거

> index.html, /user/form.html, /qna/form.html 코드를 보면 header, navigation bar, footer 부분에 많은 중복 코드가 있다. 중복 코드를 제거한다.

------

### html 중복 제거 힌트

- 중복 코드가 발생하는 부분을 별도의 template 파일로 분리한다.
- 예를 들어 중복 코드가 있는 부분을 user.html로 다음과 같이 분리한다.

```html
{{#partial "content" }}
<p>Home page</p>
{{/partial}}

{{> base}}
코드복사
```

------

- 위와 같이 분리한 template 코드를 다음과 같이 base.html과 같은 파일에서 추가할 수 있다.

```
{{#block "header"}}
<h1>Title</h1>
{{/block}}

{{#block "content"}}
{{/block}}

{{#block "footer" }}
<span>Powered by Handlebars.java</span>
{{/block}}
코드복사
```

------

- 두 개로 분리한 위 html의 실행 결과는 다음과 같다.

```html
<h1>Title</h1>
<p>Home page</p>
<span>Powered by Handlebars.java</span>
코드복사
```

- [handlebars.java reuse](http://jknack.github.io/handlebars.java/reuse.html) 문서를 참고해 개선한다.

------

### URL과 html 쉽게 연결하기

앞 단계와 같이 html에서 중복을 제거하는 기능은 handlebars.java template engine에서 제공하는 기능이다.

따라서 모든 html의 중복을 제거하려면 static의 html 또한 templates 폴더로 이동하고 URL 매핑을 해야 한다.

그런데 이와 같이 구현할 경우 특별한 로직이 없음에도 불구하고 매번 메소드를 만들고 매핑하는 일이 귀찮은 작업이다. 이 같은 단점을 다음과 같이 보완할 수 있다.

- base package 아래에 config와 같은 새로운 패키지 생성한다.
- MvcConfig 이름으로 클래스를 생성해 다음과 같은 형태로 구현한다.

------

```java
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
	@Override
 public void addViewControllers(ViewControllerRegistry registry) {
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
		
  registry.addViewController("/users/form").setViewName("user/form");
  registry.addViewController("/users/login").setViewName("user/login");
  registry.addViewController("/questions/form").setViewName("qna/form");
 }
}
코드복사
```

------

## 실습 - 질문하기, 질문 목록 기능 구현

## 질문하기 요구사항

> 사용자는 질문을 할 수 있어야 한다.

------

### 힌트 1 - 기본 흐름

- 질문하기 구현에 대한 기본 흐름은 다음과 같다.

![question_form.PNG](https://firebasestorage.googleapis.com/v0/b/nextstep-real.appspot.com/o/lesson-attachments%2F-KpD87bCLSmNUE0rO_tp%2Fquestion_form.PNG?alt=media&token=fa9a0fdb-2b56-421d-b758-8bf77dd3900d)

------

### 힌트 2 - 구현 단계별 힌트

- 질문하기 페이지는 static/qna/form.html을 사용한다.
- static에 있는 html을 templates로 이동한다.
- 질문 기능 구현을 담당할 QuestionController를 추가하고 애노테이션 매핑한다.
- 질문하기 요청(POST 요청)을 처리할 메소드를 추가하고 매핑한다.
- 사용자가 전달한 값을 Question 클래스를 생성해 저장한다.
- 질문 목록을 관리하는 ArrayList를 생성한 후 앞에서 생성한 Question 인스턴스를 ArrayList에 저장한다.
- 질문 추가를 완료한 후 메인 페이지(“redirect:/”)로 이동한다.

------

## 질문 목록 요구사항

> 모든 사용자는 질문 목록을 볼 수 있어야 한다.

------

### 힌트

- 메인 페이지(요청 URL이 “/”)를 담당하는 Controller의 method에서 질문 목록을 조회한다.
- 조회한 질문 목록을 Model에 저장한 후 View에 전달한다. 질문 목록은 앞의 질문하기 단계에서 생성한 ArrayList를 그대로 전달한다.
- View에서 Model을 통해 전달한 질문 목록을 출력한다.
- 질문 목록을 구현하는 과정은 사용자 목록을 구현하는 html 코드를 참고한다.

------

## 질문 상세보기

> 모든 사용자는 질문 상세 내용을 볼 수 있어야 한다.

------

### 힌트

- 질문 목록(qna/list.html)의 제목을 클릭했을 때 질문 상세 페이지에 접속할 수 있도록 한다.
- 질문 상세 페이지 접근 URL은 "/questions/{index}"(예를 들어 첫번째 글은 /questions/1)와 같이 구현한다.
- 질문 객체에 id 인스턴스 변수를 추가하고 ArrayList에 질문 객체를 추가할 때 `ArrayList.size() + 1`을 질문 객체의 id로 사용한다.
- Controller에 상세 페이지 접근 method를 추가하고 URL은 /questions/{index}로 매핑한다.
- ArrayList에서 `index - 1` 해당하는 데이터를 조회한 후 Model에 저장해 /qna/show.html에 전달한다.
- /qna/show.html에서는 Controller에서 전달한 데이터를 활용해 html을 생성한다.

------

## 회원정보 수정(선택)

## 요구사항

> 회원 목록에서 회원가입한 사용자의 정보를 수정할 수 있어야 한다.
> 비밀번호, 이름, 이메일만 수정할 수 있으며, 사용자 아이디는 수정할 수 없다.
> 비밀번호가 일치하는 경우에만 수정 가능하다.

------

### 힌트 1 - 회원정보 수정 화면 기능 구현

- 회원가입한 사용자 정보를 수정할 수 있는 수정 화면과 사용자가 수정한 값을 업데이트할 수 있는 기능을 나누어 개발해야 한다.
- 사용자 정보를 수정하는 화면 구현 과정은 다음과 같다.
  ![user_update_form.png](https://firebasestorage.googleapis.com/v0/b/nextstep-real.appspot.com/o/lesson-attachments%2F-KpD87bCLSmNUE0rO_tp%2Fuser_update_form.png?alt=media&token=4b14ef86-adea-47dd-9266-1273f641220a)

------

- /user/form.html 파일을 /user/updateForm.html로 복사한 후 수정화면을 생성한다.
- URL 매핑을 할 때 "/users/{id}/form"와 같이 URL을 통해 인자를 전달하는 경우 @PathVariable 애노테이션을 활용해 인자 값을 얻을 수 있다.
- public String updateForm(@PathVariable String id)와 같이 구현 가능하다.
- Controller에서 전달한 값을 입력 폼에서 출력하려면 value를 사용하면 된다.
- ``

------

### 힌트 2 - 회원정보 수정

- 사용자 정보를 최종적으로 수정하는 과정은 다음과 같다.
  ![user_update.png](https://firebasestorage.googleapis.com/v0/b/nextstep-real.appspot.com/o/lesson-attachments%2F-KpD87bCLSmNUE0rO_tp%2Fuser_update.png?alt=media&token=c5ea1fa7-a85e-4a90-b22f-c68b9efcfa06)

학습 완료