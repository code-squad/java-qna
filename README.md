# Step5 
[배포 url : https://hyunjun2.herokuapp.com/](https://hyunjun2.herokuapp.com/)

## Step4 리뷰어 피드백 참고 리팩토링
- [ ] Exception이 발생하는 부분만 try - catch 하기. AnswerController의 hasPermission()
- [x] 객체의 필드로 선언된 repository를 메서드의 인자로 넘길 필요없음.  
- [x] PathVariable에 .(dot) 대신 camelCase로 표기하기. ex) answer.id -> answerId
- [x] 기능 추가에 따른 hasPermission의 이름 구분 생각해보기.  
- [x] question.isNoAnswer() || isSameBetweenWriters() -> question.isDeletable() 변경 추천
- [ ] 로그인 체크하는 메서드 중복 발생하니 클래스로 뽑아서 공통으로 사용하기.
- [x] 유저 업데이트할 때 인자 여러개보다 DTO를 만들면 코드 간결성, 관리 측면에 효과적. 
- [x] 호눅스의 추천 : DTO에 대한 공부
- [x] this 사용 시 일관성있게 모든 필드에서 사용.
- [x] OrderBy 대신 Repository에서 sort 해보기. -> findAllByOrderByIdAsc();
- [x] ORM으로 연관관계 시 toString() 사용 주의. 공부해보기. 
- [x] stream().allMatch() 사용하기. -> allMatch(), anyMatch(), noneMatch(), findFirst(), findAny() 적절히 사용하기 

### DTO(Data transfer object)
DTO는 데이터를 전달하는 역할을 하는 객체를 의미한다. 보통 객체란 역할과 책임을 가진 존재이다. 하지만 DTO로 사용될 땐 마치 Collection처럼 여러개의 데이터를 한번에 전달할 때 사용된다. 속성과 setter, getter가 존재한다. 

### ORM으로 연관관계 시 toString() 대신 toStringBuilder() 
[참고 : 오라클 ToStringStyle](https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/builder/ToStringStyle.html)
 
[참고 : 오라클 ToStringBuilder](https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/builder/ToStringBuilder.html) <br>
ORM을 통해 객체 간 연관관계가 양방향일 경우 toString 사용 시 StackOverFlow exception이 발생할 수 있다. 이유는 A 객체의 toString에서 매핑된 B객체를 참조한다. 그러면 B객체의 toString에서도 A객체를 참조한다. 그러면 A->B->A->B와 같이 에러가 발생한다. 해결책은 toStringBuilder가 있다.<br>  
아래 코드에서 1번은 기본 형태이다. 2번 형식이 간결하고 style을 지정할 수 있다. (reflectionToString)

```java
 public class Person {
   String name;
   int age;
   boolean smoker;

   ...
    // 1번
   public String toString() {
     return new ToStringBuilder(this).
       append("name", name).
       append("age", age).
       append("smoker", smoker).
       toString();
   }
    // 2번
    public String toString() {
        return ToStringBuilder
            .reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
 }
```

ToStingStyle은 7가지가 있다. 
DEFAULT_STYLE 
JSON_STYLE The 
MULTI_LINE_STYLE 
NO_CLASS_NAME_STYLE 
NO_FIELD_NAMES_STYLE 
SHORT_PREFIX_STYLE 
SIMPLE_STYLE 

## Ajax를 왜 사용하는가? 
- 서버에 리퀘스트를 해도 고정된 값이 있고 전달되는 값이 있다. 이 때 모든 리소스를 매번 요청하지 않고 내가 필요한 데이터만 리퀘스트하고 그 데이터만 리스폰스할 수 있도록 도와주는 기술이다. 예를 들어, 댓글을 추가할 때 모든 페이지를 리퀘스트하지 않고 댓글 관련된 리소스만 리퀘스트하는 것이다. 

### 특정 태그에 동적 html 추가하는 2가지 방법 : prepend, append
[참고: https://api.jquery.com/prepend/](https://api.jquery.com/prepend/)

[참고: https://api.jquery.com/prepend/](https://api.jquery.com/prepend/) <br>
- prepend : 선택한 태그의 모든 데이터의 위로(처음으로) 동적 html이 추가된다. ex) $( ".inner" ).prepend( "<p>Test</p>" );
- prependTo : 동적 html을 선택한 태그로 넣는다. ex) $( "<p>Test</p>" ).prepend( ".inner" );
- append : 선택한 태그의 모든 데이터의 뒤로(마지막) 동적 html이 추가된다. ex) 
- appendTo : append와 순서가 다르다. 

```html
<h2>Greetings</h2>
<div class="container">
  <div class="inner">Hello</div>
  <div class="inner">Goodbye</div>
</div>
```

```html
$( ".inner" ).prepend( "<p>Test</p>" );
```

```html
<h2>Greetings</h2>
<div class="container">
  <div class="inner">
    <p>Test</p>
    Hello
  </div>
  <div class="inner">
    <p>Test</p>
    Goodbye
  </div>
</div>
```

- append 예시 

```html
$( ".inner" ).append( "<p>Test</p>" );
```

```html
<h2>Greetings</h2>
<div class="container">
  <div class="inner">
    Hello
    <p>Test</p>
  </div>
  <div class="inner">
    Goodbye
    <p>Test</p>
  </div>
</div>
```

## Step5 진행하며 배운점
- 객체 데이터를 json으로 만들 때 왜 stackOverFlow가 발생할까? toString()에서 Mapping된 객체가 있으면 그 객체 속성으로 들어가는데, 만약 양방향 매핑이라면 서로 참조하기 때문이다.

## Step5 버그 해결과정 
- 답변 추가 시 자바지기처럼 OrderedBy(id DESC)로 수정했는데 새로고침하면 asc가 된다. 그 이유는 자바지기와 다르게 나는 question과 answers를 따로 넘기기 때문에 Repository에서 정렬시킨 다음 가져와야 했다. findByQuestionIdAndDeletedFalseOrderByIdDesc(id) 추가. 

## 답변 기능 Ajax이용해 구현 하기. 
- 답변 추가 시 서버에 전송하는 기능을 제한한다. ajax가 대신 서버에 전달하게 하는 기능 -> 왜?? 댓글과 관련된 리소스만 받아오며 되는데 매번 모든 리소스를 가져오면 비효율적이기 때문이다. 
- footer에 src를 절대경로로 변경. 
- [에러] show에서 footer 중복코드를 제거하지 않고 그대로 사용하니 데이터를 못가져온다. 중복 코드를 확실하게 반영하기.
- serialize()을 이용해 queryString 가져오기.

```javascript
var queryString = $(".answer-write").serialize();
    console.log("query : " + queryString);
```

- attr를 이용해 클릭된 url을 가져온다. 

```javascript
var url = $(".answer-write").attr("action");
    console.log("url : " + url);
```
- $ajax로 데이터 보내기

```javascript
    $.ajax({
        type : 'post',
        url : url,
        data : queryString,
        dataType : 'json',
        error : onError,
        success : onSuccess});
```

### AnswerController를 api를 처리하는 controller로 변경
- AnswerController가 api를 처리한다는 의미에서 url 앞에 /api를 붙인다. 

```java
@RequestMapping("/api/questions")
```

- AnswerController가 리퀘스트를 처리한 후에 web이 아니라 Answer데이터를 전달하도록 리턴 타입을 바꾼다. 
```java
    public Answer create(@PathVariable Long questionId,
```

- @RestController : Controller가 요청된 리소스에 응답하는 데이터를 json형태로 보내주는 어노테이션

### answer데이터 서버에서 json으로 받기 
- [에러1] status 500 뜨면서 json 데이터 못 가져올 때 : 에러 로그를 찾아보니 User에 @OneToMany로 매핑된 quesitons, answers가 문제. -> 둘을 제거하니 해결. 
- [에러2] json은 가져오지만 새로고침 누르면 stackOverFlow 뜰 때 : answer와 question의 toString이 문제. answer와 question이 매핑되어 있기 때문에 한 객체이 호출되면 toString이 실행되는데 그 때 서로 객체를 참조함. -> toStringBuilder로 대체.
- [에러3] 두 번째 댓글의 json을 못 가져오고 stackOverFlow 에러 발생 : answer와 question의 매핑 문제. 매핑된 상태에서 json을 만드려고 하니 서로 반복적으로 DB에서 꺼냄. -> 한쪽을 @JsonIgnore로 만들지 않기.

```java
Could not write JSON: Infinite recursion (StackOverflowError); nested exception is com.fasterxml.jackson.databind.JsonMappingException: Infinite recursion (StackOverflowError) (through reference chain: com.codessquad.qna.domain.Answer["question"]->com.codessquad.qna.domain.Question["answers"]->org.hibernate.collection.internal.PersistentBag[0]->com.codessquad.qna.domain.Answer["question"]->com.codessquad.qna.domain.Question["answers"]->org.hibernate.collection.internal.PersistentBag[0]->com.codessquad.qna.domain
```

### User의 json 데이터를 처리하는 apiController 생성
- @RestController 사용

```java
@RestController
@RequestMapping("/api/users")
public class ApiUserController {}
```

- @JsonProperty, @JsonIgnore 사용 : getter를 이용하지 않고 어노테이션을 통해 객체의 속성 변환 가능. 아래 코드에서 password는 json으로 표현되지 않음.

```java
    @JsonProperty
    private String userId;
    @JsonIgnore
    private String password;
```

### json을 동적으로 처리할 HTML templates 준비 
- show에 <script>태그의 templates을 동적으로 제어하는 것. 
- script.js에 아래 코드 추가. 처음에 있는데 삭제한 경우 추가한다.

```javascript
String.prototype.format = function() {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function(match, number) {
        return typeof args[number] != 'undefined'
            ? args[number]
            : match
            ;
    });
};
``` 

- template에 answer json 데이터 전달.

```javascript
function onSuccess(data, status) {
    console.log(data);
    var answerTemplate = $("#answerTemplate").html();
    var template = answerTemplate.format(data.writer.name, data.formattedCreatedDate,
        data.contents, data.id, data.id);
    $(".qna-comment-slipp-articles").prepend(template);
    $(".answer-write textarea").val("");
}
```
