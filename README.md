# Step2

## 배포 URL
[배포 URL](https://hyunjun.herokuapp.com)

## 자가피드백 
- redirect, GET, POST의 동작 원리를 이해하고 미션 구현하니 훨씬 수월했다. 
- PutMapping 기능 구현이 어려워서 조금 더 공부가 필요하다. 
- DB를 활용해 데이터를 관리하니 훨씬 실제 웹이랑 비슷한 느낌을 받았다.  

## DB 설치 및 연결
- H2 및 JPA 의존성 주입.
### 어려움
- DB 연결 URL을 정확하게 입력안하니 연결이 안됐다. 

## User, Question과 DB 연결 후 테이블 생성 
### 어려움 
- domain 패키지를 qna 패키지의 바깥으로 설정하니 테이블 생성이 안됐다.

## UserRepository, QuestionRepository 생성 후 DB에 CRUD 준비
### 어려움 
- id의 타입을 int에서 Long으로 변경했는데 UserRepository<User, int>를 변경 안해서 오류 발생. 


