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
    
   
  GetMapping
  - 특정 파일을 호출할 때 Get 방식을 사용한다.
  
 WelcomeController 구현 사항 
 - 주소창을 통해 유저로부터 데이터를 받을 수 있다.
   e.g. localhost:8080/firstpage?menu=chicken
 - 이렇게 받아온 데이터 값을 모델로 추가해 준다. model.addAttribute("menu", menu)
 - 그러면 view 를 담당하는 firstPage.html 에 그 데이터 값을 전달할 수 있다.
 - {{menu}} 라고 작성할 때 chicken 이 출력 됨.