# Step2

## 배포 URL
[배포 URL](https://hyunjun.herokuapp.com)

## 자가피드백 
- redirect, GET, POST의 동작 원리를 이해할 수 있었다. 
-  

- 유저 회원가입, 질문하기, 회원정보 수정 페이지에 적용.
	
	- User, Question와 DB 연결해서 테이블 생성
	- UserRepository, QuestionRepository 생성 후 DB에 CRUD 준비
	-  

## DB 설치 및 연결
- H2 및 JPA 의존성 주입.
### 어려움
- DB 연결 URL을 정확하게 입력안하니 연결이 안됐다. 

## User, Question과 DB 연결 후 테이블 생성 
### 어려움 
- domain 패키지를 qna 패키지의 바깥으로 설정하니 테이블 생성이 안됐다.
