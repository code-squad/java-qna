package com.codesquad.qna.web;

import com.codesquad.qna.domain.User;
import com.codesquad.qna.domain.UserRepository;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

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
            sessionUser = userRepository.findByUserId(userId); //에러캐치가 안됨..어느시점에서 발생하는거지..
        } catch (NullPointerException e) {
            logger.debug("Login Fail!! no user");
            //TODO: user가 없는 경우, 404 NotFound
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user가 없어요!!");
        }

        if (!sessionUser.matchPassword(password)) {
            logger.debug("Login Fail!! not match password");
            return "redirect:/users/login";
        }
        logger.debug("Login Success!");
        session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, sessionUser); //sessionUser 이름의 session 데이터에 sessionUser 정보를 담음
        return "redirect:/";
    }

    @PostMapping("/logout")
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
        logger.debug("User : {}", user);
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
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }

        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        if(!sessionUser.matchId(id)) {
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
    public String updateUser(User updatedUser, String newPassword , @PathVariable Long id, HttpSession session) throws ResponseStatusException {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }

        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        if (!sessionUser.matchId(id)) {
            throw new IllegalStateException("자신의 정보만 수정할 수 있습니다.");
        }

        try {
            //session에 들어있는 사용자의 id 값으로 데이터를 가져와서 모델에 담아줌 -> 자신의 정보만 수정가능
            User currentUser = userRepository.findById(sessionUser.getId()).orElseThrow(() -> new NotFoundException("그런 회원 없는뎅"));
            //TODO : 기존 비밀번호 확인 로직
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
