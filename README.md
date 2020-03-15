# 스프링 부트로 QA 게시판 구현1

## 배포

https://jay-simple-qna.herokuapp.com/

## Step5

## 공부 내용

### jQuery

+ 선택자(Selector)

  + 선택자(Selector)로 원하는 DOM 객체를 가져온다.
  + CSS 선택자와 유사

+ ```serialize()```

  + 데이터를 보내기 위해(submit) 폼 태그의 요소를 key-value 형식의 문자열로 인코딩하는 메소드
  + 폼 태그의 요소는 반드시 ```name``` 속성을 가지고 있어야 직렬화에 포함된다.

+ ```ajax(url[, settings])```

  + *Perform an asynchronous HTTP (Ajax) request.*

  + 서버로 데이터를 전송하는 메소드

    + 해당 메소드는 jQuery 객체의 메소드

    + ```XMLHttpRequest``` 객체를 리턴한다. (브라우저의 기본 ```XMLHttpRequest``` 객체의 상위 객체?)

      >  ```ajax()``` 는 ```XMLHttpRequest``` 객체를 생성해서 서버에 데이터를 전송하는 메소드겠지?

  + settings : key-value 형태로 구성된 요청 데이터

    + method : http 메소드
    + url : 요청할 url
    + data : 전송할 데이터
    + dataType : 서버로부터 받을 데이터 타입

  > 참고 및 출처
  >
  > [jQuery-API-Ajax](https://api.jquery.com/jQuery.ajax/)

+ ```$.```

  + ```$()``` : jQuery 함수 호출
  + ```$.``` : jQuery 객체의 프로퍼티 호출
  + 즉, $ == jQuery

  > [참고](https://okky.kr/article/416020?note=1303402)

  ```
  if ( !noGlobal ) {
  	window.jQuery = window.$ = jQuery;
  }
  ```



+ ```on("click")``` 과 ```click()``` 차이점

  + 동적으로 이벤트를 바인딩할 수 있는가

    + ``` on(event)``` 은 동적으로 이벤트를 바인딩한다.

    + ```click()``` 은 최초에 선언된 element에만(정적 DOM) 동작한다.

  + 즉, 페이지가 로드된 이후에 동적으로 추가되는 DOM 객체에 이벤트를 등록하고 싶다면 ```on(event)``` 으로 이벤트 리스너를 바인딩해야 한다.

  + 이벤트 위임

    + 하위 요소에 각각 이벤트를 붙이지 않고 상위 요소에서 하위 요소의 이벤트들을 제어하는 방식

    + 언제 필요한 개념인가?

      동적으로 요소가 추가되는 경우, 새로운 요소마다 이벤트 리스너를 등록해주는 것은 번거롭다.

      추가될 요소의 상위 요소에 이벤트 리스너를 달아주면 *이벤트 버블링* 에 의해 하위 요소에서 발생한 이벤트를 감지할 수 있다.

  > [on-과-click-차이](https://lookingfor.tistory.com/entry/JQuery-%ED%81%B4%EB%A6%AD-%EC%9D%B4%EB%B2%A4%ED%8A%B8-onclick-%EA%B3%BC-click-%EC%9D%98-%EC%B0%A8%EC%9D%B4)
  >
  > [이벤트-버블링-캡처-위임](https://joshua1988.github.io/web-development/javascript/event-propagation-delegation/)



### Ajax

+ Asynchronous JavaScript And XML

+ Ajax는 프로그래밍 언어가 아니다.

+ Ajax는 XMLHttpRequest 와 JavaScript 그리고 HTML의 조합이다.

  > 이름 때문에 xml 파일(만)을 이용하는 것 같지만 일반 텍스트나 JSON 텍스트로 전송하는 것이 일반적이다.

+ 역할

  웹 페이지 뒷단에서 서버와 데이터를 교환하여 비동기적으로 웹 페이지를 업데이트할 수 있도록 해준다.

  즉, 페이지가 로드된 이후에 리로드 없이 부분적으로 페이지를 변경하는 것을 가능하게 한다.

+ 특징

  + GET, POST 외의 메소드를 지원한다.

+ 최근에는 최초 웹 서비스에 접근할 때 서비스에 필요한 모든 웹 자원(html, css, javascript)을 다운로드 받고, 이후 요청은 AJAX를 통해 서버와 데이터(xml 또는 json)만 주고 받는다.



### Ajax 동작 방식

![how-ajax-works](https://www.w3schools.com/xml/ajax.gif)

1. 페이지가 로드된 이후에 이벤트가 발생한다.

   > '버튼이 눌렸다.'

2. 자바스크립트에 의해 ```XMLHttpRequest``` 객체가 생성된다.

3. ```XMLHttpRequest``` 객체가 서버에 요청을 전송한다.

4. 서버는 요청을 처리한다.

5. 서버는 웹 페이지에 응답을 전송한다.

6. 자바스크립트가 응답 내용을 받는다.

7. 자바스크립트가 응답 내용을 이용하여 적절한 행동을 취한다.

   > 페이지 리프레쉬없이 부분적으로 업데이트한다.

   

### XHR

+ XML Http Request
+ AJAX의 핵심적인 요소!
+ 대부분의 웹 브라우저가 제공하는 자바스크립트 API
+ 페이지 뒷단에서 서버와 데이터 교환을 가능하게 하는 것
+ XHR 메소드로 브라우저와 서버간의 네트워크 요청 및 응답을 처리한다.



### 왜 Ajax를 사용하는가?

+ 페이지 리로딩 없이 페이지를 업데이트할 수 있다.

  > 페이지 리로딩은 html 페이지를 서버에 재요청하는 것인가? html을 새롭게 생성하는 행위인가?

+ 페이지가 로드된 이후에 서버에 데이터를 요청할 수 있다.

+ 페이지가 로드된 이후에 서버로부터 데이터를 받을 수 있다.

+ 기존 방식과 비교 (다시 확인해야 함)

  |                    | 기존 방식                                          | AJAX를 활용한 XHR 요청/응답                                  |
  | ------------------ | -------------------------------------------------- | ------------------------------------------------------------ |
  | 응답 처리          | 서버에서 새로운 HTML을 생성해서 응답한다.          | 서버로부터 받아온 데이터(JSON)을 포함하여 현재 HTML 페이지를 업데이트한다. |
  | 서버 응답 데이터   | HTML                                               | XML 또는 JSON                                                |
  | 자원 다운로드 여부 | 새롭게 응답받은 HTML에 필요한 자원을 다운로드한다. | (응답에 의해 새롭게 추가되는 자원이 존재하지 않는 경우) HTML이 그대로 유지되었기 때문에 다운로드를 하지 않는다. |

+ 사용자 경험 측면에서는 리로드(페이지 깜빡임)없이 페이지가 갱신되는 것이 더 좋다.

  > 기존 방식보다 Ajax를 활용하는게 코드 작성 측면에서는 복잡할 수 있다.

  

> 참고 및 출처
>
> [Ajax-XHR-개념](https://www.w3schools.com/xml/ajax_intro.asp)



### Ajax 요청에 대한 응답 결과가 리다이렉트인 경우

+ 기대 동작

  + 답변 삭제 버튼을 눌렀을 때, ajax 방식으로 서버에 요청하고 로그인된 상태가 아니라면 인터셉터에 의해 로그인 페이지로 리다이렉트한다.

+ 문제 상황

  + (step4 코드 기준) ajax 방식으로 요청된 결과가 리다이렉트인 경우, 리다이렉트의 메소드가 GET이 아닌 이전 요청 당시 메소드(DELETE)가 그대로 요청된다.

    > 즉, ```DELETE 리다이렉트URL``` 가 요청된다. (왜???) 
    >
    > 그래서 status도 302가 아니라 405 (method not allowed)가 찍힌다.

    > 뷰를 리턴할 때 ```response.sendRedirect(url);``` 은 GET 메소드로 변경되어서 작동하지만
    >
    > json을 리턴할 때는 리다이렉트 메소드가 GET으로 변경되지 않고 기존 메소드가 그대로 적용됨.

  + Ajax로 처리하는 것 자체가 페이지 이동이 없을 때 사용하는 것인데 리다이렉트하여 페이지를 이동하는 것이 정상적인 작동인지 모르겠다. 

+ 해결 방향

  1. 첫 시도
     + 405로 인해 error 콜백 함수가 호출되면 path에 담긴 값으로 리다이렉트 한다.

  2. 개선
     + 인터셉터에서 url에 ```/api``` 가 포함되는 경우, ```response.sendRedirect(url);``` 하지 않고 ```response.setHeader("LoginPage", "/user/login"); ``` 으로 헤더를 추가해주고 그냥 false 반환한다.
     + false 했기 때문에 error 콜백 함수가 호출되고 ```response``` 객체의 상태 코드와 LoginPage 헤더 유무를 검사. 해당 헤더가 존재하면 값으로 넘긴 로그인 페이지 url로 리다이렉트 한다.

+ 추가로 알게 된 점

  + 리다이렉트되는 경우, ```response.setHeader(header, value);``` 는 유지되지 않는다. (정확한 이유는 아직 모르겠다.)

> [리다이렉트-헤더사용하기-참고](https://c10106.tistory.com/2169)



### 자바스크립트 템플릿

+ 자바스크립트를 이용하여 HTML 페이지에 동적으로 추가하기 위한 HTML 조각

+ 템플릿 스크립트 내 {} 부분 => 데이터로 치환하여 템플릿 전체를 동적으로 추가한다.

  > 템플릿 엔진(handlebar)과 역할이 비슷하다. handlebar를 이용하는 방법은 없을까?

+ 사용 목적

  + 템플릿은 페이지가 로드된 이후에 자바스크립트를 이용하여 동적으로 HTML DOM을 추가하기 위해 사용한다.

  

> 자바지기님 영상에서 사용한 템플릿

```javascript
<script type="text/template" id="answerTemplate">
  <!--블라블라-->
</script>
```

+ ```type="text/template"``` 은 HTML 페이지를 불러오는 동안 구분 분석기가 템플릿을 읽기는 하지만 이는 유효성을 검증하기 위함이며 렌더링되지는 않는다.



> 자바지기님 영상에서 사용하는 자바스크립트 코드

```javascript
String.prototype.format = function() {
    var args = arguments; // 해당 메소드를 호출하면서 전달된 인자가 배열 형태로 저장되어 있다.
    return this.replace(/{(\d+)}/g, function(match, number) { // this는 String 객체 자신.
        return typeof args[number] != 'undefined'
            ? args[number]
            : match
            ;
    });
};
```

+ ```String ``` 타입에 ```format``` 이라는 프로퍼티(메소드)를 새롭게 정의하는 것.

+ 용도

  동적으로 생성될 html에 작성된 {%d} 부분을 인자로 전달받은 데이터로 차례대로 바인딩한다.

  > ```html()``` 의 반환 타입은 ```String``` 타입이므로 ```String``` 에 ```format``` 메소드를 정의한다.
  >
  > 자바스크립트 템플릿 내용을 ```html()``` 로 가져오고 ```format()``` 를 이용하여 자신(```String``` 객체)에게 포함되어 있는 ```{%d}``` 형태의 문자열을 메소드 인자로 전달된 데이터로 치환한다.

+ 기타

  ```prototype``` 을 이용하여 정의하는 이유 : 모든 ```String``` 객체에서 사용할 수 있다. (맞나?)



### 핸들바 템플릿

+ 사용 이유

  + 자바스크립트 템플릿과 위 코드를 대체하기 위해서 템플릿 엔진을 사용하는 것이라고 생각했다.

+ 적용 과정

  1. handlebar.js 라이브러리 추가

     + ```handlebars-spring-boot-starter``` 의존성을 주입했기 때문에 바로 사용 가능할 줄 알았는데 handlebar.js 라이브러리가 따로 필요했다.

     + 의존성을 주입한 것은 서버 사이드 템플릿 엔진으로 핸들바를 채택한 것이고, handlebar.js는 클라이언트 사이드 템플릿 엔진으로 핸들바를 선택한 것이다.

  2. 템플릿을 작성한다.

     ```html
     <script type="text/x-handlebars-template" id="answer-Template">
     <!-- 템플릿 -->
     </script>
     ```

  3. 템플릿이 필요한 시점에 컴파일하는 자바스크립트를 작성한다.

     ```javascript
     var answerTemplate = $('#answer-Template').html();
     var template = Handlebars.compile(answerTemplate);
     var html = template(data);
     $(".answer-article-ajax").append(html);
     ```

     + Spring Framework 을 사용하는 서버 환경에서는 대부분 컴파일 과정을 프레임워크 레벨에서 처리하므로 데이터만 묶으면 되지만 Framework를 사용하지 않는 환경은 반드시 텍스트를 생성하기 위해 해당 환경에서 처리 가능한 형태로 변환하는 컴파일 작업이 사전에 수행해야 한다. [출처](https://blog.javarouka.me/2014/05/24/handlebars-server-client-multiple-useing/#)

  > Handlebars.js 작동 방식 [출처](https://www.sitepoint.com/a-beginners-guide-to-handlebars/)

  <img src="https://dab1nmslvvntp.cloudfront.net/wp-content/uploads/2015/07/1435920536how-handlebars-works.png" alt="how-does-handlebars-work" style="zoom:50%;" />

+ 문제

  + 필요한 시점에 템플릿이 추가되긴 하는데 데이터가 바인딩되지 않는 문제가 발생했다.

+ 원인

  + 하나의 스크립트에서 서버에서 컴파일되는 부분과 클라이언트에서 컴파일되는 부분이 함께 존재하는 경우

    ```html
    <-- 이 부분은 클라이언트에서 사용할 템플릿 -->
    <script id="answer-template" type="text/template" >
    	<!-- 생략 -->
    </script>
    
    <-- 이 부분은 서버에서 컴파일 될 템플릿 -->
    <div>
        <h1>{{reportName}}</h1>
        <p>{{author}}</p>
        <div id="answer-area"></div>
    </div>
    
    <-- 클라이언트 템플릿을 사용해서 그려보자 -->
    <script type="text/javascript" >
    	<!-- 생략 -->
        var templateHtml = $("#answer-template").html();
        var template = Handlebars.compile(templateHtml);
        var html = template(data);
        $("#answer-area").html(html);
    </script>
    ```

  + 서버와 클라이언트에서 동시에 handlebars를 사용하는 경우, 이미 서버에서 컴파일하면서 치환자들이 치환되어 버렸기 때문에 클라이언트에서  템플릿을 사용하려고 하면 컨텍스트 부분이 사라져서 의미가 없어진다.

    => 즉, 동일한 파일을 2번(서버, 클라이언트) 컴파일하는 경우에 발생하는 문제

+ 해결

  임시 방편을 포함하여 3가지 방법이 있다. (더 있을지도?)

  1. <u>escape 문자를 넣어서 최초 (서버에 의해) 컴파일될 때 ```{{}}``` 을 문자로 인지하도록 한다.</u> (임시 방편)

     > 클라이언트에서 컴파일되는 시점에서 ```{{}}``` 가 존재하기 때문에 정상적으로 바인딩이 된다.

     > **Escaping**: If you have `{{thing}}` in your template that you want to be viewed as template data instead of a path, use `\` to escape it. So `\{{thing}}` will print "{{thing}}" into the template, and `\\{{thing}}` will print `\` to the template, and then render the path "thing". [출처](https://mandrill.zendesk.com/hc/en-us/articles/205582537-Using-Handlebars-for-Dynamic-Content)

  2. <u>클라이언트 템플릿을 별도의 파일로 분리하여 pre-compile 하는 방법</u>

     + Handlebars에서 권고하는 방법

     > 배포할 때마다 pre-compile 해주는 작업이 필요하다.
     >
     > 이해가 잘 안되는 부분이 있어서 나중에 아래 주소 참고해서 다시 공부해야겠다.

  3. <u>embedded helper ?</u>

+ compile보다 pre-compile하는 것이 성능상 좋다. (더 찾아봐야 하는 부분)

  > Compiled Handlebars templates are much faster than non-compiled templates as compiling is a very expensive part of templating.

  

> 참고 및 출처
>
> [Node.js-with-Handlebars.js-on-server-and-client](https://stackoverflow.com/questions/10037936/node-js-with-handlebars-js-on-server-and-client)]
>
> [using-pre-compiled-templates-with-handlebars-js](https://stackoverflow.com/questions/8659787/using-pre-compiled-templates-with-handlebars-js-jquery-mobile-environment)
>
> [Handlebars(for Java)-서버,-클라이언트-동시에-사용하기](https://blog.javarouka.me/2014/05/24/handlebars-server-client-multiple-useing/#)
>
> [Handlebar를-사용하여-배포까지-(+grunt+gradle)](https://jojoldu.tistory.com/23)
>
> [핸들바-템플릿-사용](https://programmingsummaries.tistory.com/381)
>
> [springboot-handlebars-간단-화면-만들기](https://jojoldu.tistory.com/255)
>
> [프론트와-UI서버를-같은-템플릿으로-관리하는 법](https://m.blog.naver.com/PostView.nhn?blogId=tmondev&logNo=220402051411&proxyReferer=https%3A%2F%2Fwww.google.com%2F)



### @RestController

+ RESTful한 웹 서비스를 위해 제공하는 어노테이션

  > 'RESTful 하다'

  + REST 원리를 따르는 시스템

  + REST API를 제공하는 웹 서비스

+ ```@Controller``` 와 ```@ResponseBody``` 를 합쳐 편리함을 제공하는 어노테이션이다.

+ ```@Controller``` 와 차이점

  + ```@Controller``` 
  + 전통적인 Spring MVC 컨트롤러 어노테이션으로, 주로 View를 반환하기 위해 사용한다.

  + 데이터(JSON)를 반환하는 경우, ```@ResponseBody``` 를 활용해야 한다.

+ ```@RestController``` 

  + 주로 JSON/XML 형태로 객체 데이터를 반환하기 위해 사용한다.

  + ```@Controller``` 에 ```@ResponseBody``` 의 기능이 추가된 것이다.
  + 반환되는 객체 데이터를 자동으로 ```HttpResponse``` 로 직렬화한다.

+ 객체 데이터 자체만 반환하는 것보다 ```ResponseEntity<T>``` 를 이용하여 데이터와 상태코드를 함께 반환하는 것이 좋다.


> 참고 및 출처
>
> [@Controller와-@RestController-차이](https://mangkyu.tistory.com/49)

+ 클라이언트에서 응답받은 객체의 프로퍼티를 접근할 때, 기본적으로 getter가 작성된 필드 데이터에 접근할 수 있다.
  + jackson 라이브러리의 ```@JsonProperty``` 를 활용하면 getter가 작성되지 않은 필드 데이터도 접근 가능



### @JsonProperty

+ 엔티티에서 JSON 데이터로 변환하고 싶은 필드에 붙인다.
+ handler가 반환하는 객체(응답 데이터)에 getter 메소드가 없어도 조회할 수 있다.



### @JsonIgnore

+ JSON 데이터로 변환하지 않는 필드를 명시한다.



### @MappedSuperClass

+ 엔티티의 공통 매핑 정보가 필요할 때 사용한다.

  + 즉, 엔티티의 부모 클래스에 사용한다.

    > 엔티티의 부모 클래스
    >
    > + 엔티티 클래스를 추상화하여 공통되는 프로퍼티를 모아 정의한다.

+ 특징

  + DB 테이블과 별개이다.
  + ```@MappedSuperClass``` 가 붙은 클래스는 엔티티가 아니다.

+ 기타

  + 엔티티는 ```@Entity``` 나 ```@MappedSuperClass``` 가 붙은 클래스만 상속할 수 있다.



### @EnableJpaAuditing

+ 스프링(data jpa)은 엔티티가 새롭게 생성되거나 변경이 발생한 시점을 추적하는 기능을 지원한다.

  + SQL을 트리거로 작동하는 듯

+ 주로 ```@CreatedDate``` , ```@LastModifiedDate``` 등과 같은 어노테이션을 사용할 때 필요하다.

+ 대상이 되는 엔티티에 ```AuditingEntityListener``` 를 콜백 클래스로 지정해줘야 한다. 

  + ```@EntityListeners``` : 엔티티를 DB에 적용하기 이전 이후에 커스텀 콜백을 요청할 수 있는 어노테이션

  ```java
  @Entity
  @EntityListeners(AuditingEntityListener.class) // 기본 제공하는 클래스를 사용한다.
  public class BaseEntity {
  	// ...
  }
  ```



### DB 답변 수 조회

+ (자바지기님 영상) DB 쿼리로 답변 수를 조회하지 않고, 자바 코드로 답변 수를 관리하도록 구현함.

+ (나의 step4) ```Question``` 객체에서 가상 컬럼으로 답변 수를 조회하도록 ```@Formula``` 를 사용함.
  + ```@Formula``` 는 DB로부터 조회하는 시점의 답변 수를 반환함.
  + ```Question``` 객체를 조회한 이후에 새로운 답변이 ```save()``` 되면 ```@Formula``` 로 가져온 값은 새로 추가된 답변 수가 반영되지 않은 값이다.
+ (새롭게 알게 된 사실) 영속성 컨텍스트에 존재하는 엔티티는 DB로부터 조회할 필요가 없기 때문에 select 쿼리를 수행하지 않는다.



### Payload

+ Body에 포함되어 전송되는 데이터

+ 전송의 목적이 되는 데이터의 일부분으로, 함께 전송되는 헤더와 메타데이터를 제외한 것.

+ 구분되는 개념 : **Parameter**

  + Parameter : URL에 포함되어 전송되는 데이터




### Swagger 라이브러리

+ 어노테이션 기반 REST API 문서 자동화 라이브러리

+ 왜 사용하는가?

  + 서버에서 작업한 REST API를 문서화할 수 있다.
  + 클라이언트가 작성된 REST API 문서를 보고 개발할 수 있다.
  + UI를 통해 테스트가 가능하다.

+ 대체품

  + Spring REST Docs

  

## 오늘의 삽질

+ data.sql 문을 수정할 때마다 gradle의 clean을 함께 해줘야 한다.

  + build 만으로 refresh가 안되는 것 같다.
  + 안해주면 무결성 위배 같은 쿼리 오류가 발생한다.
  + data.sql문과 gradle 빌드 간의 수행 관계를 잘 모르겠다.

  

## 공부 필요

+ spring-tx (트랜잭션?)

+ 스프링 로그인 보안 (```ThreadLocal```)

+ 영속성 컨텍스트

  > https://gmlwjd9405.github.io/2019/08/06/persistence-context.html
  >
  > https://victorydntmd.tistory.com/207

+ DTO

+ ```@Profile```

  > http://wonwoo.ml/index.php/post/1933

---

## Step4

## 공부 내용

### ManyToOne

+ 질문 - 회원 관계

+ 최상위 객체가 되는 회원은 관계를 형성하는 맵핑을 자제하는 것이 좋다.

  + 왜? 최상위인만큼 맵핑이 복잡해지기 때문에

+ ```@JoinColumn```

  + 제약 조건(외래키)의 이름 지정할 수 있다.

  ```java
  @ManyToOne
  @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
  private User writer;
  ```

+ 답변 - 질문 관계

  + 질문이 존재해야 답변이 존재하는 관계의 경우, 맵핑된 URL에도 종속 관계를 표현하는 것이 좋다.

    ```java
    @Controller
    @RequestMapping("/questions/{questionId}/answers")
    public class AnswerController {
    	//...
    }
    ```



### OneToMany

+ 질문 - 답변 관계

+ 속성

  + mappedBy

    ```java
    public class Question {
    	@OneToMany(mappedBy = "question") //@ManyToOne으로 매핑한 필드의 이름
    	List<Answer> answers;
    }
    
    public class Answer {
      @ManyToOne
      Question question; // mappedBy에 지정되는 값
    }
    ```



### @Formula

+  엔티티를 조회할 때 가상 컬럼으로 맵핑될 변수에 붙인다.

+ JPA 명세는 아니지만 [하이버네이트Hiberante](https://hibernate.org/orm/releases/5.4/)에서 제공하는 [Formula 어노테이션](https://docs.jboss.org/hibernate/stable/orm/userguide/html_single/Hibernate_User_Guide.html#mapping-column-formula)으로 가상 컬럼을 매핑할 수 있다.

  > JPA 명세랑 하이버네이트랑 역할 차이?

+ Formula 어노테이션 사용시 알아두어야 할 점은 네이티브 SQL을 사용한다는 것이다.

  > 네이티브 SQL?

+ 엔티티에 포뮬러가 추가될 경우, 해당 쿼리는 항시 실행된다.

  + 해당 포뮬러가 필요할 때만 동적으로 실행하는 방법?

    + 결론부터 말하면 <u>패치 전략을 Lazy</u>로 변경해야 함
    + JPA는 기본적으로 컬렉션 타입이 아니면 패치 전략이 <u>즉시(Eager)로딩</u>이기 때문에 동적으로 필요할 때만 쿼리를 수행하도록 하고 싶다면 패치 전략을 Lazy로 변경하자.

  + 패치 전략 Lazy로 변경하기

    1. @Basic 어노테이션에 패치 전략 명시하기

    ```java
    @Basic(fetch = FetchType.LAZY)
    @Formula("(select count(*) from answer a where a.question_id = id)")
    private int countOfAnswers;
    ```

    2. Bytecode Enhancement 설정

    + [빌드 툴에서 설정을 추가해야 함](https://docs.jboss.org/hibernate/orm/5.0/topical/html/bytecode/BytecodeEnhancement.html)

      > Bytecode Enhancement?

  > 참고
  >
  > [엔터티 카운트 성능 개선하기](https://www.popit.kr/jpa-%EC%97%94%ED%84%B0%ED%8B%B0-%EC%B9%B4%EC%9A%B4%ED%8A%B8-%EC%84%B1%EB%8A%A5-%EA%B0%9C%EC%84%A0%ED%95%98%EA%B8%B0/)



### JPA의 즉시 로딩, 지연 로딩

+ 데이터베이스로부터 조회하는 시점을 정할 수 있다
+ 연관 관계를 가지고 있는 엔티티를 조회할 때,
  + 해당 엔티티를 조회할 때 연관된 엔티티를 같이 로딩할 것인가 => *즉시 로딩(Eager)*
  + 해당 엔티티 정보만 조회하고, 연관된 엔티티는 필요한 시점에 로딩할 것인가 => *지연 로딩(Lazy)*

+ 목적

  + 데이터베이스 조회 성능을 최적화하기 위해서 사용

+ 지연 로딩이 가능한 이유는 **프록시 패턴** 을 이용하기 때문!

  + 프록시 객체가 연관된 엔티티를 대행함. 프록시가 연관된 엔티티를 바라보고 필요한 시점에 엔티티의 데이터를 리턴함.
  + 단, 만약 연관된 엔티티가 이미 영속성 컨텍스트에 존재한다면

  > 회원을 조회할 때, 연관 관계를 맺고 있는 팀(@ManyToOne)에는 실제 팀 엔티티가 들어가는 것이 아니고 프록시 객체가 들어감.

+ 단, 연관된 엔티티가 이미 영속성 컨텍스트에 존재한다면 지연 로딩과 관계없이 즉시 로딩처럼 동일하게 영속성 컨텍스트에서 엔티티를 가져온다.

  > 영속성 컨텍스트는 ```EntityManager``` 랑 관련있는 듯?

  > 참고
  >
  > https://coding-start.tistory.com/82



### Interceptor 이용하여 로그인 구현하기

+ 목표

  + 인터셉터를 이용하여 로그인 확인 여부 중복 코드 제거하기
  + 인터셉터를 이용하여 ```HttpSession``` 으로부터 가져온 사용자 객체를 보관하도록 구현. 컨트롤러에서 직접 ```HttpSession```에서 사용자를 찾아오는 과정 제거하기

+ 인터셉터란?

  + 인터셉터는 Handler(Controller) 수행 전과 후에 요청 정보를 가로채어(?) 처리할 수 있다.

+ 구현 과정

  1. HandlerInterceptor 구현체를 정의

     - `preHandle()` : 컨트롤러(즉 RequestMapping이 선언된 메서드 진입) 실행 직전에 동작
     - `postHandle()` : 컨트롤러 진입 후 view가 랜더링 되기 전 동작
     - `afterCompletion()` : 컨트롤러 진입 후 view가 정상적으로 랜더링 된 후 제일 마지막에 동작

     ```java
     @Component
     public class UserInterceptor implements HandlerInterceptor {
         @Override
         public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
         //...
     }
     ```

  2. 인자로 전달되는 ```HttpServletRequest``` 를 이용하여 ```request``` 에 세션 사용자 정보를 추가

     ```java
     request.setAttribute("sessionedUser", HttpSessionUtils.getUserFromSession(session));
     ```

  3. 어플리케이션에 인터셉터 등록

     + ```addPathPatterns()``` , ```excludePathPatterns()``` , ```pathMatcher()``` 등 메소드를 통해 path 별로 Interceptor를 적용할 범위를 지정

     ```java
     @Configuration
     public class MvcConfig implements WebMvcConfigurer {
     
         @Autowired
         @Qualifier(value = "userInterceptor")
         private HandlerInterceptor interceptor;
       
         //...
       
         @Override
         public void addInterceptors(InterceptorRegistry registry) {
             registry.addInterceptor(interceptor)
                     .addPathPatterns("/users/**/form")
                     .addPathPatterns("/users/**/update")
                     .addPathPatterns("/questions/**")
                     .addPathPatterns("/questions/**/answers/**");
         }
     }
     ```

     > 추가 구현 내용
     >
     > + REST API(?)에 의해 동일한 URL에 다른 메소드가 맵핑된 경우 처리하기
     >
     >   => ```HttpServletRequest``` 의 ```requestURI``` 와 ```Method``` 정보가 저장되어 있음. ```Method``` 별로 다르게 처리함.

     ```java
     @Override
     public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       if (matchQuestionURI(request.getRequestURI(), request.getMethod()))
         return true;
       // ...
     }
     
     private boolean matchQuestionURI(String requestURI, String requestMethod) {
       return requestURI.matches("/questions/[0-9^/]+$") && HttpMethod.GET.matches(requestMethod);
     }
     ```

  > 참고
  >
  > [https://medium.com/@devAsterisk/spring-boot-기반-rest-api-제작-5-bd1b4f0e4680](https://medium.com/@devAsterisk/spring-boot-%EA%B8%B0%EB%B0%98-rest-api-%EC%A0%9C%EC%9E%91-5-bd1b4f0e4680)
  >
  > https://heowc.dev/2018/02/06/spring-boot-interceptor/
  >
  > [http-method-구분참고](https://stackoverflow.com/questions/45825582/spring-configureradapter-exclude-pattern-for-separate-http-method)
  > 
  > https://elfinlas.github.io/2017/12/28/SpringBootInterceptor/

### 기타

+ 객체의 getter를 사용하는 것에 의심을 갖고, 객체 자체를 인자로 전달해서 구현하는 방법을 모색해보자

+ 어떤 검사에 대한 결과를 객체화하는 방법도 고려해보자

  ```java
  Result valid(HttpSession session, Question question) {
  	if (!HttpSessionUtils.isLoginUser(session)) {
  		return Result.fail("로그인이 필요합니다.");
  	}
  	
  	User loginUser = HttpSessionUtils.getUserFromSession(session);
  	if (!question.isSameWriter(loginUser)) {
  		return Result.fail("자신이 쓴 글만 수정, 삭제 가능합니다.");
  	}
  	
  	return Result.ok();
  }
  ```

  ```java
  public class Result {
  	private boolean valid;
  	private String errorMessage;
  	
  	private Result(boolean valid, String errorMessage) {
  		this.valid = valid;
  		this.errorMessage = errorMessage;
  	}
    
    public boolean isValid() {
      return valid;
    }
    
    public String getErrorMessage() {
      return errorMessage;
    }
  	
  	public static Result fail(String errorMessage) {
  		return new Result(false, errorMessage);
  	}
  	
  	public static Result ok() {
  		return new Result(true, null);
  	}
  }
  ```



+ 스프링 부트는 기존 스프링 환경에서 설정했던 XML 없이 자바 코드로 설정이 가능하다.

  <img src="https://user-images.githubusercontent.com/33659848/76007231-10f98880-5f51-11ea-8593-1beaa0fc83a3.png" alt="spring_xml" style="zoom: 33%;" />

  + xml 파일은 오류를 찾기 힘들지만 자바 코드는 비교적 쉽게 관리할 수 있다.

  + ```WebMvcConfigurer``` 구현 클래스가 xml을 대체한다. (정확히 어떤 파일?)

    ```java
    @Configuration
    public class MvcConfig implements WebMvcConfigurer {
    	// ...
    }
    ```

  > [참고](https://m.blog.naver.com/PostView.nhn?blogId=scw0531&logNo=221066404723&proxyReferer=https%3A%2F%2Fwww.google.com%2F)



## 공부해야 할 것

+ JPA의 Eager 로딩과 Lazy 로딩

  + 영속성 컨텍스트

    > https://lng1982.tistory.com/273

  + 프록시 패턴

+ 내부 조인, 외부 조인

+ ***Servelet의 Filter*** vs ***Spring MVC의 HandlerInterceptor*** vs ***Spring AOP*** 비교

  > https://doublesprogramming.tistory.com/210

---

## step3

### 미구현 (고민했던 것)

+ 답변 수정 기능

  + 답변의 수정 버튼이 누르면 기존 show.html에서 답변에 해당되는 부분만 바뀌도록 구현해야 할듯 (Ajax?)
  + 현재로서는 핸들바 if문을 이용해서 show의 기본 화면과 답변 수정 화면을 구분해야 하나 싶은데 실제로는 핸들바로 처리할 것 같지 않다.

+ index.html에서 각 질문에 대한 답변 수 출력하기

  + 질문 내용(show.html)에서는 ```countByQuestionId(Long questionId)``` 메소드를 이용해서 답변 개수를 쉽게 구할 수 있었음.

  + 내가 생각한 index.html에서 질문별 답변 개수를 구하는 것은 

    1. <u>```Question``` 객체가 자신과 연계된 답변 객체의 개수를 필드(```@Transient```)로 저장하는 방법</u>
    2. <u>질문 순서별로 답변 개수를 저장하고 있는 List를 모델에 전달하는 방법</u>

    들이 있지만 2번은 좋은 방법이 아닌 것 같고, 1번은 sql문으로 처리할 수 있을 것 같은데 굳이 필드로 가져야 하는가 고민됨.

  + ```@OneToMany```를 이용하여 1:N (Question:Answer)관계를 형성, ```List<Answer>``` 필드를 가지고 있게 구현하고, ```list.size()``` 등으로 구하는 방법도 있으나 ```@OneToMany```에 대한 성능 이슈가 많아서 머리가 복잡해져서 그만둠.

    + 대충 이해한 것으로는 ```@OneToMany```를 이용하는 경우, 어떤 처리를 할때 마다 쿼리 대상에 대한 불필요한 조회를 수행한다는 내용 같은데 DB와 SQL에 대해서 잘 모르는 상태로 성능 이슈 내용을 이해하려니까 어렵다.

      >  https://okky.kr/article/335497

    + ```@OneToMany``` 연관 관계에서 delete를 수행할 때도 비슷한 내용이 있었음.

      > https://jojoldu.tistory.com/235

  + ```@OneToMany```를 사용하지 않고 JPQL을 이용하여 구현해보려고 했으나 실패함. 다음 스텝에서 도전!

+ (고민했던 점) ```Question```이 삭제되면 외래 키로 관계된 ```Answer``` 객체들도 삭제되어야 하는데 그냥 ```Question```만 삭제하면 외래키 제약 조건에 의해 오류가 발생함. ```cascade = CascadeType.Delete``` 을 이용하려고 했지만 또 ```@OneToMany``` 을 사용해야 하는 것 같아서 피해버렸다. [(위 링크와 동일) JPA delete 관련](https://jojoldu.tistory.com/235) 참고해서 직접 범위 조건의 삭제 쿼리를 생성하는 방식으로 구현함. 이때 ```Answer``` 를 먼저 삭제하고 ```Question``` 을 삭제해야 하는 것 주의

  + 이렇게 하니까 한건씩 삭제하지는 않는 것 같다.

    > 삭제 쿼리에 대한 콘솔 로그

    ```
    Hibernate: 
        delete 
        from
            answer 
        where
            question_id in (
                ?
            )
    Hibernate: 
        delete 
        from
            question 
        where
            id=?
    ```

  + (추가 내용) on delete cascade 속성을 지정하려면 ```@OneToMany``` 를 해줘야 한다고 생각했는데 다른 분 코드 보니까 다른 방법이 있었음

    ```java
    @Entity
    public class Answer {
        // (생략)
      @ManyToOne
      @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
      // @OnDelete 어노테이션이 있었구나
      @OnDelete(action = OnDeleteAction.CASCADE)
      private Question question;
      // (생략)
    }
    ```

    > 이 방법으로 하면 한 건씩 가져와서 delete를 수행할까? 아니면 범위 기반으로 제거될까? (아래 내용 참고)
    
  +  delete 쿼리가 ```Question``` 삭제 밖에 안보임. ```Answer``` 객체를 삭제하는 쿼리가 보일 줄 알았는데?

  +  더 간단한 방법인 것 같긴 한데 삭제 쿼리가 아예 출력되지 않아서 확신을 못하겠다.

  +  ```@OneToMany``` 도 직접 테스트하고 한 건씩 삭제되는 것이 맞는지 확인해봐야 겠다. 참고한 링크가 다른 조건에서 수행된 걸수도 있으니까

     > 삭제 쿼리에 대한 콘솔 로그

     ```
     Hibernate: 
         delete 
         from
             question 
         where
             id=?
     ```

### 궁금한 점

+ equals랑 hashCode 오버라이딩 안해주면 ```Question```이 참조하고 있는 ```User``` 객체의 주소가 바뀌는 현상

  + 오버라이딩 전

  > 질문 객체 생성 (create : 생성한 질문 객체, writer = 작성한 회원 객체 주소. 즉 로그인된 회원 객체 주소)

  ```java
  create : Question{id=1, writer='com.codesquad.qna.model.User@7940fb4c', ... },
  writer : com.codesquad.qna.model.User@7940fb4c
  ```

  > 질문의 작성자와 로그인된 회원 비교 시, 주소값 출력
  >
  > => 생성 당시 질문 객체의 회원 주소(위에 출력된 주소)랑 select 해온 질문 객체의 회원 주소가 달라짐

  ```java
  matchedQuestion : Question{id=1, writer='com.codesquad.qna.model.User@2d5fa3a1', ... },
  sessionedUser : com.codesquad.qna.model.User@7940fb4c
  ```

  

  + 오버라이딩 후

  > 로그인된 회원 객체의 주소

  ```java
  login Success : com.codesquad.qna.model.User@290e4a3c
  ```

  > 질문 객체 생성

  ```java
  create : Question{id=1, writer='com.codesquad.qna.model.User@290e4a3c', ... },
  writer : com.codesquad.qna.model.User@290e4a3c
  ```

  > 오버라이딩 전과 달리, 생성 당시 작성자의 주소가 동일함

  ```java
  matchedQuestion : Question{id=1, writer='com.codesquad.qna.model.User@290e4a3c', ... },
  sessionedUser : com.codesquad.qna.model.User@290e4a3c
  ```

  

### 느낀 것

+ 잘 모르면서 성능 문제 신경쓰려니까 머리 아프다. 지금은 그냥 구현에만 신경쓰는게 낫겠다. (맨날 그렇게 생각했지만...)
+ 내가 직접 테스트하지 않고 누군가가 테스트한 내용만 믿는 것이 조금 안일한 것 같다.



### 공부 필요

- 웹 프로젝트의 절대 경로, 상대 경로
- REST API
- 로직의 응집도란?



---

## step2

+ H2 Database를 이용하여 회원, 질문 데이터 관리

  >  H2 Database

  + 자바로 구현된 DB

  + 별도의 설치가 필요없음. 라이브러리만 추가해주면 사용할 수 있음.

  + spring-boot-starter-data-jpa와 함께 사용

  + 자바 객체에 맵핑해놓은 설정에 따라서 자동으로 테이블을 생성해줌

  + /h-console의 jdbc url과 application.properties의 db url가 동일해야 함

    ```
    spring.datasource.url=jdbc:h2:~/java-qna;DB_CLOSE_ON_EXIT=FALSE
    ```



+ 클래스를 DB 테이블에 매핑

  1. @Entity

     + 테이블로 생성될 클래스

  2. @Id

     + primary key 지정

  3. @GeneratedValue

     + DB에서 자동으로 값을 생성시켜 줌

     + 자동 증가하는 정수값을 primary key로 지정하기도 함

  4. @Column(nullable=false)

     + 컬럼 지정 (명시 안해도 알아서 변수가 컬럼화. 옵션이 필요한 경우에 명시함)

     + 널 허용하지 않는 컬럼으로 지정하기

     + null 입력되어도 별다른 에러가 발생하지 않음. 왜 하는걸까? 명시 용도?

       ```java
       @Column(nullable=false)
       ```

  5. @Autowired

     + DB 테이블과 자동으로 연결시켜줌???

     + 의존성 주입을 쉽게 해줌 (*잘 모르겠음*)

       + 기존에는 xml 설정 파일에 bean을 설정해주거나, @Bean으로 표시해줘야 함

       

+ 테이블의 객체화(?)

  > @XXX으로 DB 객체화 가능한 이유는 jpa를 사용했기 때문

  1. ```CrudRepository``` 상속받은 테이블 구현체

  ```java
  public interface UserRepository extends CrudRepository<User, String> { }
  ```

  + CRUD 기능을 제공하는 인터페이스

    > CRUD = Create(생성), Read(읽기), Update(갱신), Delete(삭제)

  + 단순하게 CRUD 작업만 한다면 CrudRepository를, 그 외에 페이징, sorting, jpa 특화 기능들을 사용하길 원한다면 JpaRepository를 사용하면 됨

  + 참고

    [[JPA\] JpaRepository 인터페이스와 CrudRepository](https://blog.naver.com/writer0713/221587319282)

    

  2. @Autowired 추가

     ```java
     @Autowired
     private UserRepository userRepository;
     ```

     

+ save의 동작 방식

  primary key가 동일한 객체가 존재하면 해당 객체를 update 없으면 insert

  

+ findOne() 과 findById() 차이점 : findOne이 findById로 대체된 것



+ Model / ModelMap / ModelAndView 비교
  + Model : 인터페이스
  + ModelMap : 구현체
  + ModelAndView : 모델과 뷰 객체를 포함하는 컨테이너



+ 메소드 간단 비교
  + GET : select 성격, 서버에서 어떤 데이터를 가져와서 뿌려주는 용도
  + POST : 폼에 입력된 데이터 전송. 서버의 값이나 상태를 바꾸기 위해 사용
  + PUT : 기존 데이터 변경 작업 (*POST와 대단한 차이를 못느끼겠음*)



+ form 태그에서 PUT 메소드 전달하는 방법

  ```html
  <input type="hidden" name="_method" value="PUT" />
  ```

  + _method (예약어)



+ Optional의 orElse()와 orElseGet() 차이점

  + orElse() : null이던 말던 항상 호출됨
  + orElseGet() : null일 때만 호출됨

  

> 스프링 부트 사용 시, default 설정 변경할 때 **application.properties** 에서 변경하는 것
