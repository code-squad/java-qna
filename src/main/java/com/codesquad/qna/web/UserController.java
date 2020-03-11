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
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String login() {
        return "/users/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User sessionUser = userRepository.findByUserId(userId).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user가 없어요!!");
        });

        if (!sessionUser.matchPassword(password)) {
            log.debug("Login Fail!! not match password");
            return "redirect:/users/login";
        }
        log.debug("Login Success!");
        session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, sessionUser);
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("sessionUser");
        session.invalidate(); //세션 만료시켜주기
        return "redirect:/";
    }

    @GetMapping("/join")
    public String moveUserForm() {
        return "/users/join";
    }

    @PostMapping("")
    public String addUser(User user) {
        log.debug("User : {}", user);
        userRepository.save(user);
        return "redirect:/users";
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
        try {
            hasPermission(session, id);
            User sessionUser = HttpSessionUtils.getUserFromSession(session);
            //session에 들어있는 사용자의 id 값으로 데이터를 가져와서 모델에 담아줌 -> 자신의 정보만 수정가능
            model.addAttribute("currentUser", userRepository.findById(sessionUser.getId()).orElseThrow(() -> new NotFoundException("그런 회원 없는뎅")));
            return "/users/modify";
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/error";
        }
    }

    @PutMapping("/{id}")
    public String updateUser(User updatedUser, String newPassword , @PathVariable Long id, Model model, HttpSession session) throws ResponseStatusException {
        User currentUser;

        try {
            User sessionUser = HttpSessionUtils.getUserFromSession(session);
            currentUser = userRepository.findById(sessionUser.getId()).orElseThrow(() -> new NotFoundException("그런 회원 없는뎅"));
            hasPermission(session, id);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/error";
        }

        if (!currentUser.matchPassword(updatedUser)) {
            log.debug("Login Fail!! not match password");
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "비밀번호가 맞지 않아요!");
        }

        currentUser.update(updatedUser, newPassword);
        userRepository.save(currentUser);
        return "redirect:/users";
    }

    private boolean hasPermission(HttpSession session, Long id) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        if (!sessionUser.matchId(id)) {
            throw new IllegalStateException("자신의 정보만 수정할 수 있습니다.");
        }

        return true;
    }
}
