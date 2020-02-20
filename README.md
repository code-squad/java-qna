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

### 구현한 기능

1. 회원가입 - `JOIN US`
2. 회원 목록 보기 - `LIST`
3. 회원 프로필 정보보기 - `PROFILE` , `LIST`에서 `NAME`을 클릭해서 봐야 정보 확인 가능
4. HTML 중복 제거
5. 질문하기 - `QnA` > `질문하기`
6. 질문 목록 보기 - `QnA`
7. 질문 상세 보기 - `QnA` > `제목`



CSS 만들다가 지쳐서 질문하기 폼이... 디자인이 없어요ㅠㅠㅠㅠ



### 배포

[헤로쿠 배포 URL](https://shrouded-cove-08217.herokuapp.com)



### 업데이트 계획

* 회원정보 수정
* log4js 적용
* HTML 중복 제거 - URL과 html 연결하여 제거
* 질문하기 폼 디자인 수정

