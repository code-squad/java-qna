package codesquad.practice;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

// Controller 역할 부여를 위한 어노테이션 //
@Controller
public class WelcomeController {

    private List<User> users = new ArrayList<>();

    // 특정 주소로 요청이되었을 때, 아래 메소드를 호출할 수 있도록 설정 //
    //  예) http://localhost:8080/helloworld 로 호출할 때, 이 메소드를 호출//
    @GetMapping("/helloworld")
    public String welcome(String name, int age, Model model) {
        // html 을 동적으로 생성해서 사용자에게 보여주는 것이 템플릿 엔진이고, 대표적인 예가 mustache //
        System.out.println("Call welcome() method");
        System.out.println(String.format("이름 : %s, 나이 : %d", name, age));
        // static 패키지가 아닌, pracitce 패키지의 welcome.html 파일을 호출하도록 설정 //
        // application.properties 파일에서 suuffix를 이용해서 html 파일을 자동으로 설정 //
        /*
            절대경로 : 고유한 주소, 실제 주소
            상대경로 : 현재 위치를 기준으로 상대의 위치
                /   : 루트
                ./  : 현재 위치
                ../ : 현재 위치의 상단 폴더
        */
        // model은 html 파일에 데이터를 전달하는 용도 MVC 패턴에서 Model의 역할 //
        //model.addAttribute("name", name);
        //model.addAttribute("age", age);
        users.add(new User(name, age, true));
        users.add(new User(name, age, false));
        model.addAttribute("users", users);
        return "/practice/welcome";
    }
}
