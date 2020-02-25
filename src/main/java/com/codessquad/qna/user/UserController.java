package com.codessquad.qna.user;

import com.codessquad.qna.common.CommonUtility;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String goUserForm(Model model) {
        model.addAttribute("actionUrl", "/users");
        model.addAttribute("httpMethod", "POST");
        model.addAttribute("buttonName", "회원가입");
        return "users/form";
    }

    @PostMapping("")
    public String createUser(User user) {
        try {
            userRepository.save(user);
        } catch (ConstraintViolationException e) {
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
            return new ModelAndView(CommonUtility.ERROR_USER_NOT_FOUND);
        }
        return modelAndView;
    }


    @GetMapping("/{id}/form")
    public String showUserInfoModifyForm(@PathVariable long id, Model model, HttpSession session) {
        try {
            User user = getUserIfExist(id);
            if (!getLoginUserAndCheckEqualsRequestedUser(session, user)) {
                return CommonUtility.ERROR_CANNOT_EDIT_OTHER_USER_INFO;
            }
            model.addAttribute("user", user);
        } catch (NotFoundException e) {
            return CommonUtility.ERROR_USER_NOT_FOUND;
        }
        model.addAttribute("actionUrl", "/users/" + id);
        model.addAttribute("httpMethod", "PUT");
        model.addAttribute("buttonName", "수정");
        return "/users/form";
    }

    @PutMapping("/{id}")
    public String updateUserInfo(@PathVariable long id,
                                 @RequestParam String userPassword,
                                 @RequestParam String userName,
                                 @RequestParam String userEmail,
                                 HttpSession session) {
        try {
            User user = getUserIfExist(id);
            if (!getLoginUserAndCheckEqualsRequestedUser(session, user)) {
                return CommonUtility.ERROR_CANNOT_EDIT_OTHER_USER_INFO;
            }
            updateUserNameAndEmail(user, userName, userPassword, userEmail);
        } catch (NotFoundException e) {
            return CommonUtility.ERROR_USER_NOT_FOUND;
        } catch (TransactionSystemException e) {
            return "redirect:/users/" + id + "/form";
        }

        return "redirect:/users";
    }

    private boolean getLoginUserAndCheckEqualsRequestedUser(HttpSession session, User user) {
        Object userAttribute = session.getAttribute(CommonUtility.SESSION_LOGIN_USER);
        return checkUserEqualsLoginUser(user, userAttribute);
    }

    private boolean checkUserEqualsLoginUser(User user, Object userAttribute) {
        if (userAttribute == null) {
            return false;
        }
        User sessionedUser = (User) userAttribute;
        return sessionedUser.equals(user);
    }

    @GetMapping("/login")
    public String goLoginPage() {
        return "users/login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String userId,
                            @RequestParam String userPassword,
                            HttpSession session) {
        User user = userRepository.findByUserId(userId);

        // 사용자가 없거나, 비밀번호 일치하지 않는 경우
        if (user == null || !user.getUserPassword().equals(userPassword)) {
            return "users/login_failed";
        }
        session.setAttribute(CommonUtility.SESSION_LOGIN_USER, user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logoutUser(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    private User getUserIfExist(long id) throws NotFoundException {
        return userRepository.findById(id)
                             .orElseThrow(() -> new NotFoundException("해당 사용자는 존재하지 않는 사용자입니다."));
    }

    private void updateUserNameAndEmail(User user,
                                        String userName,
                                        String userPassword,
                                        String userEmail) {
        if (user.getUserPassword().equals(userPassword)) {
            user.setUserName(userName);
            user.setUserEmail(userEmail);
            userRepository.save(user);
        }
    }

}
