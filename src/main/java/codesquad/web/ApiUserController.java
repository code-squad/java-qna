package codesquad.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codesquad.domain.User;
import codesquad.domain.UserRepository;

@RestController
@RequestMapping("/api/users")
public class ApiUserController {
	@Autowired
	private UserRepository userRepositoy;
	
	@GetMapping("/{id}")
	public User show(@PathVariable Long id) {
		return userRepositoy.findById(id).get();
	}
}
