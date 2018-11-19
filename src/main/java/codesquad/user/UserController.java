package codesquad.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/loginForm")
    public String loginForm(){
        return "/user/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        Optional<User> maybeUser = userRepository.findByUserId(userId);
        if (maybeUser.isPresent()) {
            User user = maybeUser.get();
            if (user.matchPassword(password)) {
                session.setAttribute("loginUser", user);
            }
        }
        return "redirect:/";
    }


    @PostMapping("/create")
    public String userCreate(User user) {
        System.out.println("execute create!!");
        System.out.println("user : " + user);
        userRepository.save(user);
        return "redirect:/users";       //행위를 하고 다른 페이지를 보여주고 싶을때
    }

    @GetMapping("")
    public String showMemberList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/user/list";
    }

    @GetMapping("/{id}")
    public String showPersonalInformation(@PathVariable Long id, Model model) {
//        Optional<User> maybeUser = userRepository.findById(id);

        model.addAttribute("usersProfile", userRepository.findById(id).orElse(null));
        return "/user/profile";
    }

    @GetMapping("/{id}/form")
    public String updatePersonalInformation(@PathVariable Long id, Model model, HttpSession session) {
        Object tempUser = session.getAttribute("loginUser");
        if (tempUser == null) {
            return "redirect:/users/loginForm";
        }

        User sessionedUser = (User)tempUser;
        if (!id.equals(sessionedUser.getId())){
            throw new IllegalArgumentException("자신의 정보만 수정할 수 있습니다.");
        }

//        Optional<User> maybeUser = userRepository.findById(id);
//        if(maybeUser.isPresent()){ //존재 한다면

//        }

//        userRepository.findById(id).orElseThrow(()->new IllegalArgumentException());  //사용자가 존재하지 않으면 에러 반환후 중단
        model.addAttribute("usersInformation", userRepository.findById(id).orElse(null));    //사용자가 존재 하지 않으면 null반환
        return "/user/updateForm";
    }

    @PutMapping("/{id}")
    public String redirect(@PathVariable Long id,User updateUser, HttpSession session) {
        Object tempUser = session.getAttribute("loginUser");
        if (tempUser == null) {
            return "redirect:/users/loginForm";
        }

        User sessionedUser = (User)tempUser;
        if (!id.equals(sessionedUser.getId())){
            throw new IllegalArgumentException("자신의 정보만 수정할 수 있습니다.");
        }

//        User loginUser = (User) session.getAttribute("loginUser");   //오늘 수업을 통해 세션을 통해 넣고 빼는 것을 배웠다.
        User user = userRepository.findById(id).orElse(null);
        user.update(updateUser);
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("loginUser");
        return "redirect:/";
    }

}
