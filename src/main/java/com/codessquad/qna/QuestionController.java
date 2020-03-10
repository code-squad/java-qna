package com.codessquad.qna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/form")
    public String createQuestion(HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/user/login";
        }
        return "/qna/form";
    }

    @PostMapping("/form")
    public String makeQuestion(String title, String contents, HttpSession session) {
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Question question = new Question(loginUser.getName(), title, contents, loginUser.getId());
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/{questionIndex}")
    public ModelAndView questionShowDetail(@PathVariable Long questionIndex) {
        ModelAndView modelAndView = new ModelAndView("/qna/show");
        modelAndView.addObject("question", questionRepository.getOne(questionIndex));
        return modelAndView;
    }

    @GetMapping("/{questionIndex}/modifyQuestion")
    public String modifyQuestion(@PathVariable Long questionIndex, HttpSession session, Model model) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/user/login";
        }
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.getOne(questionIndex);
        if (question.authorizeUser(loginUser.getId())) {
            model.addAttribute("question", question);
            return "/qna/modify_form";
        }
        return "/qna/not_qualified";
    }

    @PostMapping("/{questionIndex}/updateQuestion")
    public String updateQuestion(@PathVariable Long questionIndex, String title, String contents) {
        Question question = questionRepository.getOne(questionIndex);
        question.updateQuestion(title, contents);
        questionRepository.save(question);
        return "redirect:/";
    }

    @PostMapping("/{questionIndex}/delete")
    public String deleteQuestion(@PathVariable Long questionIndex, HttpSession session) {
        Question question = questionRepository.getOne(questionIndex);
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        if (question.authorizeUser(loginUser.getId())) {
            questionRepository.delete(question);
            return "redirect:/";
        }
        return "/qna/not_qualified";
    }

}
