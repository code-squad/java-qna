package codesquad;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @PostMapping("/create")
    public String create(UserDTO userDTO, Model model){
        model.addAttribute("userId", userDTO.getUserId());
        model.addAttribute("password", userDTO.getPassword());
        model.addAttribute("name", userDTO.getName());
        model.addAttribute("email", userDTO.getEmail());
        return "hello";
    }
}
