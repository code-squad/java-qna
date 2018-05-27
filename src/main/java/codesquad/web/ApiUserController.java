package codesquad.web;

import codesquad.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class ApiUserController {

    @GetMapping("/user/show")
    public User show(HttpSession session){
        return HttpSessionUtils.getSessionedUser(session);
    }
}
