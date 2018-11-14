package codesquad.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private List<User> users = new ArrayList<>();

    @PostMapping("/user/create")
    public String create(User user) {
        System.out.println("execute create!!");
        System.out.println("user : " + user);
        users.add(user);
        return "redirect:/user/list";
    }

    @GetMapping("/user/list")
    public String userList(Model model) {
        System.out.println("userList execute complete!");
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/user/profile/{id}")
    public String userProfile(Model model, @PathVariable String id) {
        for (User user : users) {
            if (user.getUserId().equals(id)) {
                model.addAttribute(user);
            }
        }
        return "user/profile";
    }

    @GetMapping("/user/form")
    public String userForm() {
        return "user/form";
    }

//    @PostMapping("/user/create")
//    public String create(String userId , String password, String name, String email) {
//        System.out.println("execute create!!");
//        System.out.println("userId : " + userId);
//        System.out.println("name : " + name);
//        System.out.println("password : " + password);
//        System.out.println("email : " + email);
//        return "user/index";
//    }

//    @GetMapping("/users")
//    public String list(Model model) {
//        model.addAttribute("users",users);
//        return "user/list";
//    }

//    @GetMapping("/helloWorld")
//    public String welcome(){
//        return "user/welcome";
//    }
}
