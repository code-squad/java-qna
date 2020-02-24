package com.codesquad.qna.web;

import com.codesquad.qna.domain.User;
import com.codesquad.qna.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String login() {
        return "/users/login";
    }

    @GetMapping("/join")
    public String moveUserForm() {
        return "/users/join";
    }

    @PostMapping("/create")
    public String addUser(User user) {
        userRepository.save(user);
        return "redirect:/users"; //templates의 index.html 호출
    }

    @GetMapping("")
    public String viewUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/users/list";
    }

    @GetMapping("/{id}")
    public ModelAndView viewProfile(@PathVariable long id) {
        ModelAndView mav = new ModelAndView("/users/profile");
        mav.addObject("currentUser", userRepository.findById(id).get());
        return mav;
    }

    @GetMapping("/{id}/modify")
//    public ModelAndView viewModificationProfile(@PathVariable long id) {
//        ModelAndView mav = new ModelAndView("/users/modify");
//        mav.addObject("currentUser", userRepository.findById(id).get());
//        return mav;
//    }
    public String moveUpdateForm(@PathVariable Long id, Model model) {
        model.addAttribute("currentUser", userRepository.findById(id).get());
        return "/users/modify";
    }

    @PutMapping("/{id}")
    public String updateUser(User user, @PathVariable long id) throws ResponseStatusException{
        User currentUser = userRepository.findById(id).get();
        //TODO : 기존 비밀번호 확인 로직
        System.out.println(currentUser.checkPassword(user));
        if (!currentUser.checkPassword(user)) {
//            return "redirect:/users";
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "비밀번호가 맞지 않아요!");
        }
        currentUser.update(user); //새로 입력한 정보로 회원정보 수정
        userRepository.save(currentUser);
        return "redirect:/users";
    }

}
