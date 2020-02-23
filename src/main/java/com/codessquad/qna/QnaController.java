package com.codessquad.qna;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class QnaController {
    public List<Question> questions = new ArrayList<>();

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("question", questionRepository.findAll());
        return "index";
    }

    @PostMapping("/question/create")
    public String create(Question question) {
        question.setLocalDateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        questionRepository.save(question);
        System.out.println("question : " + question);
        return "redirect:/index";
    }

    @GetMapping("/question/show/{index}")
    public String show(Model model, @PathVariable int index) {
        for (int i = 0; i < questions.size(); i++) {
            if (questions.size() - 1 == i) {
                model.addAttribute("title", questions.get(i).getTitle());
                model.addAttribute("writer", questions.get(i).getWriter());
                model.addAttribute("contents", questions.get(i).getContents());
                model.addAttribute("time", questions.get(i).getLocalDateTime());
                model.addAttribute("index", index);
                return "qna/show";
            }
        }
        return "qna/show";
    }
}
