package codesquad.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import codesquad.domain.QuestionRepository;

@Controller
public class HomeController {
	
	@Autowired
	private QuestionRepository questionRepositiory;
	
	@GetMapping("")
	public String home(Model model) {
		model.addAttribute("questions", questionRepositiory.findAll());
		return "index";
	}
}
