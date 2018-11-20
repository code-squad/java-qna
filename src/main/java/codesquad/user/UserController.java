package codesquad.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;  //db

    @PostMapping("/create")
    public String create(User user) {
        System.out.println("execute create!!");
        System.out.println("user : " + user);
        userRepository.save(user);
        return "redirect:/user/list";
    }

    @GetMapping("/list")
    public String userList(Model model) {
        System.out.println("userList execute complete!");
        model.addAttribute("users", userRepository.findAll());
        return "/user/list";
    }

    @GetMapping("/profile/{pId}")
    public String userProfile(Model model, @PathVariable long pId) {
        System.out.println("profile complete!");
        model.addAttribute("user", userRepository.findById(pId).get());
        System.out.println(model);
        return "/user/profile";
    }

    @GetMapping("/form")
    public String userForm() {
        return "/user/form";
    }

    @GetMapping("/{pId}/form")
    private String userUpdateForm(Model model, @PathVariable long pId, HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
//        model.addAttribute("user", userRepository.findById(loginUser.getPId()).get());
//        return "/user/updateForm";
        if (loginUser.getPId()==pId) {
            model.addAttribute("user", userRepository.findById(pId).get());
            return "/user/updateForm";
        }
        throw new IllegalStateException("You can't update other user's information.");
    }

    @PutMapping("/{pId}/update")
    private String userUpdate(User updatedUser, @PathVariable long pId, HttpSession session) {
        User user = userRepository.findById(pId).orElseThrow(() -> new IllegalArgumentException());
//        if(session.)
//        User user = userRepository.findById(pId).get();
        user.update(updatedUser);
        userRepository.save(user);
        return "redirect:/user/list";
    }

    @GetMapping("/loginForm")
    private String login() {
        return "/user/login";
    }

    @PostMapping("/login")
    public String userLogin(String userId, String password, HttpSession session) {
        Optional<User> maybeUser = userRepository.findByUserId(userId);
        if (!maybeUser.isPresent()) {
            System.out.println("Wrong Id!");
            return "/user/login";
        }
        User user = maybeUser.get();
        if (!user.matchPassword(password)) {
            System.out.println("Wrong Password!");
            return "/user/login";
        }
        session.setAttribute("loginUser", user);
        System.out.println("login complete");
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("loginUser");
        return "redirect:/";
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
}
