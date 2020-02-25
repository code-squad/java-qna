package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.domain.UserRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users") // 중복되는 prefix, 자원에 대해 명시해준다.
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/form")
    public String getQuestionFrom() {
        System.out.println("회원가입 폼");
        return "users/form";
    }

    @PostMapping("/create")
    public String createUser(User user) {
        System.out.println("사용자 생성");
        userRepository.save(user);
        return "redirect:/users/list";
    }

    @GetMapping("/list")
    public String getUserList(Model model) {
        System.out.println("사용자 리스트");
        model.addAttribute("users", userRepository.findAll());
        return "users/list";
    }

    @GetMapping("/{id}")
    public String getUserProfile(@PathVariable Long id, Model model) throws NotFoundException {
        System.out.println("사용자 프로필");
        model.addAttribute("user", userRepository.findById(id).orElseThrow(() -> new NotFoundException("존재하지 않는 회원입니다.")));
        return "users/profile";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model) throws NotFoundException {
        System.out.println("사용자 정보 수정 폼");
        model.addAttribute("user", userRepository.findById(id).orElseThrow(() -> new NotFoundException("존재하지 않는 회원입니다.")));
        return "users/updateForm";
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable Long id, User updatedUser) {
        User user = userRepository.findById(id).get();
        user.update(updatedUser);
        userRepository.save(user);
        return "redirect:/users/list";
    }

}
