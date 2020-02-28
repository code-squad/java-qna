package com.codessquad.qna;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.QuestionRepository;
import com.codessquad.qna.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/form")
    public String form(HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/users/loginForm";
        }

        return "/question/form";
    }

    @PostMapping("")
    public String create(String title, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/users/loginForm";
        }

        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        if (sessionUser != null) {
            Question newQuestion = new Question(sessionUser, title, contents);
            questionRepository.save(newQuestion);
        }

        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable Long id) {
        model.addAttribute("question", questionRepository.findById(id).orElseThrow(NullPointerException::new));

        return "/question/show";
    }


    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }

        User sessionedUser = HttpSessionUtils.getUserFromSession(session);

        model.addAttribute("question", questionRepository.findById(id).orElseThrow(NullPointerException::new));
        return "/question/updateForm";
    }

//    @PutMapping("/{id}")
//    public String update(@PathVariable Long id, Model model, Question updateQuestion, HttpSession session) {
//        if (!HttpSessionUtils.isLoginUser(session)) {
//            return "redirect:/users/loginForm";
//        }
//
//        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
//
//        model.addAttribute("question", questionRepository.findById(id).orElseThrow(NullPointerException::new));
//        Question question = questionRepository.findById(id).orElseThrow(NullPointerException::new);
//        question.update(updateQuestion);
//        questionRepository.save(question);
//        return "redirect:/";
//    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, String title, String contents) {
        Question question = questionRepository.findById(id).orElseThrow(NullPointerException::new);
        question.update(title, contents);
        questionRepository.save(question);
        return String.format("redirect:/questions/%d", id);
    }

//    @DeleteMapping("/{id}")
//    public String delete(@PathVariable Long id, Question deleteQuestion, HttpSession session) {
//        if (!HttpSessionUtils.isLoginUser(session)) {
//            return "redirect:/users/loginForm";
//        }
//
//        Question question = questionRepository.findById(id).orElseThrow(NullPointerException::new);
//        questionRepository.delete(id);
//        return "redirect:/";
//    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        questionRepository.deleteById(id);
        return "redirect:/";
    }
}
