package com.codessquad.qna.question;

import com.codessquad.qna.user.User;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Controller
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/")
    public String goIndexPage(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "index";
    }

    @GetMapping("/questions/form")
    public String goQuestionForm() {
        return "questions/form";
    }

    @PostMapping("/questions")
    public String createQuestion(HttpSession session,
                                 @RequestParam String title,
                                 @RequestParam String contents) {
        Object userAttribute = session.getAttribute("loginUser");
        if (userAttribute == null) {
            return "redirect:/users/login";
        }
        String writer = ((User) userAttribute).getUserName();
        Question question = new Question(writer, title, contents);
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/questions/{id}")
    public String showQuestion(@PathVariable long id, Model model, HttpSession session) {
        try {
            Question question = getQuestionIfExist(id);
            Object userAttribute = session.getAttribute("loginUser");
            model.addAttribute("question", question);
            model.addAttribute("isLoginUserEqualsWriter",
                    isLoginUserEqualsWriter(question, userAttribute));
        } catch (NotFoundException e) {
            return "error/question_not_found";
        }

        return "questions/show";
    }

    @GetMapping("/questions/{id}/form")
    public String goQuestionModifyForm(@PathVariable long id, Model model, HttpSession session) {
        try {
            Question question = getQuestionIfExist(id);
            Object userAttribute = session.getAttribute("loginUser");
            if (!isLoginUserEqualsWriter(question, userAttribute)) {
                return "redirect:/questions/" + id;
            }
            model.addAttribute("question", question);
        } catch (NotFoundException e) {
            return "error/question_not_found";
        }

        return "questions/modifyForm";
    }

    @PutMapping("/questions/{id}")
    public String updateQuestion(@PathVariable long id,
                                 HttpSession session,
                                 @RequestParam String title,
                                 @RequestParam String contents) {
        try {
            Question question = getQuestionIfExist(id);
            Object userAttribute = session.getAttribute("loginUser");
            if (!isLoginUserEqualsWriter(question, userAttribute)) {
                return "redirect:/questions/" + id;
            }
            question.setTitle(title);
            question.setContents(contents);
            question.setUpdatedDateTime(LocalDateTime.now());
            questionRepository.save(question);
        } catch (NotFoundException e) {
            return "error/question_not_found";
        }
        return "redirect:/questions/" + id;
    }

    @DeleteMapping("/questions/{id}")
    public String deleteQuestion(@PathVariable long id, HttpSession session) {
        try {
            Question question = getQuestionIfExist(id);
            Object userAttribute = session.getAttribute("loginUser");
            if (!isLoginUserEqualsWriter(question, userAttribute)) {
                return "redirect:/questions/" + id;
            }
            questionRepository.delete(question);
        } catch (NotFoundException e) {
            return "error/question_not_found";
        }
        return "redirect:/";
    }

    private boolean isLoginUserEqualsWriter(Question question, Object userAttribute) {
        if (userAttribute == null) {
            return false;
        }
        String loginUserName = ((User) userAttribute).getUserName();
        return loginUserName.equals(question.getWriter());
    }

    private Question getQuestionIfExist(long id) throws NotFoundException {
        return questionRepository.findById(id)
                                 .orElseThrow(() -> new NotFoundException("해당 질문글을 찾을 수 없습니다."));
    }

}
