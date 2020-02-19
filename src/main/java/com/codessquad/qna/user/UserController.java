package com.codessquad.qna.user;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/form")
    public String goUserForm(Model model) {
        model.addAttribute("actionUrl", "/user/create");
        model.addAttribute("buttonName", "회원가입");
        return "user/form";
    }

    @PostMapping("/user/create")
    public String createUser(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String showUserList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/users/{id}")
    public ModelAndView showUserProfile(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView("user/profile");
        try {
            modelAndView.addObject("user",
                    userRepository.findById(id)
                                  .orElseThrow(() -> new NotFoundException("해당 사용자는 존재하지 않는 사용자입니다.")));
        } catch (NotFoundException e) {
            return new ModelAndView("error/user_not_found");
        }
        return modelAndView;
    }

    @GetMapping("/users/{userId}/form")
    public String showUserInfoModifyForm(@PathVariable String userId, Model model) {
        for (User user : userRepository.findAll()) {
            if (user.getUserId().equals(userId)) {
                model.addAttribute("user", user);
            }
        }
        model.addAttribute("actionUrl", "/users/" + userId + "/update");
        model.addAttribute("buttonName", "수정");
        return "/user/form";
    }

    @PostMapping("/users/{userId}/update")
    public String updateUserInfo(@PathVariable String userId, HttpServletRequest request) {
        String userPassword = request.getParameter("password");
        String userName = request.getParameter("name");
        String userEmail = request.getParameter("email");
        User modifyUser = null;

        for (User user : userRepository.findAll()) {
            if (user.getUserId().equals(userId)) {
                modifyUser = user;
            }
        }

        if (modifyUser == null || !modifyUser.getUserPassword().equals(userPassword)) {
            return "redirect:/users";
        }

        modifyUser.setUserName(userName);
        modifyUser.setUserEmail(userEmail);
        return "redirect:/users";
    }

}
