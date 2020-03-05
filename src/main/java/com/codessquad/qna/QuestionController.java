package com.codessquad.qna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class QuestionController {

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
        Question question = questionRepository.findById(postNumber).get();
        model.addAttribute("question", question);
        return "QuestionContents";
    }

    /// 질문 수정하기
    @GetMapping("/questions/{postNumber}/form")
    public String editContents(@PathVariable Long postNumber, Model model) {
        Question question = questionRepository.findById(postNumber).get();
        model.addAttribute("question", question);
        return "qna/updateForm";
    }

    @PutMapping("/questions/{postNumber}/edit")
    public String update(@PathVariable Long postNumber, String title, String contents) {
        Question question = questionRepository.findById(postNumber).get();
        question.updateContents(title, contents);
        questionRepository.save(question);
        return "redirect:/questions/{postNumber}/contents";
    }

    @DeleteMapping("/questions/{postNumber}/delete")
    public String delete(@PathVariable Long postNumber) {
        Question uselessQuestion = questionRepository.findById(postNumber).get();
        questionRepository.delete(uselessQuestion);
        return "redirect:/posts";
    }

}
