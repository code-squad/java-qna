package com.codessquad.qna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/form")
    public String createUserForm() {
        return "/user/form";
    }

    @PostMapping("/create")
    public String createUser(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/user/list";
    }

    @GetMapping("/{id}")
    public ModelAndView show(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("user/profile");
        modelAndView.addObject("user",userRepository.findById(id).orElse(null));
        return modelAndView;
    }

    @GetMapping("/changeUserInfo/{id}")
    public ModelAndView userLoginForm(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("user/updateForm");
        modelAndView.addObject("user",userRepository.findById(id).orElse(null));
        return modelAndView;
    }
    @PostMapping("/{id}/update")
    public String changeUserInfoForm(@PathVariable("id") Long id, String userId, String password, String name, String email) {
        User user = userRepository.findById(id).orElse(null);
        if(password.length() > 0){
            user.setPassword(password);
        }
        if(name.length() > 0){
            user.setName(name);
        }
        if(email.length() > 0){
            user.setEmail(email);
        }
        userRepository.save(user);
        return "redirect:/users";
    }
}
