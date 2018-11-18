package codesquad.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("user",userRepository.findById(pId).get());
        System.out.println(model);
        return "/user/profile";
    }

    @GetMapping("/form")
    public String userForm() {
        return "/user/form";
    }

    @GetMapping("/login")
    public String userLogin() {
        return "/user/login";
    }

    @GetMapping("/{pId}/form")
    private String userUpdateForm(Model model, @PathVariable long pId){
        model.addAttribute("user",userRepository.findById(pId).get());
        return "/user/updateForm";
    }

    @PutMapping("/{pId}/update")
    private String userUpdate(User newUser, @PathVariable long pId){
        User user = userRepository.findById(pId).get();
        user.update(newUser);
        userRepository.save(user);
        return "redirect:/user/list";
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
