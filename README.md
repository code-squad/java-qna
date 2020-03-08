# Step3 

## Step3 진행하며 배운 것 
- 자바지기 영상보니 모르면 검색하는 게 일상. 두려워하지 않고 검색한다. 
- Bad Request일 경우 mapping URL에 @RequestMapping에 해당하는 url이 있나 확인 ex)/users/{{id}}
- 사용자일 땐 몰랐던 기능들이 로그인 하나로 복잡해질 수 있음을 깨달음. 
- getter, setter로 객체의 속성에 직접 접근하지 말고 메세지를 보내서 자율성을 높이는 중요성을 깨달음. 과거 호눅스가 getter가 적으면 좋다는 피드백을 했는데 이해가 된다. 
- @ManyToOne은 Many에 해당하는 객체에 매핑을 한다.

## 해결 못한 것 
- auto reload 기능 : 코드 변경하면 서버 재시작하지 않고 바로 반영하게 하고 싶은데 검색대로 했는데 기능 동작안함. 

## 로그인 기능 구현
### 어려운 점 
- 보안을 위해 로그인한 사용자인지 매번 확인하는 로직이 처음엔 복잡했다. 

## 중복제거 및 리팩토링 
- user의 속성을 getter로 가져오지 않고 메세지를 보내서 자율적으로 구현하는 것이 객체 지향 프로그래밍이라는 걸 다시 느낌 ex) user.notMatchPassword(password)   
### 어려운 점
- 반복되는 로그인된 유저 확인 로직을 메서드로 분리하고 싶었는데 하지 못함. 다른 클래스에서 redirect하면 실행이 안됐다. 더 공부가 필요하다.

## 질문 기능 구현
### 어려움 
- 질문 수정 및 삭제 시 작성한 사용자만 가능한 코드를 작성이 어려웠다. 
- Writer 1명 당 question은 여러개를 작성할 수 있다는 건 당연한 건데 Join으로 매핑된다는 점을 이해하는 데 시간이 걸림.

## Answer 구현
### 어려움
- 답변하기 버튼의 type을 button이 아니라 submit으로 해야 반응.
- Answer의 getId를 작성안해서 오랜시간 view에서 answer의 id가 못 넘어오는 이유를 파악하지 못함. 사소한 문제지만 반복되지 않게 기록!! 


# step3 진행과정 및 어려웠던 점.
## step2 추가 학습 
- Controller에서 View로 모델로 데이터를 한 개만 넘기는 상황. 그 때 view에서 해당 model 데이터를 사용하려면 코드를 {{#user}} ... {{/user}}로 감싸야 한다. 기존에는 {{user.name}} 이렇게 접근함. 
- 개인정보 수정 시 user 객체에 update 메서드 추가한다. update에서 개인정보 수정하고 DB에 넣기. 훨씬 코드가 간결해짐. 

## 자가 피드백 
- 자바지기 영상보니 모르면 검색하는 게 일상. 두려워하지 않고 검색한다. 
- Bad Request일 경우 mapping URL이 "/users/login"처럼 @RequestMapping(/users)이 붙였는 지 확인.

## 전체적 어려움 
- 코드 수정하면 자동으로 웹페이지에 적용이 되야 하는데 안된다. 매번 서버를 재시작해야 한다. 

## 로그인 기능 구현
- 메인에서 로그인 버튼 누르면 /users/login으로 이동, 관리하는 login Controller 생성
- 로그인 창에서 전달된 아이디, 패스워드를 처리하는 Controller 생성, 
    - userId로 DB에서 데이터를 가져온다. UserRepository에 findByUserId() 생성
    - 만약, user==null이거나 패스워드가 틀리면 다시 로그인 페이지 로드 return "/login"
    - 아이디, 비밀번호 인증 성공 시 HttpSesstion에 user 저장하고 return "/"; 

## 로그인 상태에 따른 메뉴 처리 및 로그아웃 기능 구현
- import.sql 파일을 통해 회원 데이터 초기화. 위치는 resources 패키지 아래에.
    - INSERT INTO USER (COLUMS) VALUES (VALUE, ...)
- 로그아웃 상태면 로그인, 회원가입 메뉴만, 로그인 상태면 로그아웃, 개인정보수정 메뉴만 나오게 하기
    - mustache 문법 중 if - else와 비슷한 문법 사용. {{^user}} , {{#user}} .. 
    - handlebars.expose-session-attributes=true를 통해 session 값을 View에 전달하는 속성 설정
- 로그아웃 시 session에 저장된 user 제거한다. 제거하면 mustache를 세션에서 user가 안 넘어오니 로그인, 회원가입 메뉴만 남긴다.

## 로그인 시 개인정보 수정 기능 구현 
- 개인정보 수정 버튼 누르면 /users/{{id}}/form으로 이동. session된 user의 id를 Controller에 넘기기. 세션과 모델에 담긴 데이터의 이름을 다르게 해야 에러가 없다.
- 개인정보수정 url을 누구나 접근하고 수정할 수 있는 상황이니 로그인한 상태에만 수정할 수 있도록 기능을 추가. sesstion에서 넘어오는 데이터값은 Object이다. if (user == null)
- 로그인 상태면 다른 사용자의 개인정보수정을 막는 기능 필요 if (user.NotMatchUser(user))
   
## 중복제거 및 리팩토링 
- Utils를 만들어서 하드코딩된 상수이름을 지정한다. 
- user의 getPassword()메서드로 값을 가져오지 말고 user에게 메세지를 보내는 방식으로 구현하는 것이 객체 지향 프로그래밍이다. 
- getter, setter는 객체의 데이터에 직접 접근하는 행위이다. 이보다 객체에게 메세지를 보내는 방식으로 책임을 수행하는 것이 중요하다. getter를 없애는 것이 좋겠다는 피드백을 이제야 이해하고 있다.  
- 실행 쿼리를 볼 수 있는 속성 설정. 

## 질문하기 기능 구현
- 로그인이 된 상태에만 질문하기 폼으로 이동 가능. 로그인 안되어 있으면 로그인 폼으로 이동. if (user == null) ~~ 
- 로그인 상태에서 질문하니까 글쓴이를 지운다. 
- HttpSessionUtils에 getUserFromSession(Session) 메서드 추가.
- Question에 writer, title, contents를 생성할 생성자메서드 추가. 이 때, 기본 생성자도 함께 추가해야 한다.
 
## 질문 목록 구현 
### 어려움
- index를 관리하는 Controller를 QuestionController에 두니 @RequestMapping 사용 시 index Controller에 적용이 안되서 @RequestMapping를 사용 안함. 하지만 HomeController 생성으로 index Controller를 따로 관리하니 구현 완료 

## 질문 수정 구현
### 어려움 
- input 폼에 값을 보이게 하려면 value="{{title}}"을 해야 한다. 
- matchId(), matchPassword를 구현했는데 inverted가 필요하다는 에러가 발생. 결과를 보니 return에 !를 붙임. 왜그러지??? 

## 질문 삭제하기 구현 
- 삭제할 때 writer가 맞아야 삭제할 수 있게 구현. 아니라면 로그인 페이지로 이동. User에 NotMatchWriter() 구현
- writer가 맞을 때 가져올 question은 id로 찾는다. 한 명의 writer가 여러개의 질문을 쓸 수 있기 때문이다. -> writer로 DB에서 찾으면 쿼리 결과가 2개라는 에러 발생. 

## User와 Question 연결 실습
### 어려움
- ManyToOne의 의미 파악하는데 어려웠음. Many : question, One : User이다. 유저 한 명은 여러개의 질문을 남길 수 있기 때문.
- Question의 Writer 속성이 User가 됐기 때문에 question에서 userId를 가져오려면 writer.userId로 변경이 필요. 

## Answer 구현
- 답변하기 클릭하면 입력한 답변을 처리하는 Controller 생성. PostMapping. 
- Controller에 넘어가는 데이터 : 로그인한 userId, 답변한 내용
- Answer 객체의 writer에 로그인한 userId, contents에 답변한 내용을 넣은 뒤 show view를 redirect한다. (model로 넘길 때는 view를 호출하는 controller에서 넘겨야 한다.)

### 수정, 삭제 기능 구현 
- 수정 버튼 클릭 시 답변을 입력한 사용자인지 확인하고 입력한 답변을 폼에 넣는다. 
- 수정할 답변을 입력하고 '수정'버튼 누르면 로그인 됐는지 확인, 로그인한 사람과 답변글 쓴 사람이 동일한지 확인, Answer 객체에 update(String contents) 추가. answerRepository.save(answer) 

### 어려움
- answer 안에 Question을 넣어놨는데 question.id로 못가져오는 이유를 모르겠다. -> Answer 객체를 생성할 때 Question을 안넣었기 때문.
- 답변 여러개를 show에 보내면 contents는 잘 생성하는데, answer의 id가 아니라 question의 id를 가져오는 이유는 getter가 없기 때문.
- 질문 상세보기에서 답변은 해당 질문 id와 매칭되는 답변만 보여줘야함. findByQuestionId()로 찾아야함.
```java
        List<Answer> answers = answerRepository.findByQuestionId(id).orElse(null);
        System.out.println("answers : " + answers);
        if (answers == null) {
            return "question/show";
        }
        model.addAttribute("answers", answers); // 여기서 다시 answerRepository에서 찾으면 null값이 들어가서 answers로 넘겨줘야 함. 이유는 모르겠다.
```

- 반복 작업 줄이기 위해 question 객체 하나를 Insert 한다.
- IllegalArgumentException 뜨면 객체에 setter, getter 잘 만들어졌나 확인!
- model을 넘길 때는 redirect가 아닌 view를 호출하는 곳에서 넘겨야 한다. 

## step3 리팩토링
- 템플릿 내 불필요한 링크들 올바르게 고치기. 
- QuestionController한테 단일 책임 주기 : question과 관련된 작업을 한다. 
- AnswerController 생성 : answer와 관련된 작업을 한다. 
- 중복되는 코드를 메서드로 만들어서 유지보수한다. 
    - user 세션을 확인하는 코드를 메서드로 분리했는데 redirect가 실행안됨.
    - findAnswer(answerRepository, id); 생성
    - findQuestion도 생성 가능 -> static으로 해서 다른 클래스에서도 사용 가능하게.  
- step3 P.R 후 reload 해결하기.

## 메소드 컨벤션 
- form : 데이터 입력받는 컨트롤러
- questions : 데이터의 리스트를 보여주는 컨트롤러 
- detailPage : 데이터의 상세페이지를 보여주는 컨트롤러 
- updateForm : 수정하는 양식을 보여주는 컨트롤러 
- update : 입력받은 데이터로 수정하는 컨트롤러 
- delete : 입력된 데이터를 삭제하는 컨트롤러

## 질문 
- 로그인 상태 확인하는 메서드가 중복되서 Utils에서 메서드를 만들어 중복제거 하려 했습니다. 그런데 Utils에서 redirect를 리턴하지만 동작하지 않는데 이유가 무엇인가요?
- show.html에 answers를 넘기는데 answer.id가 안잡히는데 원인을 모르겠습니다. 부모 객체 속성에 접근하려면 ../ 이나 @root로 접근한다는 데 이래도 해결이 안되네요. -> Answer에서 getId()가 없었네요..!!!  
