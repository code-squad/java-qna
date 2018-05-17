package codesquad.controller;

import codesquad.domain.User;
import codesquad.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepo;

    @PostMapping
    public String create(User user) {
        try {
            userRepo.save(user);
            return "redirect:/users";
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
            return "redirect:/err/db";
        }
    }

    @GetMapping
    public String show(Model model) {
        model.addAttribute("users", userRepo.findAll());
        return "/user/list";
    }

    @GetMapping("/{id}")
    public String get(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userRepo.findById(id).get());
        return "/user/profile";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userRepo.findById(id).get());
        return "/user/edit";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable("id") Long id, String currentPasswd, User updateInfo) {
        try {
            User user = userRepo.findById(id).get();
            user.changeInfo(currentPasswd, updateInfo);
            userRepo.save(user);
            return "redirect:/users/" + id;
        } catch (DataAccessException e) {
            return "redirect:/error/db";
        } catch (IllegalArgumentException e) {
            return "redirect:/";
        }
    }
}
