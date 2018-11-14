package codesquad.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.*;

// 어노테이션, 컨트롤러라고 명시
@Controller
public class UserController {
    // 데이터를 최초로 받는 친구

    private List<User> users = new ArrayList<>();

    // 회원가입시 데이터를 전달해야하니까 post
    @PostMapping("/user/create")
    public String create(User user) {
        System.out.println("execute create!!");
        System.out.println("user : " + user);
        users.add(user);
        return "redirect:/users";
        //회원가입후에 목록을 보고 싶다.
        //뭔가 행위를 하고 다른 페이지로 이동하고 싶을 때 사용하는 키워드 redirect
    }

    // user 목록 보여주는 친구니까 get
    // 브라우저에서 해당하는 경로를 요청을 보내면 이것이 실행이 된다.
    // 템플릿츠 밑에서 접근한다. 브라우저에서 접근이 불가능
    // suffix  = 그 값과 이것을 합쳐서 파일을 찾게 된다.
    @GetMapping("/users")
    public String list(Model model) {
        model.addAttribute("users", users);
        return "user/list";
    }


    @GetMapping("/users/{userId}")
    public String showProfile(Model model, @PathVariable("userId") String userId) {
        model.addAttribute(users.stream().filter(x -> x.getUserId().equals(userId)).findFirst().orElse(null));
        return "user/profile";
    }
}
