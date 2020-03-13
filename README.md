# Step5 
[배포 url : https://hyunjun2.herokuapp.com/](https://hyunjun2.herokuapp.com/)

## Ajax를 왜 사용하는가? 
- 서버에 리퀘스트를 해도 고정된 값이 있고 전달되는 값이 있다. 이 때 모든 리소스를 매번 요청하지 않고 내가 필요한 데이터만 리퀘스트하고 그 데이터만 리스폰스할 수 있도록 도와주는 기술이다. 예를 들어, 댓글을 추가할 때 모든 페이지를 리퀘스트하지 않고 댓글 관련된 리소스만 리퀘스트하는 것이다.

## Step5 진행하며 배운점
- 객체 데이터를 json으로 만들 때 왜 stackOverFlow가 발생할까? toString()처럼 Mapping된 객체가 있으면 그 객체 속성으로 들어가는데, 만약 양방향 매핑이라면 서로 참조하기 때문이다.

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
- show에 <script> 파일을 읽어야 한다. 


