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
   * Question 작성을 위한 form 으로 이동합니다.
   */
  @GetMapping("/form")
  public String form(Model model, HttpSession session) {
    log.info("### sessionedUser : " + session.getAttribute("sessionedUser"));

    if (session.getAttribute("sessionedUser") == null) {
      throw new UserException(CustomErrorCode.USER_NOT_LOGIN);
    }

    return "/questions/form";
  }

  /**
   * Question 수정을 위한 form 으로 이동합니다.
   */
  @GetMapping("/{id}/form")
  public String updateForm(@PathVariable long id, Model model, HttpSession session) {
    log.info("### sessionedUser : " + session.getAttribute("sessionedUser"));

    if (session.getAttribute("sessionedUser") == null) {
      throw new UserException(CustomErrorCode.USER_NOT_LOGIN);
    }

    User sessionedUser = Utils.getSessionedUser(session);
    Question question = getQuestion(id, CustomErrorCode.QUESTION_NOT_EXIST);

    if (sessionedUser.getUserId().equals(question.getUserId())) {
      model.addAttribute("question", question);
      return "/questions/update";
    }

    throw new QuestionException(CustomErrorCode.USER_NOT_MATCHED);
  }

  /**
   * Question 추가 후 welcome 페이지로 이동합니다.
   */
  @PostMapping("")
  public String create(Question question, HttpSession session) {
    if (session.getAttribute("sessionedUser") == null) {
      throw new UserException(CustomErrorCode.BAD_REQUEST);
    }

    questionRepository.save(question);

    return "redirect:/";
  }

  /**
   * Question 의 상세 내용을 보여주는 show 페이지로 이동합니다.
   */
  @GetMapping("/{id}")
  public String show(@PathVariable long id, Model model) {
    log.info("### show()");
    model.addAttribute("question", getQuestion(id, CustomErrorCode.BAD_REQUEST));

    return "/questions/show";
  }

  @PutMapping("/{id}")
  public String update(@PathVariable long id, Question question, Model model) {
    log.info("### update()");
    Question origin = getQuestion(id, CustomErrorCode.QUESTION_NOT_EXIST);
    origin.update(question);
    questionRepository.save(question);

    return "/questions/show";
  }

  @DeleteMapping("/{id}")
  public String delete(@PathVariable long id, Model model, HttpSession session) {
    log.info("### delete()");

    User sessionedUser = Utils.getSessionedUser(session);
    Question question = getQuestion(id, CustomErrorCode.QUESTION_NOT_EXIST);

    if (sessionedUser.getUserId().equals(question.getUserId())) {
      questionRepository.delete(question);
      return "redirect:/";
    }

    throw new QuestionException(CustomErrorCode.USER_NOT_MATCHED);
  }

  private Question getQuestion(long id, CustomErrorCode customErrorCode) {
    Optional<Question> optionalUser = questionRepository.findById(id);
    Question question = optionalUser.orElseThrow(() -> new QuestionException(customErrorCode));

    return question;
  }
}
