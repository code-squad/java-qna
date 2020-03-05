package com.codessquad.qna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class QnaController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/question/form")
    public String form(HttpSession session) {
         if (!HttpSessionUtil.isLoginUser(session)) {
            System.out.println("sign in first to ask questions");
            return "loginForm";
        }
        return "qna/form";
    }

    @PostMapping("/questions")
    public String ask(String title, String contents, HttpSession session) {
        User writer = HttpSessionUtil.getUserFromSession(session);
        Question newQ = new Question(writer, title, contents);
        questionRepository.save(newQ);

        return "redirect:/posts";
    }

    @GetMapping("/questions/{postNumber}/contents")
    public String seeQuestions(@PathVariable Long postNumber, Model model) {
        System.out.println("post number : " + postNumber);
        Question question = questionRepository.findById(postNumber).get();

        System.out.println(question.toString());
        model.addAttribute("question", question);

        return "questionContents";
    }

}
