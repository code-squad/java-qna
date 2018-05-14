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
			return "/user/login";
		}
		return "/qna/form";
	}

	private boolean hasPermission(HttpSession session, Question question) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			throw new IllegalStateException("로그인이 필요합니다.");
		}
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		if (!question.matchUserId(loginUser)) {
			throw new IllegalStateException("자신이 쓴 글만 수정, 삭제 가능");
		}
		return true;
	}

	@PostMapping("")
	public String createQuestion(String title, String contents, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "/user/login";
		}
		User sessionUser = HttpSessionUtils.getUserFromSession(session);
		Question newQuestion = new Question(sessionUser, title, contents);

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
		try {
			Question question = questionRepository.findById(id).get();
			hasPermission(session, question);
			model.addAttribute("question", question);
			return "/qna/updateForm";
		} catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "/user/login";
		}
	}

	@PutMapping("")
	public String updateQuestion(Long id, String contents, String title, HttpSession session, Model model) {
		try {
			Question question = questionRepository.findById(id).get();
			hasPermission(session, question);
			question.update(contents, title);
			questionRepository.save(question);
			return String.format("redirect:/questions/%d", id);
		} catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "/user/login";
		}
	}

	@DeleteMapping("/{id}")
	public String deleteQuestion(@PathVariable Long id, HttpSession session, Model model) {
		try {
			Question question = questionRepository.findById(id).get();
			hasPermission(session, question);
			questionRepository.deleteById(id);
			return "redirect:/";
		} catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "/user/login";
		}

	}

}
