# 스프링 부트로 QA 게시판 구현1

## 배포

https://jay-simple-qna.herokuapp.com/



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

  + ​	CRUD 기능을 제공하는 인터페이스

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