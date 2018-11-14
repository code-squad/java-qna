package codesquad.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {
    @PostMapping
    public String create(User user) {
        user.setIndex(UserRepository.getUsers().size() + 1);
        UserRepository.addUser(user);
        return "redirect:/users";
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", UserRepository.getUsers());
        return "user/list";
    }

    @GetMapping("/{userId}")
    public String profile(Model model, @PathVariable String userId) {
        model.addAttribute("user",
                UserRepository.getUsers().stream()
                .filter(u -> u.isMatchUserId(userId))
                .findFirst()
                .orElse(null));
        return "/user/profile";
    }

    ////        todo: 회원정보수정
    @GetMapping("/{userId}/form")
    public String updateProfile(Model model, @PathVariable String userId) {
        model.addAttribute("user",
                UserRepository.getUsers().stream()
                        .filter(u -> u.isMatchUserId(userId))
                        .findFirst()
                        .orElse(null));
        return "/user/updateForm";
    }

    //그냥 users로 그럼 create와 구분 어떻게?
    @PostMapping("/update")
    public String updateUser(User updatedUser) {
        User user = UserRepository.getUsers().get(updatedUser.getIndex());

        //비밀번호만 같을 때 수정가능하다...? 근데 비밀번호도 수정...? 로그인상태 전제...?
        user.setEmail(updatedUser.getEmail());
        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());

        return "redirect:/users";
    }
}
//todo: stream에서 map한 값은 object type인지 확인해보기

