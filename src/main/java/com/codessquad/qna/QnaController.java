package com.codessquad.qna;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QnaController {
    public List<Question> questions = new ArrayList<>();

    @PostMapping("/question/create")
    public String create(Question question) {
        System.out.println("question : " + question);
        questions.add(question);
        return "redirect:/question/show";
    }

    @GetMapping("/question/show")
    public String show(Model model) {
        for (int i = 0; i < questions.size(); i++) {
            if (questions.size() - 1 == i) {
                model.addAttribute("title", questions.get(i).getTitle());
                model.addAttribute("writer", questions.get(i).getWriter());
                model.addAttribute("contents", questions.get(i).getContents());
                return "qna/show";
            }
        }
        return "qna/show";
    }

//    @GetMapping("/question/show")
//    public String show(String writer, Model model) {
//        for (Question question : questions) {
//            if (question.getWriter().equals(writer)) {
//                model.addAttribute("title", question.getTitle());
//                model.addAttribute("writer", writer);
//                model.addAttribute("contents", question.getContents());
//                return "qna/show";
//            }
//        }
//        return "qna/show";
//    }
}
