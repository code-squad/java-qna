package codesquad.web;

import java.util.Optional;

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

	@PostMapping("/create")
	public String create(User user) {
		
		
		/*Optional<User> aa = userRepository.findById(1L);
		User u = aa.orElseThrow(EntityNotFoundException::new);
		User g = userRepository.getOne(1L);
		
//		
		userRepository.getOne(1L);
		Optional<User> maybeUser = userRepository.findByUserId("gram");
		User ab =  maybeUser.filter(u -> u.matchPassword(user.getPassword())).orElseThrow(IllegalArgumentException::new);*/
		
		userRepository.save(user);
		return "redirect:/users";
	}

	@GetMapping("/loginForm")
	public String loginForm() {
		return "/user/login";
	}

	@PostMapping("/login")
	public String login(String userId, String password, HttpSession session) {
		Optional<User> user = userRepository.findByUserId(userId);
		
		if (!user.isPresent()) {
			System.out.println("login fail");
			return "redirect:/users/loginForm";
		}

		if (!user.get().matchPassword(password)) {
			System.out.println("login fail");
			return "redirect:/users/loginForm";
		}

		System.out.println("login success");
		session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user.get());

		return "redirect:/";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
		return "redirect:/";
	}

	@GetMapping("/form")
	public String userForm(User user) {
		return "/user/form";
	}

	@GetMapping("/{id}/updateForm")
	public String userUpdate(@PathVariable Long id, Model model, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/loginForm";
		}
		User sessionedUser = HttpSessionUtils.getUserFromSession(session);
		if (!sessionedUser.matchId(id)) {
			throw new IllegalStateException("You can't update the another user");
		}

		User user = userRepository.findById(id).get();
		model.addAttribute("user", user);
		return "/user/updateForm";
	}

	@PutMapping("/update/{id}")
	public String userUpdate(@PathVariable Long id, User updatedUser, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/loginForm";
		}
		User sessionedUser = HttpSessionUtils.getUserFromSession(session);
		if (!sessionedUser.matchId(id)) {
			throw new IllegalStateException("You can't update the another user");
		}

		User user = userRepository.findById(id).get();
		user.update(updatedUser);
		userRepository.save(user);
		return "redirect:/users";
	}

	@GetMapping("/profile/{id}")
	public String profile(@PathVariable Long id, Model model) {
		User user = userRepository.findById(id).get();
		model.addAttribute("user", user);
		return "/user/profile";
	}
}
