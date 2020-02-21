package com.codessquad.qna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping("")
public class UserController {

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

    @GetMapping("/user/{id}/form")
    public String viewUpdateForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).get());
        return "/user/updateForm";
    }

    @PostMapping("/user/{id}/update")
    public String viewUpdatedList(@PathVariable Long id, String password, String name, String email) {
        if(!(isPasswordEquals(id, password))) {
            return "redirect:/users";
        }
        userRepository.findById(id).get().setName(name);
        userRepository.findById(id).get().setEmail(email);
        userRepository.save(userRepository.findById(id).get());
        return "redirect:/users";
    }

    public Boolean isPasswordEquals(Long id, String password) {
        return userRepository.findById(id).get().getPassword().equals(password);
    }
}
