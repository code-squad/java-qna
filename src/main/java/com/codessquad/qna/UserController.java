package com.codessquad.qna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String showUserList(Model model) {
        model.addAttribute("users", userRepository.findAll());

        return "user/list";
    }

    @GetMapping("/form")
    public String goForm() {
        return "user/form";
    }

    @GetMapping("/{id}")
    public String showUserProfile(@PathVariable Long id, Model model) {
        model.addAttribute("userProfile", userRepository.findById(id).get());
        return "user/profile";
    }

    @GetMapping("/{id}/form")
    public String modifyUserProfile(@PathVariable Long id, Model model, HttpSession session) {
        User sessionedUser = (User) session.getAttribute("sessionedUser");
        if(sessionedUser == null){
            return "redirect:/users/login";
        }
        if(!id.equals(sessionedUser.getId())){
            throw new IllegalStateException("자기 자신의 정보만 수정 가능합니다.");
        }

        User user = userRepository.findById(id).get();
        model.addAttribute("userProfile", user);

        return "user/updateForm";
    }

    @PutMapping("/{id}/update")
    public String updateUserProfile(@PathVariable Long id, Model model, User updateUser, HttpSession session) {
        User sessionedUser = (User) session.getAttribute("sessionedUser");
        if(sessionedUser == null){
            return "redirect:/users/login";
        }
        if(!id.equals(sessionedUser.getId())){
            throw new IllegalStateException("자기 자신의 정보만 수정 가능합니다.");
        }

        User oldUser = userRepository.findById(id).get();
        if (oldUser.isCheckPassword(updateUser)) {
            oldUser.update(updateUser);
            userRepository.save(oldUser);
        }
        model.addAttribute("userProfile", oldUser);

        return "redirect:/users";
    }

    @GetMapping("/login")
    public String goLoginForm() {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return "redirect:/users/login";
        }
        if (!password.equals(user.getPassword())) {
            return "redirect:/users/login";
        }
        session.setAttribute("sessionedUser", user);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("sessionedUser");
        return "redirect:/";
    }
}
