### Heroku
> https://wooody92.herokuapp.com/

-------
# STEP 2
# 사용자 데이터를 DB에 저장하는 동영상

## [H2 DB 추가 및 설정](https://youtu.be/F3koiTIJCwM)

- 질문/답변 게시판을 구현하기 위한 HTML 템플릿을 추가(**이미 진행했기 때문에 생략 가능**)
- H2 데이터베이스에 대한 의존관계 설정 및 설정
- H2 데이터베이스 관리툴 확인

## [회원가입, 목록 기능 구현](https://youtu.be/69tNvDm-iiI)

- 데이터베이스의 테이블과 자바 객체를 매핑
- 회원가입 기능을 DB를 사용하도록 수정
- 회원목록 기능을 DB를 사용하도록 수정

## [개인정보 수정 기능 구현 1](https://youtu.be/D3PjDIYZYW0), [개인정보 수정 기능 구현 2](https://youtu.be/V2AhIjdfcMg)

- 웹 애플리케이션 개발의 기본 흐름을 이해할 때 가장 복잡한 과정 중의 하나가 데이터베이스에 추가된 정보를 수정하는 것이다.
- 회원가입한 사용자의 정보를 수정하는 기능을 1단계와 2단계로 나누어 진행한다.

------

# 사용자 데이터를 DB에 저장

## 데이터베이스 설정

#### 의존관계 설정

- pom.xml 파일에 다음 라이브러리에 대한 의존관계를 설정한다.

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <version>1.4.192</version>
</dependency>
코드복사
```

------

#### DB Connection 설정 – application.properties

```
spring.datasource.url=jdbc:h2:mem://localhost/~/java-qna;MVCC=TRUE;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
코드복사
```

------

#### 실행 쿼리 보기 설정 – application.properties

```
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
코드복사
```

------

#### 테이블 자동 생성 설정 – application.properties

- 서버를 시작하는 시점에 DB 테이블을 drop후에 다시 생성하도록 설정하는 방법.

```
spring.jpa.hibernate.ddl-auto=create-drop
코드복사
```

- 서버를 시작하는 시점에 DB 테이블을 drop하지 않도록 설정하는 방법.

```
spring.jpa.hibernate.ddl-auto=validate
코드복사
```

------

#### h2 db console에 접근 설정 – application.properties

```
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
코드복사
```

------

#### User 클래스를 DB 테이블에 매핑

- User 클래스와 DB의 테이블에 매핑한다.
- DB 테이블은 별도로 생성할 필요없이 자동으로 생성된다.

```java
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GeneratedType;
import javax.persistence.Id;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false, length=20)
	private String userId;
	
	private String password;
	private String name;
	private String email;
	
	[...각 필드에 대한 setter, getter method...]
}
코드복사
```

- User 클래스를 @Entity로 설정한다.
- User 클래스에 대한 유일한 key 값을 @Id로 매핑한다.
- @Id에 @GeneratedValue 애노테이션을 추가하면 key가 자동으로 증가한다.
- 각 필드를 테이블 칼럼과 매핑할 때는 @Column 애노테이션을 사용한다.

------

#### 사용자 데이터에 대한 CRUD 구현

- CrudRepository 인터페이스를 상속하는 것만으로 CRUD 기본 기능을 구현할 수 있다.

```java
import org.springframework.data.jpa.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long>{
}
코드복사
```

------

## Controller에서 Repository 사용

- 사용자 데이터를 추가, 조회하기 위해 더 이상 ArrayList를 사용하지 않는다.
- @Autowired 애노테이션을 활용해 UserRepository를 추가한다.

#### 사용자 데이터 추가

- UserRepository의 save() 메소드를 통해 DB에 사용자 데이터를 추가한다.

```java
@Controller
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @PostMapping("")
  public String create(User user) {
    System.out.println("user : " + user);
    userRepository.save(user);
    return "redirect:/users";
  }
}
코드복사
```

------

#### 사용자 목록 조회

- UserRepository의 findAll() 메소드를 통해 DB에 저장되어 있는 전체 사용자 목록을 조회한다.

```java
@Controller
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserRepository userRepository;

  [... 사용자 추가 코드 ...]

  @GetMapping("")
  public String list(Model model) {
    model.addAttribute("users", userRepository.findAll());
    return "/user/list";
  }
}
코드복사
```

------

#### 사용자 조회

- 사용자 목록을 출력하는 곳에서 {{@index}}를 사용하는 대신 {{id}}를 사용한다.
- UserRepository의 findOne() 메소드를 통해 DB에 저장되어 있는 사용자 데이터 조회한다.

```java
@Controller
@RequestMapping("/users")
public class UserController {
  @Autowired
  private UserRepository userRepository;

  @GetMapping("/{id}")
  public ModelAndView show(@PathVariable long id) {
    ModelAndView mav = new ModelAndView("user/profile");
    mav.addObject("user", userRepository.findById(id).get());
    return mav;
  }
}
코드복사
```

------

# 실습 - 질문 데이터를 DB에 저장 및 조회

## 질문 데이터 저장하기

- @Entity를 활용해 Question 클래스를 DB 테이블과 매핑한다.
- @Id, @GeneratedValue를 활용해 Question의 key를 생성한다.
- @Column을 활용해 각 필드를 테이블의 칼럼과 매핑한다.
- QuestionRepostory를 생성한다. QuestionRepository는 CrudRepository를 extends한다.
- QuestionController에서 QuestionRepository를 @Autowired를 활용해 의존관계를 설정한다.
- QuestionRepository의 save() 메소드를 이용해 Question 데이터를 DB에 저장한다.

------

## 질문 목록 구현하기

- QuestionRepository의 findAll() 메소드를 활용해 전체 질문 목록 데이터를 조회한다.

------

## 질문 상세보기 구현하기

- QuestionRepository의 findOne() 메소드를 활용한다.

------

# 회원정보 수정

## 요구사항

> 회원 목록에서 회원가입한 사용자의 정보를 수정할 수 있어야 한다.
> 비밀번호, 이름, 이메일만 수정할 수 있으며, 사용자 아이디는 수정할 수 없다.
> 비밀번호가 일치하는 경우에만 수정 가능하다.

#### 힌트 1 - 회원정보 수정 화면 기능 구현

- 회원가입한 사용자 정보를 수정할 수 있는 수정 화면과 사용자가 수정한 값을 업데이트할 수 있는 기능을 나누어 개발해야 한다.
- 사용자 정보를 수정하는 화면 구현 과정은 다음과 같다.

![user_update_form.PNG](https://firebasestorage.googleapis.com/v0/b/nextstep-real.appspot.com/o/lesson-attachments%2F-KpD8DNo7tp5Vhx9vg-9%2Fuser_update_form.PNG?alt=media&token=b5ddc6a0-d050-430e-bade-bf2a4f8d5672)

- /user/form.html 파일을 /user/updateForm.html로 복사한 후 수정화면을 생성한다.
- URL 매핑을 할 때 "/users/{id}/form"와 같이 URL을 통해 인자를 전달하는 경우 @PathVariable 애노테이션을 활용해 인자 값을 얻을 수 있다.
- public String updateForm(@PathVariable String id)와 같이 구현 가능하다.
- Controller에서 전달한 값을 입력 폼에서 출력하려면 value를 사용하면 된다.
- ``

#### 힌트 2 - 회원정보 수정

- 사용자 정보를 최종적으로 수정하는 과정은 다음과 같다.

![user_update.PNG](https://firebasestorage.googleapis.com/v0/b/nextstep-real.appspot.com/o/lesson-attachments%2F-KpD8DNo7tp5Vhx9vg-9%2Fuser_update.PNG?alt=media&token=7b0408f8-c670-4c97-88c9-6440d9edef0a)

- URL 매핑을 할 때 "/users/{id}"와 같이 URL을 통해 인자를 전달하는 경우 @PathVariable 애노테이션을 활용해 인자 값을 얻을 수 있다.
- UserController의 사용자가 수정한 정보를 User 클래스에 저장한다.
- {id}에 해당하는 User를 DB에서 조회한다(UserRepository의 findOne()).
- DB에서 조회한 User 데이터를 새로 입력받은 데이터로 업데이트한다.
- UserRepository의 save() 메소드를 사용해 업데이트한다.

**POST 대신 PUT method를 사용하는 방법**

- 수정 화면(updateForm.html)에서 수정된 값을 전송할 때 PUT값을 다음과 같이 hidden으로 전송한다.
  - ``
- 위와 같이 `_method`로 PUT을 전달하면 UserController에서는 @PutMapping으로 URL을 매핑할 수 있다.