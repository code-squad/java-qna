package codesquad.web;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;

@Controller
public class QuestionController {

	@Autowired
	private QuestionRepository questionRepository;
	
	
	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("questions", questionRepository.findAll());
		return "index";
	}
	
	@GetMapping("/qna/form")
	public String userForm() {
		return "/qna/form";
	}
	
	@PostMapping("/questions/create")
	public String createQuestion(Question question) {
		SimpleDateFormat time = new SimpleDateFormat("yyyy-mm-dd hh:mm");
		String nowTime = time.format(new Date(System.currentTimeMillis())); 
		question.setTime(nowTime);
		questionRepository.save(question);
		return "redirect:/";
	}
	
	@GetMapping("/questions/{id}")
	public String showQuestion(@PathVariable Long id, Model model) {
		model.addAttribute("question", questionRepository.findById(id).get());
		return "/qna/show";
	}
	
}
