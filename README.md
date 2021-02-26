## STEP6

- https://wooody92.herokuapp.com

> 함께한 팀원 : Jack
> 페어프로그래밍 방식 : Ground rule 논의 -> Step6의 페이지 기능 구현을 위해 학습 후 아이디어 공유 -> 함께 코드 작성 -> 세부적인 기능을 각자 수정 후 P.R

### 0. 구현 기능

- 동적으로 구동하는 페이지 나누기 기능 구현했습니다.
- 페이지를 각 양 끝으로 이동하면, 이전 혹은 다음 버튼을 보이지않게 제거하여 예상치못한 버그차단 했습니다.
- PageController와 PageUtils 클래스를 목적성에 맞게 분리했습니다.

### 1. 회고

- PageUtils 클래스를 만들어서 페이지 컨트롤러에서 import static 선언하여 사용하려고 했는데 하다보니 여러 문제가 생겼습니다. 주로 QuestionRepository를 가져다 쓰면서 발생한 문제였는데 그래서 궁금한점이 있습니다.

  1. @Autowired QuestionRepository는 static하게 사용하는 것은 바람직하지 않나요?

  2. 아래 삽질했던 링크처럼 클래스 생성자에서 QuestionRepository를 사용하고 싶은데, 생성자 수행전에 @Autowired 시키는 방법은 없을까요?

  3. 실제 작성한 코드에서는 빈을 제거하고, QuestionRepository를 매개변수로 하는 메서드들을 만들어 사용했는데 괜찮을까요?

     https://github.com/wooody92/notes/blob/master/spring/삽질노트/0319%20auto-wired%20before%20class%20constructor.md

