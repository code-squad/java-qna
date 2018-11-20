package codesquad.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public String create(User user){
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping
    public String showUsers(Model model){
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/{id}")
    public String showUser(@PathVariable long id, Model model){
        model.addAttribute("user", userRepository.findById(id).get());
        return "user/profile";
    }

    @GetMapping("/{id}/form")
    public String showModifyView(@PathVariable long id, Model model){
        model.addAttribute("user", userRepository.findById(id).get());
        return "user/updateForm";
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable long id, User modifiedUser){
        User user = userRepository.findById(id).get();
        user.update(modifiedUser);
        userRepository.save(user);
        return "redirect:/users";
    }


    /*@PutMapping("/{id}")
    public String updateUserTest(User user, HttpSession session){
        User loginUser = (User)session.getAttribute("loginUser");
        return null
    }*/


    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session){
        User user = userRepository.findByUserId(userId).orElse(null);
        if(user == null || !user.matchPassword(password)) return "user/login_failed";

        session.setAttribute("loginUser", user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("loginUser");
        return "redirect:/";
    }
}
