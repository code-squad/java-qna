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
