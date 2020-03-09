package com.codessquad.qna.question;

import com.codessquad.qna.sessionutils.HttpSessionUtils;
import com.codessquad.qna.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ModelAndView showQuestionContents(@PathVariable Long id) throws IllegalAccessException {
        ModelAndView mav = new ModelAndView("qna/show");
        mav.addObject("question", questionRepository.findById(id).orElseThrow(IllegalAccessException::new));
        return mav;
    }
}
