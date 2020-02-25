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

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String login() {
        return "/users/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User sessionUser;
        try {
            sessionUser = userRepository.findByUserId(userId);
            System.out.println("input userId : " + userId + ", input password : " + password + ", session : " + session);
            System.out.println("userID : " + sessionUser.getUserId());
            System.out.println("password : " + sessionUser.getPassword());
        } catch (NullPointerException e) {
            System.out.println("Login Fail!");
            //TODO: user가 없는 경우, 404 NotFound
            return "redirect:/users/login";
        }

        if (!password.equals(sessionUser.getPassword())) {
            System.out.println("Login Fail!");
            return "redirect:/users/login";
        }
        System.out.println("Login Success!");
        session.setAttribute("sessionUser", sessionUser); //HttpSession을 통해서 session.setAttribute();`로 세션을 저장
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("sessionUser");
        return "redirect:/";
    }

    @GetMapping("/join")
    public String moveUserForm() {
        return "/users/join";
    }

    @PostMapping("")
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
            mav.addObject("currentUser", userRepository.getOne(id)); //NoSuchElementException
            return mav;
    }

    @GetMapping("/{id}/modify")
    public String moveUpdateForm(@PathVariable Long id, Model model) {
            model.addAttribute("currentUser", userRepository.getOne(id)); //EntityNotFoundException
            return "/users/modify";
    }

    @PutMapping("/{id}")
    public String updateUser(User sesssionUser, String newPassword , @PathVariable long id) throws ResponseStatusException{
        User currentUser = userRepository.getOne(id);
        //TODO : 기존 비밀번호 확인 로직
        System.out.println(currentUser.matchPassword(sesssionUser));
        if (!currentUser.matchPassword(sesssionUser)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "비밀번호가 맞지 않아요!");
        }
        currentUser.update(sesssionUser, newPassword); //새로 입력한 정보로 회원정보 수정
        userRepository.save(currentUser);
        return "redirect:/users";
    }

}
