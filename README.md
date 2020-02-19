# 질문답변 게시판
## heroku url
- https://java-qna-1.herokuapp.com/

## 구현한 기능
- 회원 가입
- 사용자 조회
- 질문하기
- 질문 목록 보기

> 아쉬운 점

1. 추가기능인 회원정보 수정을 하고싶었는데 안됐다. 
2. put으로 넘겨주고싶었는데 form태그에서 지원하는게 get이랑 post 뿐이라고 했다.
3. 그래서 put으로 넘겨줄 수 있는 방법을 찾았는데 이게 안됐다
    ```html
    <form name="question" method="post" action="/question/{{id}}">
        <input type="hidden" name="_method" value="put"/>
    </form>
    ```
4. 답답해서 리뷰어분들 중 한 분껄 찾아봤는데 이거랑 똑같았다
5. 근데 난 안됐다
6. 화나서 일단 배포해버림
