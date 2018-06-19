package codesquad.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;

@Controller
@RequestMapping("/questions")
public class QuestionController {
	
	@Autowired
	private QuestionRepository questionRepository;

	@PostMapping("")
	public String create(Question question) {
		System.out.println("question : " + question);
		questionRepository.save(question);
		return "redirect:/";
	}
	
	@GetMapping("/{id}")
	public String profile(Model model, @PathVariable Long id) {
		model.addAttribute("question", questionRepository.findById(id).get());
		return "/qna/show";
	}
	
	@GetMapping("/form")
	public String profile(Model model) {
		model.addAttribute("questions", questionRepository.findAll());
		return "/qna/form";
	}
}