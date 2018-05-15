package codesquad.web.controller;

import codesquad.web.domain.User;
import codesquad.web.domain.UserRepository;
import codesquad.web.domain.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    private Users users = new Users();

    @Autowired
    private UserRepository userRepository;

    // post는 받아서 전달
    @PostMapping("create")
    public String create(User user) {
        System.out.println("User : " + user);
        userRepository.save(user);
        users.addUser(user);
        return "redirect:/users"; // 얘는 get맵핑이 되어 있어야 함.
    }

    // get은 가진 것을 뿌려줌
    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        System.out.println(users.toString());
        return "user/list";
    }

    @GetMapping("{userId}")
    public String showUser(Model model, @PathVariable("userId") String userId) {
        User user = users.findUser(userId);
        System.out.println("find user : " + user.getUserId());
        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("{id}/form")
    public String showUpdatePage(Model model, @PathVariable("id") int id) {
        User user = users.findUser(id);
        System.out.println("find user : " + user.getUserId());
        model.addAttribute("user", user);
        return "user/updateForm";
    }

    @PostMapping("update")
    public String update(String userId, String beforePassword, String password, String name, String email) {
        User user = users.matchUser(userId, beforePassword);
        if (user == null) return "/";
        user.update(password, name, email);
        return "redirect:/users";
    }

}
