package codesquad.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class QuestionController {
	private List<Question> questions = new ArrayList<>();

	@PostMapping("/questions/create")
	public String create(Question question) {
		question.setId(questions.size() + 1);
		System.out.println("question : " + question);
		questions.add(question);
		return "redirect:/";
	}

	@GetMapping("/")
	public String list(Model model) {
		model.addAttribute("questions", questions);
		return "index";
	}
	
	@GetMapping("/questions/{id}")
	public String profile(Model model, @PathVariable long id) {
		for(int i = 0; i < questions.size(); i++) {
			if (questions.get(i).getId() == id) {
				model.addAttribute("question", questions.get(i));
			}
		}
		return "/qna/show";
	}
	
	@GetMapping("/questions/form")
	public String profile(Model model) {
		model.addAttribute("questions", questions);
		return "/qna/form";
	}
}
