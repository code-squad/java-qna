package com.codessquad.qna.web.user;

import com.codessquad.qna.common.constants.CommonConstants;
import com.codessquad.qna.common.constants.ErrorConstants;
import com.codessquad.qna.common.error.exception.CannotEditOtherUserInfoException;
import com.codessquad.qna.common.error.exception.UserNotFoundException;
import com.codessquad.qna.common.utils.HttpSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
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

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {this.userRepository = userRepository;}

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
    public ModelAndView showUserProfile(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("users/profile");
        modelAndView.addObject("user", getUserIfExist(id));
        return modelAndView;
    }


    @GetMapping("/{id}/form")
    public String showUserInfoModifyForm(@PathVariable Long id, Model model, HttpSession session) {
        User user = getUserIfExist(id);
        User loginUser = HttpSessionUtils.getUserFromSession(session).orElse(null);
        if (!user.equals(loginUser)) {
            throw new CannotEditOtherUserInfoException();
        }
        model.addAttribute("user", user);
        model.addAttribute("actionUrl", "/users/" + id);
        return "/users/modify-form";
    }

    @PutMapping("/{id}")
    public String updateUserInfo(@PathVariable Long id,
                                 User updateUser,
                                 @RequestParam String newPassword,
                                 HttpSession session) {
        try {
            User user = getUserIfExist(id);
            User loginUser = HttpSessionUtils.getUserFromSession(session).orElse(null);
            if (!user.equals(loginUser)) {
                throw new CannotEditOtherUserInfoException();
            }
            if (user.isUserPasswordNotEquals(updateUser)) {
                return "redirect:/users/" + id + "/form";
            }
            user.update(updateUser, newPassword);
            session.setAttribute(CommonConstants.SESSION_LOGIN_USER, userRepository.save(user));
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

    private User getUserIfExist(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    /**
     * 다른 유저의 정보를 수정하려고 하였을 때, 유저 목록으로 이동.
     */
    @ExceptionHandler(CannotEditOtherUserInfoException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    protected String handleCannotEditOtherUserInfoException(CannotEditOtherUserInfoException e) {
        log.error("handleCannotEditOtherUserInfoException", e);
        return ErrorConstants.ERROR_CANNOT_EDIT_OTHER_USER_INFO;
    }

}
