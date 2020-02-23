package com.codessquad.qna;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user/create")
    public String create(User user) {
        System.out.println("user : " + user);
        userRepository.save(user);
        return "redirect:/user/list";
    }

    @GetMapping("/user/list")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

//    @GetMapping("/user/{id}/form")
//    public String updateForm(@PathVariable Long id, Model model) {
//        ModelAndView mav = new ModelAndView("user/updateform");
//        mav.addObject("user", userRepository.findById(id).orElse(null));
//        return mav;
//    }

//    @GetMapping("/user/profile/{userId}")
//    public String profile(Model model, @PathVariable String userId) {
//        for (User user : users) {
//            if (user.getUserId().equals(userId)) {
//                model.addAttribute("userId", userId);
//                model.addAttribute("email", user.getEmail());
//                return "user/profile";
//            }
//        }
//        return "user/profile";
//    }
}
