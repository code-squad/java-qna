# 질문답변 게시판
## heroku url
- https://java-qna-1.herokuapp.com/

## 반영한 피드백
- 예외처리!!!!
- input 값에 대한 검증을 반드시 할 것!

> 공통 피드백
- optional에서 get() 쓰지 않기
- :exclamation: 이건 반영하지 못했다
- null이 아닌거 확인하고나서도 get()을 쓰면 안되는걸까?

> 공부해볼 것
- Exception vs RuntimeException [참고자료](http://blog.naver.com/PostView.nhn?blogId=serverwizard&logNo=220789097495)
   1. Exception
      컴파일링 과정에서 잡히는 오류들! 반드시 예외처리 해야함
   2. RuntimeException
      개발자의 실수로 발생할 수 있는 오류들 (IndexOutOfBoundsException)

)

## 구현한 기능
- 로그인
- 로그인 상태에 따른 메뉴처리
- 로그아웃(Post로)
- 본인 개인정보만 수정
- User와 Question 연결
- Answer 구현하기
    1. 등록
    2. 수정
    3. 삭제

> 어려웠던 점
1. 중복코드가 굉장히 많이 생겼는데 이걸 빼낼 방법을 모르겠다 :cry:
2. 하고싶은걸 다 넣어보려고 하다보니 좋은 방법으로 하고있는건지 검증할 방법이 없어졌다
