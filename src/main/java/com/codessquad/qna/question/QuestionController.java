package com.codessquad.qna.question;

import com.codessquad.qna.common.CommonString;
import com.codessquad.qna.user.User;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

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
    public String goQnaForm() {
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

    @GetMapping("/questions/{index}")
    public String showQuestion(@PathVariable long index, Model model) {
        try {
            model.addAttribute("question", getQuestionIfExist(index));
        } catch (NotFoundException e) {
            return "error/question_not_found";
        }
        return "questions/show";
    }

    private Question getQuestionIfExist(@PathVariable long index) throws NotFoundException {
        return questionRepository.findById(index)
                                 .orElseThrow(() -> new NotFoundException("해당 질문글을 찾을 수 없습니다."));
    }

}
