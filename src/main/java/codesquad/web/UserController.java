package codesquad.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import codesquad.domain.User;
import codesquad.domain.UserRepository;

@Controller
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/user/form")
	public String userForm() {
		return "/user/form";
	}

	@GetMapping("/users/{id}/form")
	public String userUpdateForm(@PathVariable Long id, Model model) {
		User user = userRepository.findById(id).get();
		model.addAttribute("user", user);
		return "/user/updateForm";
	}

	@PostMapping("/user/create")
	public String create(User user) {
		System.out.println(user);
		userRepository.save(user);
		return "redirect:/user/list";
	}

	@PutMapping("/user/update/{id}")
	public String userUpdate(@PathVariable Long id, User newUser) {
		User user = userRepository.findById(id).get();
		user.update(newUser);
		userRepository.save(user);
		return "redirect:/user/list";
	}

	@GetMapping("/user/list")
	public String list(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "/user/list";
	}
	
	

	@GetMapping("/user/profile/{id}")
	public String profile(@PathVariable Long id, Model model) {
		User user = userRepository.findById(id).get();
		model.addAttribute("user", user);
		return "/user/profile";
	}
	
	

}
