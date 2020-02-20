package com.codessquad.qna.user;

import com.codessquad.qna.common.Common;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/form")
    public String goUserForm(Model model) {
        model.addAttribute("actionUrl", "/users/create");
        model.addAttribute("httpMethod", "POST");
        model.addAttribute("buttonName", "회원가입");
        return "users/form";
    }

    @PostMapping("/create")
    public String createUser(@RequestParam String userId,
                             @RequestParam String userPassword,
                             @RequestParam String userName,
                             @RequestParam String userEmail) {
        userRepository.save(new User(userId, userPassword, userName, userEmail));
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
            return new ModelAndView(Common.ERROR_USER_NOT_FOUND);
        }
        return modelAndView;
    }


    @GetMapping("/{id}/form")
    public String showUserInfoModifyForm(@PathVariable long id, Model model) {
        try {
            model.addAttribute("user", getUserIfExist(id));
        } catch (NotFoundException e) {
            return Common.ERROR_USER_NOT_FOUND;
        }
        model.addAttribute("actionUrl", "/users/" + id + "/update");
        model.addAttribute("httpMethod", "PUT");
        model.addAttribute("buttonName", "수정");
        return "/users/form";
    }

    @PutMapping("/{id}/update")
    public String updateUserInfo(@PathVariable long id,
                                 @RequestParam String userPassword,
                                 @RequestParam String userName,
                                 @RequestParam String userEmail) {
        try {
            User user = getUserIfExist(id);
            updateUserNameAndEmail(user, userName, userPassword, userEmail);
        } catch (NotFoundException e) {
            return Common.ERROR_USER_NOT_FOUND;
        }

        return "redirect:/users";
    }

    private User getUserIfExist(@PathVariable long id) throws NotFoundException {
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
