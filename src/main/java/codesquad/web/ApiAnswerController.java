package codesquad.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codesquad.domain.Answer;
import codesquad.domain.AnswerRepository;
import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;
import codesquad.domain.User;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {

	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerRepository answerRepository;

	@PostMapping("")
	public Answer create(@PathVariable Long questionId, String contents, HttpSession session) {
		System.out.println("오냐");
		if (!HttpSessionUtils.isLoginUser(session)) {
			return null;
		}

		User sessionUser = HttpSessionUtils.getUserFromSession(session);
		Question question =  questionRepository.findById(questionId).get();
		Answer answer = new Answer(sessionUser, question, contents);
		return answerRepository.save(answer);
		
//		return String.format("redirect:/questions/%d", questionId);
	}
}
