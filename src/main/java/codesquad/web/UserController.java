package codesquad.web;


import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("")
	public String create(User user) {
		logger.debug("user : {}", user);
		userRepository.save(user);
		return "redirect:/users";
	}
	
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
			logger.debug("fail");
			return "/user/login_failed";
		}
		if (!user.checkPassword(password)) {
			logger.debug("fail");
			return "/user/login_failed";
		}
		logger.debug("success");
		// 로그인 정보 기록
		session.setAttribute("sessionedUser", user);
		return "redirect:/";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("sessionedUser");
		return "redirect:/";
	}
	
	@GetMapping("/{id}")
	public String profile(Model model, @PathVariable Long id) {
		User user = userRepository.findById(id).get();
		model.addAttribute("user", user);
		return "/user/profile";
	}
	
	@GetMapping("/form")
	public String profile(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "/user/form";
	}
	
	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/loginForm";
		}
		User sessionedUser = (User) session.getAttribute(HttpSessionUtils.USER_SESSION_KEY);
		//question의 id와 user의 id가 맞는지 아닌지 확인하고 있음
		if (!sessionedUser.checkId(id)) {
			throw new IllegalStateException("u can modify only yours");
		}
		
		User user = userRepository.findById(id).get();
		model.addAttribute("user", user);
		return "/user/updateForm";
	}
	
	@PutMapping("/{id}")
	public String update(@PathVariable Long id, User updatedUser, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/loginForm";
		}
		User sessionedUser = (User) session.getAttribute(HttpSessionUtils.USER_SESSION_KEY);
		if (!sessionedUser.checkId(id)) {
			throw new IllegalStateException("u can modify only yours");
		}
		
		User user = userRepository.findById(id).get();
		user.update(updatedUser);
		userRepository.save(user);
		return "redirect:/users";
	}
}