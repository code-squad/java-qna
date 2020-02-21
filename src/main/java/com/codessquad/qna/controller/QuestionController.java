package com.codessquad.qna.controller;

import com.codessquad.qna.Question;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Controller
public class QuestionController {
    private List<Question> questions = Collections.synchronizedList(new ArrayList<>());

    @PostMapping("/qna/create")
    public String createQna(Question question) {
        question.setWriteTime(getWriteTime());
        question.setIndex(questions.size()+1);
        questions.add(question);
        return "redirect:/";
    }

    @GetMapping()
    public String getIndex(Model model) {
        model.addAttribute("questions", questions);
        return "index";
    }

    @GetMapping("/qna/{index}")
    public String getQuestion(@PathVariable int index, Model model) throws IndexOutOfBoundsException{
        model.addAttribute("question", getQuestionByIndex(index));
        return "qna/show";
    }

    public Date getWriteTime() {
        return new Date();
    }

    public Question getQuestionByIndex(int index) {
        return questions.get(index-1);
    }

}
