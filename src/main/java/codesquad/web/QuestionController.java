package codesquad.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;
import codesquad.domain.User;

@Controller
@RequestMapping("/questions")
public class QuestionController {

	@Autowired
	private QuestionRepository questionRepository;

	@GetMapping("/form")
	public String questionForm(HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "/users/loginForm";
		}
		return "/qna/form";
	}

	@PostMapping("")
	public String createQuestion(String title, String contents, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "/users/loginForm";
		}
		User sessionUser = HttpSessionUtils.getUserFromSession(session);
		Question newQuestion = new Question(sessionUser.getUserId(), title, contents);

		questionRepository.save(newQuestion);
		return "redirect:/";
	}

	@GetMapping("/{id}")
	public String showQuestion(@PathVariable Long id, Model model) {
		model.addAttribute("question", questionRepository.findById(id).get());
		return "/qna/show";
	}

	@GetMapping("/{id}/updateForm")
	public String questionUpdateForm(@PathVariable Long id, Model model, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "/users/loginForm";
		}

		Question question = questionRepository.findById(id).get();
		if (!question.matchUserId(session)) {
			throw new IllegalStateException("You can't update the another user");
		}

		model.addAttribute("question", questionRepository.findById(id).get());
		return "/qna/updateForm";
	}

	@PutMapping("")
	public String updateQuestion(Long id, String contents, String title, HttpSession session) {

		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect: /users/loginForm";
		}
		
		Question question = questionRepository.findById(id).get();
		if (!question.matchUserId(session)) {
			throw new IllegalStateException("You can't update the another user");
		}
		
		question.update(contents,title);
		questionRepository.save(question);

		return "redirect:/questions/" + id;
	}
	
	@DeleteMapping("/{id}")
	public String deleteQuestion(@PathVariable Long id, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/loginForm";
		}
		
		Question question = questionRepository.findById(id).get();
		if (!question.matchUserId(session)) {
			throw new IllegalStateException("You can't delete the another user");
		}
		
		questionRepository.deleteById(id);
		return "redirect:/";
	}
	
	
}
