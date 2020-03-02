package com.codessquad.qna.question;

import com.codessquad.qna.commons.CustomErrorCode;
import com.codessquad.qna.commons.Utils;
import com.codessquad.qna.errors.QuestionException;
import com.codessquad.qna.errors.UserException;
import com.codessquad.qna.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/questions")
public class QuestionController {

  @Autowired
  private QuestionRepository questionRepository;

  /**
   * Feat : Question 작성을 위한 hbs 로 이동합니다.
   * Desc : Utils.getSessionedUser() 를 통해 login 여부를 검증합니다.
   * Return : /questions/form
   */
  @GetMapping("/form")
  public String form(Model model, HttpSession session) {
    log.info("### sessionedUser : " + session.getAttribute("sessionedUser"));
    Utils.getSessionedUser(session);

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

    User sessionedUser = Utils.getSessionedUser(session);
    Question question = getQuestion(id, CustomErrorCode.QUESTION_NOT_EXIST);

    if (question.validateUserId(sessionedUser)) {
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
    Utils.getSessionedUser(session);
    questionRepository.save(question);

    return "redirect:/";
  }

  /**
   * Feat : Question 상세 내용을 보여주는 .hbs 로 이동합니다.
   * Desc : getQuestion() 을 통해 Question 여부를 검증합니다.
   * Return : /questions/show
   */
  @GetMapping("/{id}")
  public String show(@PathVariable Long id, Model model) {
    log.info("### show()");
    Question question = getQuestion(id, CustomErrorCode.BAD_REQUEST);
    model.addAttribute("question", question);

    return "/questions/show";
  }

  @PutMapping("/{id}")
  public String update(@PathVariable Long id, Question question, Model model) {
    log.info("### update()");
    Question origin = getQuestion(id, CustomErrorCode.QUESTION_NOT_EXIST);
    origin.update(question);
    questionRepository.save(question);

    return "/questions/show";
  }

  @DeleteMapping("/{id}")
  public String delete(@PathVariable Long id, Model model, HttpSession session) {
    log.info("### delete()");

    User sessionedUser = Utils.getSessionedUser(session);
    Question question = getQuestion(id, CustomErrorCode.QUESTION_NOT_EXIST);

    if (sessionedUser.getUserId().equals(question.getUserId())) {
      questionRepository.delete(question);
      return "redirect:/";
    }

    throw new QuestionException(CustomErrorCode.USER_NOT_MATCHED);
  }

  private Question getQuestion(Long id, CustomErrorCode customErrorCode) {
    Optional<Question> optionalUser = questionRepository.findById(id);
    Question question = optionalUser.orElseThrow(() -> new QuestionException(customErrorCode));

    return question;
  }
}
