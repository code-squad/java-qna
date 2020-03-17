package com.codesquad.qna.web;

import com.codesquad.qna.advice.exception.UnauthorizedException;
import com.codesquad.qna.domain.Question;
import com.codesquad.qna.domain.User;
import com.codesquad.qna.repository.AnswerRepository;
import com.codesquad.qna.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

import static com.codesquad.qna.UrlStrings.REDIRECT_MAIN;
import static com.codesquad.qna.web.HttpSessionUtils.getUserFromSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @GetMapping("/create")
    public String createForm(HttpSession session) {
        getUserFromSession(session);
        return "qna/form";
    }

    @PostMapping("")
    public String create(Question question, Model model, HttpSession session) {
        User loginUser = getUserFromSession(session);
        Question createdQuestion = new Question(loginUser, question);
        questionRepository.save(createdQuestion);
        return REDIRECT_MAIN.getUrl();
    }

    @GetMapping("/{id}")
    public String read(@PathVariable Long id, Model model, HttpSession session) {
        Question question = findById(id);
        model.addAttribute("question", question);

        if (getUserFromSession(session).isIdEquals(question)) {
            model.addAttribute("hasPermissionUser", true);
        }

        model.addAttribute("answers", answerRepository.findByQuestionId(id));
        return "qna/show";
    }

    @GetMapping("/{id}/update")
    public String updateForm(@PathVariable Long id, HttpSession session, Model model) {
        User loginUser = getUserFromSession(session);
        Question updatingQuestion = findById(id);

        loginUser.hasPermission(updatingQuestion);
        model.addAttribute("updatingQuestion", updatingQuestion);
        return "qna/form";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, HttpSession session, Question updatedQuestion) {
        User loginUser = getUserFromSession(session);
        Question question = findById(id);

        loginUser.hasPermission(question);
        question.update(updatedQuestion);
        questionRepository.save(question);
        return "redirect:/questions/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        User loginUser = getUserFromSession(session);
        Question question = findById(id);

        loginUser.hasPermission(question);
        questionRepository.delete(question);
        return "redirect:/";
    }

    private Question findById(Long id) {
        return questionRepository.findById(id).orElseThrow(UnauthorizedException::noMatchUser);
    }
}
