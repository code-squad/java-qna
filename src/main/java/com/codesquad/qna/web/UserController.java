package com.codesquad.qna.web;

import com.codesquad.qna.domain.User;
import com.codesquad.qna.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    // 로그인 페이지로 이동
    @GetMapping("/login")
    public String loginForm() {
        return "/user/login";
    }

    // 로그인 페이지에서 아이디 비밀번호 입력 후, userRepository 에서 ID,PW 비교하고, 같으면 HTTP session에 저장
    // 로그인시 , 해당 아이디가 DB에 없거나, 비밀번호가 다를 경우 login_failed 페이지로 이동
    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User loginUser = userRepository.findByUserId(userId);
        if (loginUser == null) {
            session.setAttribute("loginCondition",false);
            return "redirect:/users/login";
        }

        if (!password.equals(loginUser.getPassword())) {
            session.setAttribute("loginCondition",false);
            return "redirect:/users/login";
        }

        session.setAttribute("loginCondition",true);
        session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, loginUser);
        return "redirect:/";
    }

    // 로그인 한 상태에서 logout을 했을 경우 session에 저장된 user의 정보를 제거한 뒤 , home으로 이동
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
        return "redirect:/";
    }

    // userRepository에 유저를 저장한다.
    @PostMapping("/create")
    public String createUser(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    // /userRepository의 모든 값을 users라는 변수명으로 model에 저장하고, HTML페이지로 전달한다.
    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/user/list";
    }

    // 로그인 한 뒤에 나오는 개인정보수정 버튼을 통해서 user/info_change 페이지로 이동한다.
    @GetMapping("/info_change")
    public String userLoginCheck() {
        return "/user/info_change";
    }

    // 개인정보 수정 페이지에서 userID를 제외한 값들을 수정할 수 있다. password,name,email 값을 user클래스의 update함수를 통해서 값을 전달하고
    // user 클래스 안에서 각 변수의 길이가 0 이상일 때, 값을 저장한다음, userRepository에 값을 변경한다.
    @PutMapping("/{id}")
    public String infoChange(@PathVariable Long id, User updateUser, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return HomeController.NOT_AUTHORIZE_DIRECTORY;
        }
        User loginUser = HttpSessionUtils.getUserFromSession(session).orElse(null);
        loginUser.update(updateUser);
        userRepository.save(loginUser);
        return "redirect:/users";
    }

    // user 전체를 볼 수 있는 list.html에서 userID를 클릭했을 때, 해당하는 유저의 profile페이지로 이동한다.
    @GetMapping("/{id}/profile")
    public ModelAndView userProfile(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("/user/profile");
        modelAndView.addObject("user", userRepository.findById(id).orElse(null));
        return modelAndView;
    }

    // user 전체를 볼 수 있는 list.html에서 수정을 클릭했을 때, 로그인 한 회원만 회원정보를 수정할 수 있고, 다른 경우는 수정할 수 없게
    @GetMapping("/{id}/info_change")
    public String idInfoChange(@PathVariable Long id, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return HomeController.NOT_AUTHORIZE_DIRECTORY;
        }
        User loginUser = HttpSessionUtils.getUserFromSession(session).orElse(null);
        if (loginUser.idCheck(id)) {
            return "redirect:/users/info_change";
        }

        return HomeController.NOT_AUTHORIZE_DIRECTORY;
    }

}
