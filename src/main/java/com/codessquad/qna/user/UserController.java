package com.codessquad.qna.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {
    private static Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @PostMapping("")
    public String create(User user) {
        log.info("User :  '{}' ", user);
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/form")
    public String form() {
        return "user/form";
    }

    @GetMapping("")
    public String userList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/{id}")
    public ModelAndView showUser(@PathVariable Long id) {
        ModelAndView showMav = new ModelAndView("user/profile");
        showMav.addObject("user", userRepository.findById(id).get());
        return showMav;
    }

    @GetMapping("/{id}/form")
    public ModelAndView updateForm(@PathVariable Long id) {
        ModelAndView updateMav = new ModelAndView("user/updateForm");
        updateMav.addObject("user", userRepository.findById(id).get());
        return updateMav;
    }

    @PostMapping("/{id}")
    public String updateUser(@PathVariable Long id, User updateUser) {
        User user = userRepository.findById(id).get();
        user.update(updateUser);
        userRepository.save(user);
        return "redirect:/users";
    }
}
