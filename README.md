# step3. 로그인 구현

## comment 사항

- [x] 메서드 이름은 동사로 짓자. (QuestionController에 그냥 지은 이름이 많다.)
- [x] REST API가이드를 보고 그에 맞게 URI를 지정하자.
- [x] REST API가이드에 의하면 유저 목록조회는 GET, /user로 충분하다.

```properties
spring.mvc.hiddenmethod.filter.enabled=true //프로퍼티 적용할 것
```

- [x] findByID결과는 Optional로 get()으로 가져오는것을 자제해야한다. 데이터가 없을경우 예외발생함.
- [ ] Optional 사용법을 알자.
- [x] getter, setter를 안쓰는 방법을 찾아보자. User 객체의 메서드로 만들어보자. (근데 뭔뜻인지모름-> [자바지기 영상](https://www.youtube.com/watch?v=DaqWKDvdmAk))
- [ ] 객체의 필드는 시간과 관련된 객체를 넣고(LocalDateTime같은)
  포멧팅된 문자열을 가져오는데 사용될 메서드를 하나 더 만들어 사용하는 것은 어떨까?

---

Rest API 가이드 링크

- [Put vs Post](https://1ambda.github.io/javascripts/rest-api-put-vs-post/)

- [RESTful API 가이드](https://sanghaklee.tistory.com/57)

- [RESTful 팁](https://spoqa.github.io/2012/02/27/rest-introduction.html)
- [REST API 사용하기](https://meetup.toast.com/posts/92)

---

## 로그인 기능 구현

### 요구사항

- [x] 로그인이 가능해야 한다.

- [x] 현재 상태가 로그인 상태이면 상단 메뉴가 "로그아웃", "개인정보수정"이 나타나야 하며,  

  로그아웃 상태이면 상단 메뉴가 "로그인", "회원가입"이 나타나야 한다.



## 로그인에 따른 메뉴 처리 구현

#### 세션 설정

- Mustache에서 HttpSession 데이터를 접근하기 위한 설정(application.properties)

```properties
handlebars.expose-session-attributes=true
```



## 테스트 데이터 추가

- src/main/resources 폴더에 data.sql 파일을 추가한다.
- 사용자 데이터를 다음과 같이 insert sql 쿼리를 추가한다.

```sql
INSERT INTO USER (USER_ID, NAME, PASSWORD, EMAIL) VALUES ('kses1010', 'Sunny', '123', 'kses1010@gmail.com');
INSERT INTO USER (USER_ID, NAME, PASSWORD, EMAIL) VALUES ('hihi', 'Hello', 'abc', 'hihi@naver.com');
```

테스트 데이터를 추가하니 다음과 같은 문구가 나타남.

```java
WARNING: An illegal reflective access operation has occurred
WARNING: Illegal reflective access by com.github.jknack.handlebars.context.MemberValueResolver (file:/home/sosah/.gradle/caches/modules-2/files-2.1/com.github.jknack/handlebars/4.0.6/ccf00179b6648523e5c64b9b5fb783d89e42401b/handlebars-4.0.6.jar) to method java.util.Collections$EmptyMap.isEmpty()
WARNING: Please consider reporting this to the maintainers of com.github.jknack.handlebars.context.MemberValueResolver
WARNING: Use --illegal-access=warn to enable warnings of further illegal reflective access operations
WARNING: All illegal access operations will be denied in a future release
```



## 개인정보 수정

### 요구사항

- [x] 회원가입한 사용자의 정보를 수정할 수 있어야 한다.
- [x] 이름, 이메일만 수정할 수 있으며, 사용자 아이디는 수정할 수 없다.
- [x] 비밀번호가 일치하는 경우에만 수정 가능하다.
- [x] 다른 사용자의 정보를 수정하려는 경우 에러페이지를 만든 후 에러 메시지를 출력한다.

### 리팩토링

- boolean method is always inverted 가 나타나면 해당 메소드를 사용할 시 항상 !를 사용해야 한다.  다른사람들이 
  헷갈리지 않도록 inverted 가 뜨면 다르게 표현해보자.

- 해당 중복되는 코드가 있다. 차차 없애도록 노력하자.

---

## 질문 기능 구현하기

### 요구사항

- [x] 사용자는 질문을 할 수 있으며, 모든 질문을 볼 수 있다.
- [x] 단, 질문을 할 수 있는 사람은 로그인 사용자만 할 수 있다.
- [x] 질문한 사람은 자신의 글을 수정/삭제할 수 있다.



#### setter 대신 생성자로 처리하기

```java
Question newQuestion = new Question(sessionUser.getUserId(), title, contents);

public class Question {
	
    public Question() {}

    public Question(String writer, String title, String contents) {
        super();
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }
}
```

#### update후 해당 번호페이지 띄우기

```java
@PutMapping("/{id}")
public String updateQna(@PathVariable Long id, String title, String contents) throws IllegalAccessException {
        Question question = 		         questionRepository.findById(id).orElseThrow(IllegalAccessException::new);
        question.update(title, contents);
        questionRepository.save(question);
        return String.format("redirect:/questions/%d",id); //여기처럼 하면됨.
    }
```

