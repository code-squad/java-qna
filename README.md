# step2. 데이터베이스 활용

## comment 사항

- [x] index는 색인의 의미, id나 questionNum으로 고칠것.

- [x] getWrittenTime -> getFormattedWrittenTime

- [x] @Controller

  @RequestMapping("/questions") 설정

- [x] 홈에선 index보단 main이나 welcome으로 변경

- [x] PostMapping("/user/create") -> ("/users")

- [ ] spring.thymeleaf.cache=false 는 타임리프는 안쓰니까 지우기

# 사용자 데이터를 DB에 저장

## 2.1 데이터베이스 설정

#### MVNRepository

- h2: 1.4.192
- JPA: 2.2.4 (스프링부트와 버전 동일)

### 2.1.2 application.properties 설정

#### DB Connection 설정

- jdbc:h2:mem://localhost/~/java-qna 을 h2 접속경로에 넣을 것.

#### 실행 쿼리 보기 설정

#### 테이블 자동 생성 결정

- [x] 서버를 시작하는 시점에 DB테이블을 drop후에 다시 생성하도록 설정하는 방법.
- [ ] ~~서버를 시작하는 시점에 DB 테이블을 drop하지 않도록 설정하는 방법.~~

#### h2 db console에 접근 설정



## 2.2 User 클래스를 DB 테이블에 매핑

- User파일에서 데이터 베이스 Entity주입할 경우 생성자를 만들면 안된다. 오류걸림
- 그냥 setter만들어야 오류가 걸리지 않는다.



## 2.3 Controller에서 Repository 사용

#### 사용자 데이터 추가

- save()

#### 사용자 목록 조회

- UserRepository의 findAll() 메소드를 통해 DB에 저장되어 있는 전체 사용자 목록을 조회한다.

#### 사용자 조회

```java
@GetMapping("/{id}")
public ModelAndView show(@PathVariable long id) {
	ModelAndView mav = new ModelAndView(템플릿 html);
	mav.addObject("핸들바", userRepository.findById(id).get());
	return mav;
}
```

- 접속하는 템플릿에서 해당 url 입력할 것. 

```html
<th scope="row"><a href="/users/{{id}}">{{id}}</a></th>
```

