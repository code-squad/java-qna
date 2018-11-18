package codesquad.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create")
    public String userCreate(User user){
        System.out.println("execute create!!");
        System.out.println("user : " + user);
        userRepository.save(user);
        return "redirect:/users";       //행위를 하고 다른 페이지를 보여주고 싶을때
    }

    @GetMapping("")
    public String showMemberList(Model model){
        model.addAttribute("users",userRepository.findAll());
        return "/user/list";
    }

    @GetMapping("/{id}")
    public String showPersonalInformation(@PathVariable Long id, Model model){
        model.addAttribute("usersProfile",userRepository.findById(id).orElse(null));
        return "/user/profile";
    }

    @GetMapping("/{id}/form")
    public String updatePersonalInformation(@PathVariable Long id, Model model){
        model.addAttribute("usersInformation",userRepository.findById(id).orElse(null));
        return "/user/updateForm";
    }

    @PutMapping("/{id}")
    public String redirect(@PathVariable Long id,User updateUser){
        User user = userRepository.findById(id).orElse(null);
        user.update(updateUser);
        userRepository.save(user);
        return "redirect:/users";
    }
}
