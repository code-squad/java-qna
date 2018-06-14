package codesquad.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class QuestionController {
private List<Question> questions = new ArrayList<>();
	
	@PostMapping("/questions")
	public String create(Question question) {
		System.out.println("question : " + question);
		questions.add(question); 
		return "redirect:/";
	}
	
	@GetMapping("/")
	public String list(Model model) {
		model.addAttribute("questions", questions);
		return "index";
	}
}
