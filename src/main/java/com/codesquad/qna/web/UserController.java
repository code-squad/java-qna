package com.codesquad.qna.web;

import com.codesquad.qna.domain.User;
import com.codesquad.qna.domain.UserRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public String viewProfile(@PathVariable long id, Model model) {
        try {
            model.addAttribute("currentUser", userRepository.findById(id).orElseThrow(() -> new NotFoundException("그런 회원 없어욧")));
            return "/users/profile";
        } catch (NotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/{id}/modify")
    public String moveUpdateForm(Model model, @PathVariable Long id, HttpSession session) {
        Object loggedUser = session.getAttribute("sessionUser");
        if (loggedUser == null) {
            return "redirect:/users/login";
        }

        User sessionUser = (User)loggedUser;
        if(!id.equals(sessionUser.getId())) {
            throw new IllegalStateException("자신의 정보만 수정할 수 있습니다.");
        }

        try {
            //session에 들어있는 사용자의 id 값으로 데이터를 가져와서 모델에 담아줌 -> 자신의 정보만 수정가능
            model.addAttribute("currentUser", userRepository.findById(sessionUser.getId()).orElseThrow(() -> new NotFoundException("그런 회원 없는뎅")));
            return "/users/modify";
        } catch (NotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public String updateUser(User updatedUser, String newPassword , @PathVariable Long id, HttpSession session) throws ResponseStatusException{
//        User currentUser = userRepository.getOne(id);
        Object loggedUser = session.getAttribute("sessionUser");
        if (loggedUser == null) {
            return "redirect:/users/login";
        }

        User sessionUser = (User)loggedUser;
        if (!id.equals(sessionUser.getId())) {
            throw new IllegalStateException("자신의 정보만 수정할 수 있습니다.");
        }

        try {
            //session에 들어있는 사용자의 id 값으로 데이터를 가져와서 모델에 담아줌 -> 자신의 정보만 수정가능
            User currentUser = userRepository.findById(sessionUser.getId()).orElseThrow(() -> new NotFoundException("그런 회원 없는뎅"));
            //TODO : 기존 비밀번호 확인 로직
            System.out.println(currentUser.matchPassword(updatedUser));
            if (!currentUser.matchPassword(updatedUser)) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "비밀번호가 맞지 않아요!");
            }
            currentUser.update(updatedUser, newPassword); //새로 입력한 정보로 회원정보 수정
            userRepository.save(currentUser);
            return "redirect:/users";
        } catch (NotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }

}
