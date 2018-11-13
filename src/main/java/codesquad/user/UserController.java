package codesquad.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {
    private List<User> users = new ArrayList<>();

    @PostMapping("/user/create")
    //form의 id를 그대로 매개변수로 (컨벤션) 그럼 인자들이 자동으로 서버에 할당
    public String create(User user) {
        System.out.println("execute create!!");
        System.out.println("userId : " + user);
        users.add(user);
        //추가된사용자들은 보여줘야하는데 -> 동적
        //이건 static에서 하는게 아니라 templates디렉토리 만들어서
        return "redirect:/users";
        //redirect: 하면 회원가입하면 users로 가서 목록을 보게 한다
        //행위를 하고 이동
    }

    @GetMapping("/users")
    public String list(Model model) {
        model.addAttribute("users", users);
        return "user/list";
        //"user.list"를 써도 application.properties의 suffix에 지정해줬기 때문에 list.html을 찾게 된다.
    }

    @GetMapping("/users/{userId}")
    public String profile(Model model, @PathVariable String userId) {

        for (User user : users) {
            //todo: 간결화 가능한 지, getter 쓰지 않고 가능한 지?
            if(user.getUserId().equals(userId)) {
                model.addAttribute("user", user);
            }
        }
        return "/user/profile";
    }

//    @GetMapping("/user/login")
//    public String login(Model model) {
////        model.addAttribute("users", users);
//        return "user/login";
//    }
//
//    @GetMapping("/user/join")
//    public String form(Model model) {
////        model.addAttribute("users", users);
//        return "user/form";
//    }

}
//todo: stream에서 map한 값은 object type인지 확인해보기

