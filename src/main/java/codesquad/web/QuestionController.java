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
import codesquad.domain.Result;
import codesquad.domain.User;

@Controller
@RequestMapping("/questions")
public class QuestionController {

	@Autowired
	private QuestionRepository questionRepository;

	@GetMapping("/form")
	public String questionForm(HttpSession session, Model model) {
		Result result = valid(session);
		if (!result.isValid()) {
			model.addAttribute("errorMessage", result.getErrorMessage());
			return "/user/login";
		}
		return "/qna/form";
	}

	private Result valid(HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return Result.fail("로그인이 필요합니다.");
		}
		return Result.ok();
	}

	private Result valid(HttpSession session, Question question) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return Result.fail("로그인이 필요합니다.");
		}
		if (!question.matchUserId(HttpSessionUtils.getUserFromSession(session))) {
			return Result.fail("자신이 쓴 글만 수정, 삭제 가능");
		}
		return Result.ok();
	}

	@PostMapping("")
	public String createQuestion(String title, String contents, HttpSession session) {
		Result result = valid(session);
		if (!result.isValid()) {
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
		Question question = questionRepository.findById(id).get();
		Result result = valid(session, question);
		if (!result.isValid()) {
			model.addAttribute("errorMessage", result.getErrorMessage());
			return "/user/login";
		}
		model.addAttribute("question", question);
		return "/qna/updateForm";
	}

	@PutMapping("")
	public String updateQuestion(Long id, String contents, String title, HttpSession session, Model model) {
		Question question = questionRepository.findById(id).get();
		Result result = valid(session, question);
		if (!result.isValid()) {
			model.addAttribute("errorMessage", result.getErrorMessage());
			return "/user/login";
		}
		question.update(contents, title, HttpSessionUtils.getUserFromSession(session));
		questionRepository.save(question);
		return String.format("redirect:/questions/%d", id);
	}

	@DeleteMapping("/{id}")
	public String deleteQuestion(@PathVariable Long id, HttpSession session, Model model) {
		Result result = valid(session, questionRepository.findById(id).get());
		if (!result.isValid()) {
			model.addAttribute("errorMessage", result.getErrorMessage());
			return "/user/login";
		}
		questionRepository.deleteById(id);
		return "redirect:/";
	}

}
