
### Heroku

- https://wooody92.herokuapp.com/

### 0. 구현 기능

- 답변 추가하기 (ajax, json 활용)
  - Template 생성 및 append 메서드를 통해 오름차순으로 정렬
- 답변 삭제하기 (ajax, json 활용)
  - answer 객체의 boolean deleted 값을 받아 이벤트 발생시킨 템플릿 삭제
- 답변 수정하기
  - 수정하기 버튼 클릭 시, 서버로부터 json data 정상적으로 받아오는 것을 확인했다. 코드 작성하면서 생각해보니 댓글 수정기능의 경우는 댓글 수정폼이라는 새페이지로 로드해서 수정하는 방식이어서 ModelAndView 이용하여 처리했다.
- @JsonIgnore 사용하여 양방향 연관관계 무한참조 에러제거
- 로깅 라이브러리 log4j2 -> logback 변경

### 1. 회고

- Ajax, jQuery, XHR 등 처음 접해서 개념을 학습했다.

  https://github.com/wooody92/notes/tree/master/spring/study

- 프로젝트 진행하며 실수 혹은 무지로 인해 삽질한 내용을 정리했다.

  https://github.com/wooody92/notes/tree/master/spring/삽질노트

- GitHub 블로그를 만들어 정리해나가면 좋을 것 같다고 생각했다.