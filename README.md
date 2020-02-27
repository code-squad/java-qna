# Step3 

## step2 추가 학습 
- Controller에서 View로 모델로 데이터를 한 개만 넘기는 상황. 그 때 view에서 해당 model 데이터를 사용하려면 코드를 {{#user}} ... {{/user}}로 감싸야 한다. 기존에는 {{user.name}} 이렇게 접근함. 
- 개인정보 수정 시 user 객체에 update 메서드 추가한다. update에서 개인정보 수정하고 DB에 넣기. 훨씬 코드가 간결해짐. 

## 전체적 어려움 
- 코드 수정하면 자동으로 웹페이지에 적용이 되야 하는데 안된다. 매번 서버를 재시작해야 한다. 

## 로그인 기능 구현
- 메인에서 로그인 버튼 누르면 /users/login으로 이동, 관리하는 login Controller 생성
- 로그인 창에서 전달된 아이디, 패스워드를 처리하는 Controller 생성, 
    - userId로 DB에서 데이터를 가져온다. UserRepository에 findByUserId() 생성
    - 만약, user==null이거나 패스워드가 틀리면 다시 로그인 페이지 로드 return "/login"
    - 아이디, 비밀번호 인증 성공 시 HttpSesstion에 user 저장하고 return "/"; 
- 로그인 로직 처리 후 메인 페이지로 이동.
