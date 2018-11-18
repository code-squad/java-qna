//package codesquad;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//
//@Controller
//public class MainController {
//    @GetMapping("/")
//    public String index() {
//        return "forward:/question/list";
//    }
//}

///메인 화면의 기본 url을 유지하면서, 질문 리스트를 보여주도록하는 작업
///mainController 클래스를 굳이 만들 필요가 있을까?? -->Configuration 기능을 쓰는게 깔끔하다고 생각한다.