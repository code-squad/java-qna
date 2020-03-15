package com.codesquad.qna.web;

import com.codesquad.qna.domain.ValidationResult;
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
    public String login(String userId, String password, HttpSession session) throws NotFoundException {
        User sessionUser = userRepository.findByUserId(userId).orElseThrow(() -> new NotFoundException("user가 없어요!!"));

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
    public String viewProfile(@PathVariable long id, Model model) throws NotFoundException{
        model.addAttribute("currentUser", userRepository.findById(id).orElseThrow(() -> new NotFoundException("그런 회원 없어욧")));
        return "/users/profile";
    }

    @GetMapping("/{id}/modify")
    public String moveUpdateForm(Model model, @PathVariable Long id, HttpSession session) throws NotFoundException{
        ValidationResult validationResult = validate(session, id);
        if (!validationResult.isValid()) {
            model.addAttribute("errorMessage", validationResult.getErrorMessage());
            return "/error";
        }

        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        model.addAttribute("currentUser", userRepository.findById(sessionUser.getId()).orElseThrow(() -> new NotFoundException("그런 회원 없는뎅")));
        return "/users/modify";
    }

    @PutMapping("/{id}")
    public String updateUser(User updatedUser, String newPassword , @PathVariable Long id, Model model, HttpSession session) throws NotFoundException {
        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        User currentUser = userRepository.findById(sessionUser.getId()).orElseThrow(() -> new NotFoundException("그런 회원 없는뎅"));
        ValidationResult validationResult = validate(session, id);
        if (!validationResult.isValid()) {
            model.addAttribute("errorMessage", validationResult.getErrorMessage());
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

    private ValidationResult validate(HttpSession session, Long id) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return ValidationResult.fail("로그인이 필요합니다.");
        }

        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        if (!sessionUser.matchId(id)) {
            return ValidationResult.fail("자신의 정보만 수정할 수 있습니다.");
        }

        return ValidationResult.ok();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) //Response 헤더의 상태코드값 설정
    protected String catchNotFoundException(Model model, NotFoundException e) {
        log.debug("catchNotFoundException!!!!!!!!!!!!!!!!!!!");
        model.addAttribute("errorMessage", e.getMessage());
        return "/error";
    }
}
