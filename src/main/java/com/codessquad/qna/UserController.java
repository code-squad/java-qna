package com.codessquad.qna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("")
    public String createUser(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("")
    public String showList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "userList";
    }

    ///회원가입 눌렀을 때
    @GetMapping("/form")
    public String createAccount() {
        return "RegistrationForm";
    }

    ///수정버튼 눌렀을 때
    @GetMapping("/{userNumber}/form")
    public String editInfo(@PathVariable Long userNumber, Model model, HttpSession session) {

        if (HttpSessionUtil.isLoginUser(session)) {
            System.out.println("Sign in first to edit your info");
            return "redirect:/";
        }

        User sessionedUser = HttpSessionUtil.getUserFromSession(session);
        if (! sessionedUser.matchUserNumber(userNumber)) {
            throw new IllegalStateException("Access denied");
        }

        model.addAttribute("user", findUser(userNumber));

        return "EditUserInfo";
    }

    @PostMapping("/{userNumber}")
    public String updateInfo(@PathVariable Long userNumber, User updatedUser) {
        User user = findUser(userNumber);
        user.update(updatedUser);
        userRepository.save(user);
        return "redirect:/users";
    }

    private User findUser(Long userNumber) {
        return userRepository.findById(userNumber).orElseThrow(() -> new EntityNotFoundException("/error/notFound"));
    }



}
