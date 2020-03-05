# 스프링 부트로 QA 게시판 구현1

## 배포

https://jay-simple-qna.herokuapp.com/

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
