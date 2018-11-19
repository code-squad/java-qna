package codesquad.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.*;

/* Controller 역할 부여를 위한 어노테이션 */
@Controller
public class UserController {
    // Ram 메모리에 저장이 되기 때문에 서버 재구동 시, 모두 초기화 //
    // Ram 메모리는 가격이 비싸고, 빠른 처리속도 제공 //
    // 하드디스크는 가격이 싸고, 느린 처리속도 제공 //
    // 서버구동에도 영향을 받지않고 데이터를 조작하기 위해서는 데이터베이스 필요 //
    private List<User> users = new ArrayList<>();

    /* Post : 클라이언트에서 서버로 데이터를 보내는 방식 / Get : 클라이언트에서 서버로 데이터를 받는 방식 */
    @PostMapping("user/create")
    public String create(User user) {
        users.add(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String list(Model model) {
        model.addAttribute("users", users); // 반환하는 웹 페이지에 변수를 전달
        return "/user/list";
    }

    @GetMapping("/users/{userId:.+}")
    public String information(@PathVariable("userId") String userId, Model model) {
        System.out.println("Call profile Method " + userId);
        model.addAttribute("user", obtainUser(userId));
        return "/user/profile";
    }

    public User obtainUser(String userId) {
        for(User user : users) {
            if(user.isUser(userId)) {
                return user;
            }
        }
        return null;
    }
}
