package codesquad.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import codesquad.domain.User;
import codesquad.domain.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping("")
	public String list(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "/user/list";
	}

	@GetMapping("/loginForm")
	public String loginForm() {
		return "/user/login";
	}

	@PostMapping("/login")
	public String login(String userId, String password, HttpSession session) {
		User user = userRepository.findByUserId(userId);

		if (user == null) {
			System.out.println("login fail");
			return "redirect:/users/loginForm";
		}

		if (!password.equals(user.getPassword())) {
			System.out.println("login fail");
			return "redirect:/users/loginForm";
		}

		System.out.println("login success");
		session.setAttribute("sessionedUser", user);

		return "redirect:/";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("sessionedUser");
		return "redirect:/";
	}

	@GetMapping("/form")
	public String userForm(User user) {
		return "/user/form";
	}

	@GetMapping("/{id}/updateForm")
	public String userUpdate(@PathVariable Long id, Model model, HttpSession session) {
		Object tempUser = session.getAttribute("sessionedUser");
		if (tempUser == null) {
			return "redirect:/users/loginForm";
		}
		User sessionedUser = (User) tempUser;
		if (!id.equals(sessionedUser.getId())) {
			throw new IllegalStateException("You can't update the another user");
		}

		User user = userRepository.findById(id).get();
		model.addAttribute("user", user);
		return "/user/updateForm";
	}

	@PutMapping("/update/{id}")
	public String userUpdate(@PathVariable Long id, User updatedUser, HttpSession session) {
		Object tempUser = session.getAttribute("sessionedUser");
		if (tempUser == null) {
			return "redirect:/users/loginForm";
		}
		User sessionedUser = (User) tempUser;
		if (!id.equals(sessionedUser.getId())) {
			throw new IllegalStateException("You can't update the another user");
		}

		User user = userRepository.findById(id).get();
		user.update(updatedUser);
		userRepository.save(user);
		return "redirect:/users";
	}

	@PostMapping("/create")
	public String create(User user) {
		userRepository.save(user);
		return "redirect:/users";
	}

	@GetMapping("/profile/{id}")
	public String profile(@PathVariable Long id, Model model) {
		User user = userRepository.findById(id).get();
		model.addAttribute("user", user);
		return "/user/profile";
	}

	/*
	 * gram's user validation
	 * 
	 * @GetMapping("/updateForm") public String userUpdateForm(@PathVariable Long
	 * id, Model model, HttpSession session) { User user =
	 * userRepository.findById(id).get(); model.addAttribute("user", user); return
	 * "/user/updateForm"; }
	 * 
	 * @GetMapping("/checkUserForm/{id}") public String checkUserForm(@PathVariable
	 * Long id, Model model) { User user = userRepository.findById(id).get();
	 * model.addAttribute("id", id); model.addAttribute("userId", user.getUserId());
	 * return "/user/checkUser"; }
	 * 
	 * @GetMapping("/checkUser/{id}") public String checkUser(@PathVariable Long id,
	 * String password, Model model) throws Exception { User user =
	 * userRepository.findById(id).get(); model.addAttribute("user", user);
	 * 
	 * if (user.check(password)) { return "/user/updateForm"; } return
	 * "/users/checkUserForm/" + id; if(!user.check(password)) { throw new
	 * Exception("비밀번호가 틀렸습니다."); } }
	 */
}
