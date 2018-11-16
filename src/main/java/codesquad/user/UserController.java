package codesquad.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

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
        return "redirect:";       //행위를 하고 다른 페이지를 보여주고 싶을때
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


}
