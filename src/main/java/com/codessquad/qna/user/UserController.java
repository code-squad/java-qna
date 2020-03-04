package com.codessquad.qna.user;

import com.codessquad.qna.constants.CommonConstants;
import com.codessquad.qna.constants.ErrorConstants;
import com.codessquad.qna.utils.HttpSessionUtils;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolationException;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/form")
    public String goUserForm() {
        return "users/form";
    }

    @PostMapping("")
    public String createUser(User user) {
        if (user == null) {
            return "redirect:/users/form";
        }
        if (user.getUserProfileImage().equals("")) {
            user.setUserProfileImage("../images/80-text.png");
        }
        try {
            userRepository.save(user);
        } catch (ConstraintViolationException | DataIntegrityViolationException e) {
            return "redirect:/users/form";
        }
        return "redirect:/users";
    }

    @GetMapping("")
    public String showUserList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "users/list";
    }

    @GetMapping("/{id}")
    public ModelAndView showUserProfile(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView("users/profile");
        try {
            modelAndView.addObject("user", getUserIfExist(id));
        } catch (NotFoundException e) {
            return new ModelAndView(ErrorConstants.ERROR_USER_NOT_FOUND);
        }
        return modelAndView;
    }


    @GetMapping("/{id}/form")
    public String showUserInfoModifyForm(@PathVariable long id, Model model, HttpSession session) {
        try {
            User user = getUserIfExist(id);
            User loginUser = HttpSessionUtils.getUserFromSession(session).orElse(null);
            if (!user.equals(loginUser)) {
                return ErrorConstants.ERROR_CANNOT_EDIT_OTHER_USER_INFO;
            }
            model.addAttribute("user", user);
        } catch (NotFoundException e) {
            return ErrorConstants.ERROR_USER_NOT_FOUND;
        }
        model.addAttribute("actionUrl", "/users/" + id);
        return "/users/modify-form";
    }

    @PutMapping("/{id}")
    public String updateUserInfo(@PathVariable long id,
                                 User updateUser,
                                 @RequestParam String newPassword,
                                 HttpSession session) {
        try {
            User user = getUserIfExist(id);
            User loginUser = HttpSessionUtils.getUserFromSession(session).orElse(null);
            if (!user.equals(loginUser)) {
                return ErrorConstants.ERROR_CANNOT_EDIT_OTHER_USER_INFO;
            }
            if (user.isUserPasswordNotEquals(updateUser)) {
                return "redirect:/users/" + id + "/form";
            }
            user.update(updateUser, newPassword);
            session.setAttribute(CommonConstants.SESSION_LOGIN_USER, userRepository.save(user));
        } catch (NotFoundException e) {
            return ErrorConstants.ERROR_USER_NOT_FOUND;
        } catch (TransactionSystemException e) {
            return "redirect:/users/" + id + "/form";
        }

        return "redirect:/users";
    }

    @GetMapping("/login")
    public String goLoginPage() {
        return "users/login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String userId, @RequestParam String userPassword, HttpSession session) {
        User user = userRepository.findByUserId(userId).orElse(null);
        User tempUser = new User();
        tempUser.setUserPassword(userPassword);

        // 사용자가 없거나, 비밀번호 일치하지 않는 경우
        if (user == null || user.isUserPasswordNotEquals(tempUser)) {
            return "users/login-failed";
        }
        session.setAttribute(CommonConstants.SESSION_LOGIN_USER, user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logoutUser(HttpSession session) {
        HttpSessionUtils.sessionLogout(session);
        return "redirect:/";
    }

    private User getUserIfExist(long id) throws NotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("해당 사용자는 존재하지 않는 사용자입니다."));
    }

}
