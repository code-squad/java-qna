Spring mvc 란?
--
 - model, view, controller 의 약자
 - model은 데이터로 애플리케이션의 정보를 나타냄
 - view 는 사용자에게 보여지는 화면을 의미
 - controller 는 생성한 모델과 뷰 간의 동작을 조정
 
 
 Controller 관련 대표적인 annotation 
 -
 
 1. @Controller
    - 해당 클래스를 Controller로 사용한다고 Spring 에 알린다.
    - 빈으로 등록한다.
    - static 이 아니라 templates 디렉토리 밑에 있는 파일을 호출한다.
       
 2. @RequestMapping
    - value에는 url, method는 get, post 와 같은 http 프로토콜 방식을 쓴다.
    - 클라이언트 url 로 서버에 요청을 하면, 해당 url 을 매핑하고 있는 메소드 해당 요청을 처리하고 응답해준다.
    
   
  GetMapping과 PostMapping 의 차이
  -
  - 특정 파일을 호출할 때 Get 방식을 사용한다.
  
 WelcomeController 구현하며 배운 것
 -
 - 주소창을 통해 유저로부터 데이터를 받을 수 있다.
   e.g. localhost:8080/firstpage?menu=chicken
 - 이렇게 받아온 데이터 값을 모델로 추가해 준다. model.addAttribute("menu", menu)
 - 그러면 view 를 담당하는 firstPage.html 에 그 데이터 값을 전달할 수 있다.
 - {{menu}} 라고 작성할 때 chicken 이 출력 됨.
 - url 이 추가되면 상응하는 controller가 있어야함.
 - html 에서 작업할 때 input 태그에 name 이라는 값으로 데이터를 설정해야만 입력받 데이터를 전달 가능
 
 
 클라이언트로부터 받는 데이터가 많을 때 
 - 
       
    회원가입을 할 때 사용자가 입력하는 데이터가 id, name, email, password 로 적지 않다.
    경우에 따라 입력받는 데이터가 더 많아질 수도 있는데,
    이럴 때는 모든 데이터를 인자로 넘겨주면 메소드의 길이가 너무 길어지니 
    새로운 클래스를 하나 만들어서 인자의 개수를 줄이는 것이 좋다.
    e.g. User 클래스를 만들어서 field 값을 id, password, name, email 을 
    설정하고 setter 메소드를 쓰면 됨.
    이럴때 인자로 User user 만 넘겨주면 간단히 해결 가능
    (정보 읽기 위해서 toString 메소드도 사용해함)
    **그리고 html페이지에서 그 정보를 불러오기위해서는 getter 메소드를 만들어야함.
    
 
 
 h2 database 와 jpa 추가
 -
 - https://mvnrepository.com/ 에서 h2 와 jpa 검색
    + build.gradle 파일 compile group: 'com.h2database', name: 'h2', version: '1.4.197'에 추가
    + compile group: 'org.springframework.boot', name: 'spring-boot-starter-data-jpa', version: '2.2.4.RELEASE'
 도 추가.
 
 - application.properties 에 아래 사항 추가
    + spring.datasource.url=jdbc:h2:~/ask-jenny;DB_CLOSE_ON_EXIT=FALSE (url을 자유롭게 지정)
        + home  밑에 지정한 주소의 이름으로 데이터베이스 파일이 생성된다 e.g. ask-jenny.mv (터미널에서 cd ~ 하면 홈으로 이동)
    + spring.h2.console.enabled=true
    + spring.h2.console.path=/h2-console
    
    
데이터베이스에 데이트를 추가하거나 조회하려면 interface가 필요함. 주로 Repository 나 dao 라는 접미사를 사용함.
e.g. UserRepository<User, Long>
JpaRepository를 extends 하고 어떤 클래스에 관한 레파지토리인지, 그리고 그 클래스의 primary key 의 타입이 무엇인지
명시해주어야함. 
이렇게만 구현해주어도 데이터 삽입 및 조회가 가능함.


기타 애노테이션 정리
-
1. @Entity
   - 데이터베이스와 연결하기 위해 사용 e.g. 
   - DB 각각의 데이터들을 고유하게 식별하기 위해 primary key 라는 것이 존재. primary key를 지정야함. 

2. @Id
   - primary key를 지정하는 애노테이션해

3. @GeneratedValue
   - 하나의 데이터가 추가될때마다 자동으로 1씩 증가하게 하는 애노테이션 : @GeneratedValue

4. @Column(nullable = false)
   - null값이 들어갈 수 없게 지정하는 애노테이션
   
5. @Autowired

6. @RequestMapping
   - 기본으로 있다고 침. e.g.  @RequestMapping("/users") 이면 @GetMapping("/list) 일 때
   사실은 users/list 인 것임.
   
7. @PathVariable
   - url로 데이터를 넘겨줄 때 

Repository
--
- repositoryname.findAll()  하면 레파지토리안의 모든 정보 가져옴.
- repositoryname.save(save하고싶은정보) 하면 레파지토리에 저장됨.


중복되는 html 생략하기
-
- html은 정적이라서 생략할 수 없지 mustache와 같은 템플릿 엔진을 활용하면 중복을 줄일 수 있음.
- header 와 footer 로 나누어주면 편함. e.g. {{> include/header }}, {{> include/footer}} 


repositoryname.findById(id);



해결해야하는 사항
-
- 회원 정보 수정 버튼 눌렀을 때 value 값이 전달이 되지 않아서 모두 빈 칸이다.
   -> 해결  model.addAttribute("user", userRepository.findById(givenNumber).get());
   끝에 get() 을 꼭 해주어야 데이터를 모델에 넘겨줄 수 있음. 아니면 Object가 넘어가는듯 함.
   
   
회원 정보 수정 
-
- User 클래스에 update 메소드를 만들어서 바뀐 유저 정보를 업데이트 해줌.
- UserRepository에 바뀐 정보를 save 해줌.

put 사용하기 위해 추가해야할 사항
- application.properties 에 spring.mvc.hiddenmethod.filter.enabled=true 추가


로그인 시
-
- 로그인할 때 유저로부터 아이디와 패스워드를 받는다. 지금까지는 userRepository.findById(givenNumber) 해서
long 타입의 givenNumber 로 유저 레파지토리에서 유저 정보를 조회했는데, 이제는 givenNumber 를 알지 못하고
userId와 password만 아니까 userId 로 유저 정보를 조회하는 방법이 필요하다.
이럴 때는 간단하게 조회하려는 레파지토리에서 findBy찾는기준 메소드를 만들어 주면 된다.(이름만 정의해놓으면 스프링부트가 알아서 만들어주는듯) 
e.g. User findByUserId(String userId) , findBy하고 뒤에 찾는 기준 값으 대문자로 오면 되는듯 하다.
'


미리 db에 정보 입력해놓기
-
- 매번 회원가입하고 로그인 해보기가 귀찮으므로 미리 정보를 입력해두자.
- resources 디렉토리 아래에 data.sql 파일을 만들고 
INSERT INTO USER (user_id, password, name, email) VALUES 
('huji', '3156', 'Jenny', 'huji3156@gmail.com');
를 입력하면 된다.(no data sources are cofigured.. 같은 메세지는 무시해도 상관없는 듯 하다)
- *이 때 만약 Null not allowed for column~~ 에러 메세지가 뜬다면 이는 @GeneratedValue 애노테이션에
    @GeneratedValue(strategy = GenerationType.IDENTITY)
을 추가해주면 해결된다.


로그인 전 후, 네비게이션 바 구성 다르게 하기
-
- 로그인 전이라면 네비바에 회원가입, 로그인 창만 뜨고 로그인 후에는 로그아웃, 개인정보 수정 창이 뜨게 하기
- session.setAttribute("user", user) 하고
- application.properties 에 handlebars.expose-session-attributes=true 추가
- 엥 로그아웃 기능 구현하니까 갑자기 됨... 왜지 모르겠다...


html 네비 바 눌렀을 때 메뉴 밑에 파란 라인 동적으로 active 하는 법?
-
