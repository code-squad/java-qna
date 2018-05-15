package codesquad.web.controller;

import codesquad.web.domain.User;
import codesquad.web.domain.UserRepository;
import codesquad.web.domain.Users;
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

    // post는 받아서 전달
    @PostMapping("/create")
    public String create(User user) {
        userRepository.save(user);
        return "redirect:/users"; // 얘는 get맵핑이 되어 있어야 함.
    }

    // get은 가진 것을 뿌려줌
    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/{id}")
    public ModelAndView showUser(@PathVariable("id") long id) {
        return new ModelAndView("user/profile").addObject("user", userRepository.findOne(id));
    }

//    @GetMapping("/{id}")
//    public String showUser(@PathVariable("id") long id, Model model) {
//        model.addAttribute("user", userRepository.findOne(id));
//        return "user/profile";
//    }

    @GetMapping("/{id}/form")
    public String showUpdatePage(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userRepository.findOne(id));
        return "user/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable("id") long id, String beforePassword, User updateUser) {
        User user = userRepository.findOne(id);
        if(user.matchWith(beforePassword)) {
            user.update(updateUser);
            userRepository.save(user);
            return "redirect:/users";
        }
        return "index";
    }

}
