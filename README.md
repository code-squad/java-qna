# 질문답변 게시판

## Step1

## 기능 요구사항

* 회원 가입
* 사용자 조회 목록
* 개별 사용자 프로필 정보
* 질문하기
* 질문 상세 보기

## 프로그래밍 요구사항

* 배포는 heroku를 사용한다.
* 템플릿 엔진은 handlebars를 사용한다.
* 로깅 라이브러리는 log4j2를 사용한다.

## 구현 내용

* User 클래스와 UserController를 이용해 회원가입과 유저 목록을 구현
* Question 클래스와 QuestionController를 이용해 질문하기 구현

## heroku 배포

* [링크](https://java-qna-poogle.herokuapp.com/)

## 보충할 사항

* 회원정보 수정
* log4j2 사용
* HTML 중복제거 (URL과 html 쉽게 연결)
---
## Step2

## 기능 요구사항

* 데이터를 DB에 저장 및 조회
  * 회원 가입
  * 사용자 조회 목록
  * 개별 사용자 프로필 정보
  * 사용자 정보 수정
  * 질문하기
  * 질문 상세 보기
  * 질문 수정

## 프로그래밍 요구사항
* H2 DB 사용
* 배포는 heroku를 사용한다.
* 템플릿 엔진은 handlebars를 사용한다.
* 로깅 라이브러리는 log4j2를 사용한다.

## 구현 내용

* UserRepository에 사용자 정보 저장 및 조회
* QuestionRepository에 질문 정보 저장 및 조회
* 예외처리
* Rest Api url design 

## heroku 배포

* [링크](https://java-qna-poogle.herokuapp.com/)

## 보충할 사항

* log4j2 사용
* 질문 리스트와 상세 화면에서 작성자 클릭 시 프로필로 이동하는 방법
* 예외처리
* 비밀번호 일치하는 경우에만 수정할 수 있도록 구현
---

## Step3

## 기능 요구사항
* 로그인 기능 구현
* 테스트 데이터 추가
* 개인정보 수정
* 질문 기능 구현
  * 질문하기
  * 질문 수정하기
  * 질문 삭제하기
* User와 Question 연결
* Answer 구현하기

## 프로그래밍 요구사항
* DB: H2
* 배포: heroku
* 템플릿 엔진: handlebars
* 로깅 라이브러리: logback
  
## 구현 내용
* 예외처리를 통한 중복 제거
* UserController에 session으로 login 기능 구현
* User와 Question, Question과 Answer, Answer와 User 간의 관계 설정
* AnswerController에 댓글 작성, 수정, 삭제 기능

## heroku 배포
* [링크](https://java-qna-poogle.herokuapp.com/)


## 보충할 사항
* 아이디 중복체크
  * 현재는 sql 필드 속성을 unique로 설정해서 에러 페이지가 뜨는 상황
  * 회원가입 시 중복체크 할 수 있는 기능을 구현, 중복이 없을 때 회원가입 할 수 있게끔 하고 싶다.
* 시간 설정
  * 질문과 댓글 수정 후 현재 시간으로 시간이 수정되어야 하는데 변동이 없음
* 비밀번호 이중 확인
  * 개인정보 수정 시 비밀번호가 일치 하는 경우만 가능하도록 해야 함
  * 변경 전 비밀번호와 변경 후 비밀번호를 확인해야 함