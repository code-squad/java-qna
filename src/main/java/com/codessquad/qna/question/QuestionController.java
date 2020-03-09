package com.codessquad.qna.question;

import com.codessquad.qna.sessionutils.HttpSessionUtils;
import com.codessquad.qna.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    private static Logger log = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/form")
    public String qnaForm(HttpSession session) {
        if (HttpSessionUtils.isNoneExistentUser(session)) {
            return "redirect:/users/loginForm";
        }

        return "qna/form";
    }

    @PostMapping("")
    public String createQna(String title, String contents, HttpSession session) {
        if (HttpSessionUtils.isNoneExistentUser(session)) {
            return "redirect:/users/loginForm";
        }

        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        Question newQuestion = new Question(sessionUser.getUserId(), title, contents);
        questionRepository.save(newQuestion);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String showQuestionContents(@PathVariable Long id, Model model) throws IllegalAccessException {
        model.addAttribute("question", questionRepository.findById(id).orElseThrow(IllegalAccessException::new));
        return "qna/show";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model) throws IllegalAccessException {
        model.addAttribute("question", questionRepository.findById(id).orElseThrow(IllegalAccessException::new));
        return "/qna/updateForm";
    }

    @PutMapping("/{id}")
    public String updateQna(@PathVariable Long id, String title, String contents) throws IllegalAccessException {
        Question question = questionRepository.findById(id).orElseThrow(IllegalAccessException::new);
        question.update(title, contents);
        questionRepository.save(question);
        return String.format("redirect:/questions/%d",id);
    }

    @DeleteMapping("/{id}")
    public String deleteQna(@PathVariable Long id) {
        questionRepository.deleteById(id);
        return "redirect:/";
    }
}
