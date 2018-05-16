package codesquad.controller;

import codesquad.domain.User;
import codesquad.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
            /* error 처리 컨트롤러로 리다이렉트 시켜야함 : 새로운 요청으로 만들어서 이전 form 데이터값 없애기 */
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
    public String get(Model model, @PathVariable("id") String id) {
        Optional<User> user = userRepo.findById(Long.valueOf(id));
        if (!user.isPresent()) {
            System.out.println("존재하지않는 사용자입니다.");
            return "redirect:/error/db";
        }
        model.addAttribute("user", user.get());
        return "/user/profile";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") String id, Model model) {
        Optional<User> optionalUser = userRepo.findById(Long.valueOf(id));
        if (!optionalUser.isPresent()) {
            return "redirect:/error/db";
        }
        model.addAttribute("user", optionalUser.get());
        return "/user/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") String id, String currentPasswd, User updateInfo) {
        Optional<User> optionalUser = userRepo.findById(Long.valueOf(id));
        if (!optionalUser.isPresent()) {
            return "redirect:/error/db";
        }

        try {
            User user = optionalUser.get();
            user.changeInfo(currentPasswd, updateInfo);
            userRepo.save(user);
            return "redirect:/users/" + id;
        } catch (DataAccessException e) {
            return "redirect:/error/db";
        } catch (IllegalArgumentException e) {
            /* TODO : redirect URI 수정하기 */
            return "redirect:/";
        }
    }
}
