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
        System.out.println("excute create!");
        System.out.println("user : " + user);
        users.add(user);

        return "redirect:/users";
    }

    @GetMapping("/users")
    public String list(Model model) {
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/users/{userId}")
    public String showProfile(@PathVariable String userId, Model model) {
//        for (User user : users) {
//            if(user.getUserId().equals(userId)){
//                model.addAttribute("user", user);
//            }
//        }

        model.addAttribute("user", users.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findFirst()
                .orElse(null));
        return "user/profile";
    }

    @GetMapping("/user/{userId}/form")
    public String updateForm(@PathVariable String userId, Model model) {
        model.addAttribute("user", users.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findFirst()
                .orElse(null));
        return "user/updateForm";
    }

    @PostMapping("/user/{id}/update")
    public String modifyForm(User modifyUser) {
        for (User user : users) {
            if(user.getUserId().equals(modifyUser.getUserId())){
                user.setName(modifyUser.getName());
                user.setPassword(modifyUser.getPassword());
                user.setEmail(modifyUser.getEmail());
                System.out.println("정보 수정 완료 : " + user);
            }
        }
        return "redirect:/users";
    }
}
