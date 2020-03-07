package com.codessquad.qna.question;

import com.codessquad.qna.answer.AnswerRepository;
import com.codessquad.qna.commons.CommonUtils;
import com.codessquad.qna.commons.CustomErrorCode;
import com.codessquad.qna.errors.QuestionException;
import com.codessquad.qna.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

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
  public String form(Model model, HttpSession session) {
    log.info("### sessionedUser : " + session.getAttribute("sessionedUser"));
    CommonUtils.getSessionedUser(session);

    return "/questions/form";
  }

  /**
   * Feat : Question 수정을 위한 hbs 로 이동합니다.
   * Desc : Utils.getSessionedUser() 를 통해 login 여부를 검증합니다.
   * Return : /questions/update
   */
  @GetMapping("/{id}/form")
  public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
    log.info("### sessionedUser : " + session.getAttribute("sessionedUser"));

    User sessionedUser = CommonUtils.getSessionedUser(session);
    Question question = CommonUtils.getQuestion(questionRepository, id);

    if (sessionedUser.validateUserId(question.getUserId())) {
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
    CommonUtils.getSessionedUser(session);
    questionRepository.save(question);

    return "redirect:/";
  }

  /**
   * Feat : Question 상세 내용을 보여주는 .hbs 로 이동합니다.
   * Desc : getQuestion() 을 통해 Question 존재 여부를 검증합니다.
   * Return : /questions/show
   */
  @GetMapping("/{id}")
  public String show(@PathVariable Long id, Model model) {
    log.info("### show()");
    Question question = CommonUtils.getQuestion(questionRepository, id);
    model.addAttribute("question", question);
    model.addAttribute("answers", answerRepository.findByQuestionId(question.getId()));

    return "/questions/show";
  }

  /**
   * Feat : Question 을 update 해줍니다.
   * Desc : getQuestion() 을 통해 Question 존재 여부를 검증합니다.
   * Return : /questions/show
   */
  @PutMapping("/{id}")
  public String update(@PathVariable Long id, Question question, Model model) {
    log.info("### update()");
    Question origin = CommonUtils.getQuestion(questionRepository, id);
    origin.update(question);
    questionRepository.save(question);

    return "/questions/show";
  }

  /**
   * Feat : Question 을 delete 합니다.
   * Desc : getQuestion() 을 통해 Question 존재 여부를 검증합니다.
   * Return : /questions/show
   */
  @DeleteMapping("/{id}")
  public String delete(@PathVariable Long id, HttpSession session) {
    log.info("### delete()");

    User sessionedUser = CommonUtils.getSessionedUser(session);
    Question question = CommonUtils.getQuestion(questionRepository, id);

    if (sessionedUser.validateUserId(question.getUserId())) {
      questionRepository.delete(question);
      return "redirect:/";
    }

    throw new QuestionException(CustomErrorCode.USER_NOT_MATCHED);
  }
}
