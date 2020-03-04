package com.codessquad.qna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        return "form";
    }



    ///수정버튼 눌렀을 때
    @GetMapping("/{givenNumber}/form")
    public String editInfo(@PathVariable Long givenNumber, Model model, HttpSession session) {

        if (HttpSessionUtil.isLoginUser(session)) {
            System.out.println("Sign in first to edit your info");
            return "redirect:/";
        }

        User sessionedUser = HttpSessionUtil.getUserFromSession(session);
        if (! sessionedUser.matchGivenNumber(givenNumber)) {
            throw new IllegalStateException("Access denied");
        }

        model.addAttribute("user", userRepository.findById(givenNumber).get());
        System.out.println(userRepository.findById(givenNumber));
        return "updateForm";
    }

    @PostMapping("/{givenNumber}")
    public String updateInfo(@PathVariable Long givenNumber, User updatedUser) {
        User user = userRepository.findById(givenNumber).get();
        System.out.println("Before edited : " + user);

        user.update(updatedUser);
        userRepository.save(user);
        System.out.println("Edited info : " + user);
        return "redirect:/users";
    }





}
