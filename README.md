# 스프링 부트로 QA 게시판 구현1

## 배포

https://jay-simple-qna.herokuapp.com/



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
