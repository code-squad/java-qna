package com.codessquad.qna.question;

import com.codessquad.qna.answer.Answer;
import com.codessquad.qna.answer.AnswerRepository;
import com.codessquad.qna.commons.CustomErrorCode;
import com.codessquad.qna.errors.QuestionException;
import com.codessquad.qna.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.commons.CommonUtils.getQuestionOrError;
import static com.codessquad.qna.commons.CommonUtils.getSessionedUserOrError;

@Slf4j
@Controller
@RequestMapping("/questions")
public class QuestionController {

  @Autowired
  private QuestionRepository questionRepository;

  @Autowired
  private AnswerRepository answerRepository;

  /**
   * Feat : Question 작성을 위한 hbs 로 이동합니다.
   * Desc : Utils.getSessionedUser() 를 통해 login 여부를 검증합니다.
   * Return : /questions/form
   */
  @GetMapping("/form")
  public String form(HttpSession session) {
    log.info("### form");
    getSessionedUserOrError(session);

    return "/questions/form";
  }

  /**
   * Feat : Question 수정을 위한 hbs 로 이동합니다.
   * Desc : Utils.getSessionedUser() 를 통해 login 여부를 검증합니다.
   * Return : /questions/update
   */
  @GetMapping("/{id}/form")
  public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
    log.info("### updateForm");

    User sessionedUser = getSessionedUserOrError(session);
    Question question = getQuestionOrError(questionRepository, id);

    if (sessionedUser.equals(question.getUser())) {
      model.addAttribute("question", question);
      return "/questions/update";
    }

    throw new QuestionException(CustomErrorCode.USER_NOT_MATCHED);
  }

  /**
   * Feat : Question 을 작성합니다.
   * Desc : Utils.getSessionedUser() 를 통해 login 여부를 검증합니다.
   * Return : redirect:/
   */
  @PostMapping("")
  public String create(Question question, HttpSession session) {
    getSessionedUserOrError(session);
    questionRepository.save(question);

    return "redirect:/";
  }

  /**
   * Feat : Question 상세 내용과 Answers 를 보여주는 .hbs 로 이동합니다.
   * Desc : getQuestion() 을 통해 Question 존재 여부를 검증합니다.
   * Return : /questions/show
   */
  @GetMapping("/{id}")
  public String show(@PathVariable Long id, Model model) {
    log.info("### show()");
    Question question = getQuestionOrError(questionRepository, id);
    Iterable<Answer> answers = answerRepository.findByQuestionId(id);
    model.addAttribute("question", question);
    model.addAttribute("answers", answers);

    return "/questions/show";
  }

  /**
   * Feat : Question 을 update 해줍니다.
   * Desc : getQuestion() 을 통해 Question 존재 여부를 검증합니다.
   * Return : /questions/show
   */
  @PutMapping("/{id}")
  public String update(@PathVariable Long id, Question question) {
    log.info("### update()");
    Question originQuestion = getQuestionOrError(questionRepository, id);
    originQuestion.update(question);
    questionRepository.save(originQuestion);

    return "redirect:/questions/" + id;
  }

  /**
   * Feat : Question 을 delete 합니다.
   * Desc : getQuestion() 을 통해 Question 존재 여부를 검증합니다.
   * Return : /questions/show
   */
  @DeleteMapping("/{id}")
  public String delete(@PathVariable Long id, HttpSession session) {
    log.info("### delete()");
    User sessionedUser = getSessionedUserOrError(session);
    Question question = getQuestionOrError(questionRepository, id);

    if (sessionedUser.equals(question.getUser())) {
      questionRepository.delete(question);
      return "redirect:/";
    }

    throw new QuestionException(CustomErrorCode.USER_NOT_MATCHED);
  }
}
