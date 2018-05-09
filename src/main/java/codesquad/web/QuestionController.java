package codesquad.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class QuestionController {

	List<Question> questions = new ArrayList<>();
	
	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("questions", questions);
		return "index";
	}
	
	@PostMapping("/questions/create")
	public String createQuestion(Question question) {
		SimpleDateFormat time = new SimpleDateFormat("yyyy-mm-dd hh:mm");
		String nowTime = time.format(new Date(System.currentTimeMillis())); 
		question.setTime(nowTime);
		question.setId(questions.size()+1);
		questions.add(question);
		return "redirect:/";
	}
	
	@GetMapping("/questions/{id}")
	public String showQuestion(@PathVariable int id, Model model) {
		System.out.println(questions.get(id-1).toString());
		model.addAttribute("question",questions.get(id-1));
		return "show";
	}
	
}
