package com.codessquad.qna.question;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        return "qna/form";
    }

    @PostMapping("/questions")
    public String createQuestion(@RequestParam String writer,
                                 @RequestParam String title,
                                 @RequestParam String contents) {
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
        return "qna/show";
    }

    private Question getQuestionIfExist(@PathVariable long index) throws NotFoundException {
        return questionRepository.findById(index)
                                 .orElseThrow(() -> new NotFoundException("해당 질문글을 찾을 수 없습니다."));
    }

}
