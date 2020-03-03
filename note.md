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
    + spring.h2.console.enabled=true
    + spring.h2.console.path=/h2-console

기타 애노테이션 정리
-
1. @Entity
- 데이터베이스와 연결하기 위해 사용 e.g. 
- DB 각각의 데이터들을 고유하게 식별하기 위해 primary key 라는 것이 존재. primary key를 지정야함. 

2. @Id
-  primary key를 지정하는 애노테이션

2. @GeneratedValue
- 하나의 데이터가 추가될때마다 자동으로 1씩 증가하게 하는 애노테이션 : @GeneratedValue

@Column(nullable = false)
- null값이 들어갈 수 없게 지정하는 애노테이션