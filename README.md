# 질문답변 게시판

## 반영한 피드백
- custom error를 만들어 중복제거

> 공통 피드백
- optional에서 get() 쓰지 않기

## 구현한 기능
- 질문/답변 삭제
   1. 자신이 작성한 답변만 삭제
   2. deleted 컬럼을 true로 변경하기
   3. 질문을 삭제하면 답변도 삭제하기(둘 다 deleted 값이 false->true)
   
### heroku url
- https://java-qna-1.herokuapp.com/
