package codesquad.web;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codesquad.domain.Answer;
import codesquad.domain.AnswerRepository;
import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;
import codesquad.domain.Result;
import codesquad.domain.User;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass()); 
	
	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;

	@PostMapping("")
	public Answer create(@PathVariable Long questionId, String contents, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return null;
		}
		
		User sessionUser = HttpSessionUtils.getUserFromSession(session);
		Question question = questionRepository.findById(questionId).get();
		Answer answer = new Answer(sessionUser, question, contents);
		question.addAnswer();
		
		return answerRepository.save(answer);
	}

	@DeleteMapping("/{id}")
	public Result deleteAnswer(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
		logger.debug("QuestionId와 id : {}, {},", questionId, id);
		logger.debug("답변삭제");
		
		if (!HttpSessionUtils.isLoginUser(session)) {
			return Result.fail("로그인 해야합니다");
		}
		Answer answer = answerRepository.findById(id).get();
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		if (!answer.isSameWriter(loginUser)) {
			return Result.fail("자신의 글만 삭제 가능");
		}
		answerRepository.deleteById(id);
		
		Question question = questionRepository.findById(id).get();
		question.deleteAnswer();
		questionRepository.save(question);
		return Result.ok();
	}
}
