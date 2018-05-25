package codesquad.web;

import codesquad.domain.model.User;
import codesquad.domain.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class ApiUserController {

    @Autowired
    private UserRepository userRepository;
    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @GetMapping("")
    public List<User> list(Model model) {
        log.debug("show list");
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User showUser(@PathVariable("id") Long id, Model model) {
        return userRepository.findOne(id);
    }

}
