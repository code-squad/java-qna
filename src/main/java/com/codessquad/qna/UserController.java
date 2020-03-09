package com.codessquad.qna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/form")
    public String createUserForm() {
        return "/user/form";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "/user/login";
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
        session.setAttribute("user", user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/";
    }

    @PostMapping("/create")
    public String createUser(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/user/list";
    }

    @GetMapping("/{id}/updateForm")
    public ModelAndView show(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("user/profile");
        modelAndView.addObject("user", userRepository.findById(id).orElse(null));
        return modelAndView;
    }

    @GetMapping("/changeUserInfo/{id}")
    public String userLoginForm() {
        return "/user/loginOnlyPassword";
    }

    @PostMapping("/changeUserInfo/{id}")
    public String userLoginCheck(@PathVariable("id") Long id, String password) {
        User user = userRepository.findById(id).orElse(null);
        String userPassword = user.getPassword();
        if (userPassword.equals(password)) {
            return "redirect:/users/{id}/update";
    // 개인정보 수정 페이지에서 userID를 제외한 값들을 수정할 수 있다. password,name,email 값을 user클래스의 update함수를 통해서 값을 전달하고
    // user 클래스 안에서 각 변수의 길이가 0 이상일 때, 값을 저장한다음, userRepository에 값을 변경한다.
    @PostMapping("/update")
    public String infoChange(String password, String name, String email, HttpSession session) {
        Object value = session.getAttribute("user");
        if (value != null) {
            User user = (User) value;
            user.update(password, name, email);
            userRepository.save(user);
        }
        return "/user/login_failed";
    }

    @GetMapping("/{id}/update")
    public ModelAndView changeUserInfoBeforeLogin(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("user/updateForm");
        User user = userRepository.findById(id).orElse(null);
        modelAndView.addObject("userId", user.getUserId());
        return modelAndView;
    }

    @PostMapping("/{id}/update")
    public String changeUserInfoForm(@PathVariable("id") Long id, String userId, String password, String name, String email) {
        User user = userRepository.findById(id).orElse(null);
        if (password.length() > 0) {
            user.setPassword(password);
        }
        if (name.length() > 0) {
            user.setName(name);
        }
        if (email.length() > 0) {
            user.setEmail(email);
        }
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}/profile")
    public ModelAndView userProfile(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("user/profile");
        modelAndView.addObject("user", userRepository.findById(id).orElse(null));
        return modelAndView;
    }

    @GetMapping("/{id}/info_change")
    public String idInfoChange(@PathVariable Long id, HttpSession session) {
        Object value = session.getAttribute("user");
        if (value != null) {
            User user = (User) value;
            if(user.idCheck(id)){
                return "redirect:/users/info_change";
            }
        }
        return "/user/login_failed";
    }

}
