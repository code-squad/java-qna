package codesquad.user;

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

    @PostMapping
    public String create(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable long id) {
        model.addAttribute("user", userRepository.findById(id).orElse(null));
        return "/user/profile";
    }

//    @GetMapping("/{id}")
//    public ModelAndView show(@PathVariable long id) {
//        ModelAndView mav = new ModelAndView("user/profile");
//        mav.addObject("user", userRepository.findById(id).orElse(null));
//        return mav;
//    }

    @GetMapping("/{id}/form")
    public String updateProfile(Model model, @PathVariable long id) {
        model.addAttribute("user", userRepository.findById(id).orElse(null));
        return "/user/updateForm";
    }

    @PutMapping("/{id}")
    public String updateUser(User updatedUser, @PathVariable long id) {
        User user = userRepository.findById(id).orElse(null);
        user.update(updatedUser);
        userRepository.save(user);
        return "redirect:/users";
    }
}
