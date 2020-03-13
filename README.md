# 질문답변 게시판

### 반영한 피드백
1. ofNullable로 포장한 후에 단지 orElseThrow를 하는 정도라면 옵셔널을 쓰지 않고 그냥 if문으로 null 체크하는 것이 객체 생성 비용을 아끼기
2. session 검증을 먼저해서 불필요하게 쿼리를 날릴 일 없게 하기

### 구현한 기능
- fetch 사용
   1. 답변 생성
   2. 답변 삭제

- RestController 생성
   1. 답변 생성
   2. 답변 삭제
   
> 공부할 내용

1. JpaRepository vs CrudRepository
   - CrudRepository: 기본 crud 기능만 제공
   - JpaRepository:  JPA 관련 특화 기능들 (ex. flushing, 배치성 작업) + CrudRepository + PagingAndSortingRepository

2. JPA에서 save()
   - 엔티티의 @Id를 기준으로 해당 프로퍼티가 null이면 Transient 상태로 판단하고 id가 null이 아니면 Detached 상태로 판단
   
### heroku url
- https://java-qna-1.herokuapp.com/
