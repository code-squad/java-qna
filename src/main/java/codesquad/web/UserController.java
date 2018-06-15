package codesquad.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
	private List<User> users = new ArrayList<>();
	
	public List<User> getUsers() {
		return users;
	}
	
	@PostMapping("/users/create")
	public String create(User user) {
		System.out.println("user : " + user);
		users.add(user); 
		return "redirect:/users";
	}
	
	@GetMapping("/users")
	public String list(Model model) {
		model.addAttribute("users", users);
		return "/user/list";
	}
	
	@GetMapping("/users/{userId}")
	public String profile(Model model, @PathVariable String userId) {
		for(int i = 0; i < users.size(); i++) {
			if (users.get(i).getUserId().equals(userId)) {
				model.addAttribute("user", users.get(i));
			}
		}
		return "/user/profile";
	}
	
	@GetMapping("/users/form")
	public String profile(Model model) {
		model.addAttribute("users", users);
		return "/user/form";
	}
}
