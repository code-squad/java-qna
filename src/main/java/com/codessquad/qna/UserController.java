package com.codessquad.qna;

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
//@RequestMapping("")
public class UserController {
    private List<User> users = new ArrayList<>();

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/form")
    public String viewUserForm() {
        return "/user/form";
    }

    @PostMapping("/user/create")
    public String createUser(User user) {
        //System.out.println("user => " + user);
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String viewList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/user/list";
    }

    @GetMapping("/user/{id}")
    public String viewProfile(@PathVariable long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).get());
        return "/user/profile";
    }

    @GetMapping("/user/{userId}/form")
    public String viewUpdateForm(@PathVariable("userId") String userId, Model model) {
        for(User user : users) {
            if(user.getUserId().equals(userId)) {
                model.addAttribute("user", user);
                return "/user/updateForm";
            }
        }
        return "/user/updateForm";
    }

    @PostMapping("/user/{userId}/update")
    public String viewUpdatedList(@PathVariable String userId, String password, String name, String email) {
        for(User user : users) {
            if(user.getUserId().equals(userId) && user.getPassword().equals(password)) {
                user.setName(name);
                user.setEmail(email);
                return "redirect:/users";
            }
        }
        return "redirect:/users";
    }
}
