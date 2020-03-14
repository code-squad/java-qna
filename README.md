# step4. 객체-관계 매핑

## comment 사항

- [x] CheckedException을 지양하고 UnCheckedException (예외처리 참고: https://www.slipp.net/questions/350)
- [ ] 로그인 여부 체크하는 기능이 누락됨. 로그인 된 유저들만 호출할 수 있는 api라 생각이 되어도 서버에서는 꼼꼼하게 체크해 주는것이 기본! (QuestionController에서 로그인 유저 검사할 것.)
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

그대로 equals를 사용하면 해당 주소만 비교. 객체값을 비교하려면 hashCode()오버라이딩이 필요하다.

## 4.5 답변 추가 및 목록 기능 구현



## 4.6 QuestionController 중복 코드 제거 리팩토링



## 4.7 답변 추가하기

















