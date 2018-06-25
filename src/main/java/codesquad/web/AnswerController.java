package codesquad.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import codesquad.domain.Answer;
import codesquad.domain.AnswerRepository;
import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;
import codesquad.domain.Result;
import codesquad.domain.User;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerRepository answerRepository;
	
	@PostMapping("")
	public String create(@PathVariable Long questionId, String contents, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/loginForm";
		}
		User loginUser = (User) HttpSessionUtils.getUserFromSession(session);
		Question question = questionRepository.findById(questionId).get();
		Answer answer = new Answer(loginUser, question, contents);
		answerRepository.save(answer);
		return String.format("redirect:/questions/%d", questionId);
	}
	
	@DeleteMapping("/{id}")
	public String delete(@PathVariable Long questionId, @PathVariable Long id, Model model, HttpSession session) {
		Answer answer = answerRepository.findById(id).get();
		Result result = Result.valid(session, answer.classForQAndA());
		if (!result.isValid()) {
			model.addAttribute("errorMessage", result.getErrorMessage());
			return "/user/login_failed";
		}
		answerRepository.delete(answer);
		return String.format("redirect:/questions/%d", questionId);
	}
}
