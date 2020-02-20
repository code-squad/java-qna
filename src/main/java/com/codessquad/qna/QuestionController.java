package com.codessquad.qna;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {
    private List<Question> questions = new ArrayList<>();
    @RequestMapping(value = "/qna/form", method = {RequestMethod.GET})
    public String createQuestion() {
        return "/qna/form";
    }

    @RequestMapping(value = "/qna/form", method = {RequestMethod.POST})
    public String makeQuestion(Question question, Model model) {
        int questionsSize = questions.size() + 1;
        question.setQuestionIndex(questionsSize);
        questions.add(question);
        return "redirect:/";
    }

    @RequestMapping(value = {"/","/index"}, method = {RequestMethod.GET})
    public String showQuestionList(Model model) {
        model.addAttribute("questions",questions);
        return "/index";
    }

    @RequestMapping(value = "/questions/{questionIndex}", method = RequestMethod.GET)
    public String questionShowDetail(@PathVariable int questionIndex, Model model) {
        for (Question question : questions){
            if (question.getQuestionIndex() == questionIndex){
                model.addAttribute("title",question.getTitle());
                model.addAttribute("writer",question.getWriter());
                model.addAttribute("writtenTime",question.getWrittenTime());
                model.addAttribute("contents",question.getContents());
            }
        }
        return "/qna/show";
    }
}