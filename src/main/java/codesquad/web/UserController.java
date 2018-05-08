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

	
	
	@GetMapping("/user/checkForm")
	public String checkForm() {
		return "checkUser.html";
	}
	
	@GetMapping("/user/checkUser")
	public String checkUser(String userId, String password, Model model) {
		for(int i = 0; i <users.size(); i++) {
			if(users.get(i).getUserId().equals(userId))
			{
				if(users.get(i).getPassword().equals(password)) {
					model.addAttribute("userId", userId);
					model.addAttribute("index", i);
					return "updateForm";
				}
			}
		}
		return "/user/checkForm";
	}
	
	@PostMapping("/user/update")
	public String update(User user, String index) {
		users.set(Integer.parseInt(index),user);
		return "redirect:/user/list";
	}
	
	@PostMapping("/user/create")
	public String create(User user) {
		System.out.println(user);
		users.add(user);
		return "redirect:/user/list";
	}

	@GetMapping("/user/list")
	public String list(Model model) {
		model.addAttribute("users", users);
		return "list";
	}

	@GetMapping("/users/{userId}")
	public String profile(@PathVariable String userId, Model model) {
		User user = null;
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUserId().equals(userId)) {
				user = users.get(i);
			}
		}

		model.addAttribute("user", user);
		return "profile";
	}
}
