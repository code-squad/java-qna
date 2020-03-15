# step4. 객체-관계 매핑

## comment 사항

- [x] CheckedException을 지양하고 UnCheckedException (예외처리 참고: https://www.slipp.net/questions/350)
- [x] 로그인 여부 체크하는 기능이 누락됨. 로그인 된 유저들만 호출할 수 있는 api라 생각이 되어도 서버에서는 꼼꼼하게 체크해 주는것이 기본! (QuestionController에서 로그인 유저 검사할 것.)
- [x] CrudRepository <-(상속) JpaRepository 차후에 페이징 기능을 사용하기 때문에 JpaRepository로 하자.
- [x] Question.java에서 super()제거
- [x] 메서드(createQna -> createQuestion)으로 변경하기
- [x] findById는 Optional로 변환하여 null체크하기.
- [x] ModelAndView, Model 둘 중에 하나만 사용하기. 일관성있게 작성하자.

---

## 4.1 회원과 질문간의 관계 매핑 및 생성일 추가

- User와 Question 간의 관계를 매핑한다. User는 너무 많은 곳에 사용되기 때문에 User에서 관계를 매핑하기 보다는 Question에서 @ManyToOne 관계를 매핑하고 있다.
- Question에 생성일을 추가한다.



## 4.2 질문 상세보기 기능

- getOne이 아닌 왜 findById를 쓰는가?

Optional로 처리한다면 null값 처리하기가 편하다.

내가 처리한 exception이 Runtime(Unchecked)인지 아닌지를 꼭 구별하여 checkedException을 지양하자.

## 4.3 질문 수정, 삭제 기능 구현

Step3에서 구현완료. String.format이 느리다고 한다. StringBuilder나 StringBuffer로 바꿔보도록 해보자.

## 4.4 수정/삭제 기능에 대한 보안 처리 및 LocalDateTime설정

@Converter를 이용한 LocalDateTime설정.

```java
@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime localDateTime) {
        return localDateTime != null ? Timestamp.valueOf(localDateTime) : null;
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp timestamp) {
        return timestamp != null ? timestamp.toLocalDateTime() : null;
    }
}
```

그대로 equals를 사용하면 해당 주소만 비교. 객체값을 비교하려면 hashCode()오버라이딩이 필요하다.

## 4.5 답변 추가 및 목록 기능 구현

### 요구사항

- [x] 사용자는 질문 상세보기 화면에서 답변 목록을 볼 수 있다.
- [x] 로그인한 사용자는 답변을 추가할 수 있다.
- [ ] 자신이 쓴 답변을 삭제할 수 있다.

---

@Lob: 글자수제한 커짐

구현하던 와중에 빌드 에러가 났음.

원인: AnswerRepository에서 잘못된 Annotaion사용이었음.

```java
org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'entityManagerFactory' defined in class path resource [org/springframework/boot/autoconfigure/orm/jpa/HibernateJpaConfiguration.class]: Invocation of init method failed; nested exception is org.hibernate.AnnotationException: Could not extract type parameter information from AttributeConverter implementation [com.codessquad.qna.answer.AnswerController]
```

```java
org.springframework.beans.factory.BeanCreationException.
    org.hibernate.AnnotationException
    Execution failed for task ':test'.
> There were failing tests. See the report at: file:///home/sosah/documents/java-qna/build/reports/tests/test/index.html

```

템플릿 사용시 변수명 제대로 확인할 것. 대문자인지 아닌지 꼭 확인할 것. (괜한 삽질함)

@OneToMany(mappedBy="변수명") <=> @ManyToOne

@OrderBy("id ASC"): 오름차순

getter가 없으면 답변이 뜨지 않음. getter를 넣자.



## 4.6 답변 삭제하기

현재 show.html에서 question과 answer과 얽혀있어 삭제하기가 작동이 잘 되지 않는다.

안뜨면 뭐다? getter문제다. 

## 4.7 QuestionController 중복 코드 제거 리팩토링

현재 JPA, 하이버네이트를 통해 DB Connection한다.

![jpa](https://suhwan.dev/images/jpa_hibernate_repository/overall_design.png)



- 복잡한 SQL을 사용하기엔 적합하지 않고 러닝커브가 너무 낮다. 배우는데 오래걸림.

- 프로젝트 시작할 땐 Spring DATA JDBC 사용할 예정이다. 이런게 있다는것만 기억하자.





