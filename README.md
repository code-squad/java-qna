# 질문답변 게시판
## 진행 방법
* 질문답변 게시판에 대한 html template은 src/main/resources 디렉토리의 static에서 확인할 수 있다. html template을 통해 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 코드스쿼드의 온라인 코드 리뷰 과정](https://github.com/code-squad/codesquad-docs/blob/master/codereview/README.md)
* [동영상으로 살펴보는 코드스쿼드의 온라인 코드 리뷰 과정](https://youtu.be/a5c9ku-_fok)

---

# STEP1

## 요구사항

- 회원가입, 사용자 목록 기능 구현
- 질문하기, 질문 목록 기능 구현

## 진행과정

1. handlebar template-engine과 Spring Boot가 정상적으로 동작하는지 확인하기 위하여, 간단한 Application을 만들었습니다.
2. Spring Tutorial에 따라서 devtools 의존성을 추가하였습니다.
3. Live Reload시 static 파일도 같이 reload되도록 하기 위하여 `application.properties`를 수정하였습니다.
4. User Controller를 추가하고, /users/form 요청이 들어오면 회원가입 창이 출력되도록 하였습니다.
5. User Controller에 생성하는 기능 추가하기
6. User들의 정보를 출력하는 기능 추가
7. User의 정보를 출력하는 기능 추가
8. 공통 부분을 Template으로 만들어서 추출하기
9. 비슷한 화면인 질문 화면을 동적페이지로 만들기
10. Question의 목록을 출력하는 화면 만들기
