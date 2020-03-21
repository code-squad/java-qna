package com.codesquad.qna.web;

import com.codesquad.qna.domain.User;
import com.codesquad.qna.domain.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login-form")
    public String loginForm() {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User selectedUser = userRepository.findByUserId(userId).orElseThrow(UserNotFoundException::new);

        if (selectedUser.isNotCorrectPassword(password)) {
            throw new LoginFailedException();
        }

        session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, selectedUser);
        logger.info("{} 사용자가 로그인을 했습니다.", selectedUser);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        User loginUser = HttpSessionUtils.couldGetValidUserFromSession(session);

        logger.info("{} 사용자가 로그아웃을 했습니다.", loginUser);
        session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);

        return "redirect:/";
    }

    @PostMapping("")
    public String createUser(User user) {
        userRepository.save(user);

        return "redirect:/users";
    }

    @GetMapping("")
    public String listUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());

        return "user/list";
    }

    @GetMapping("/{id}")
    public String showUser(@PathVariable Long id, Model model) {
        User selectedUser = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        model.addAttribute("user", selectedUser);
        logger.info("{} 사용자의 정보를 조회 합니다.", selectedUser);

        return "user/profile";
    }

    @GetMapping("/{id}/form")
    public String userForm(@PathVariable Long id, Model model, HttpSession session) {
        User loginUser = HttpSessionUtils.couldGetValidUserFromSession(session);
        User selectedUser = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        if (!loginUser.equals(selectedUser)) {
            throw new UserNotPermittedException();
        }

        model.addAttribute("user", selectedUser);

        return "user/updateForm";
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable Long id, @RequestParam String confirmPassword,  User updatedUser, HttpSession session) {
        User loginUser = HttpSessionUtils.couldGetValidUserFromSession(session);
        User selectedUser = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        if (!loginUser.equals(selectedUser)) {
            throw new UserNotPermittedException();
        }

        if (selectedUser.isNotCorrectPassword(confirmPassword)) {
            throw new LoginFailedException();
        }

        selectedUser.update(updatedUser);
        userRepository.save(selectedUser);
        logger.info("{} 사용자의 정보를 수정 했습니다.", selectedUser);

        return "redirect:/users";
    }
}
