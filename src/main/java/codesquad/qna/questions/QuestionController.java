package codesquad.qna.questions;

import codesquad.util.HttpSessionUtils;
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
    public String doQuestion(HttpSession session){
        if(!HttpSessionUtils.existLoginUserFromSession(session)) return "redirect:/user/login";
        return "qna/form";
    }

    @PostMapping
    public String create(Question question){
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping
    public String showQuestions(Model model){
        model.addAttribute("questions", questionRepository.findAll());
        return "index";
    }

    @GetMapping("/{id}")
    public String showQuestion(@PathVariable long id, Model model){
        model.addAttribute("question", questionRepository.findById(id).get());
        return "qna/show";
    }

    @GetMapping("/{id}/form")
    public String showUpdateView(@PathVariable long id, Model model, HttpSession session){
        if(!HttpSessionUtils.existLoginUserFromSession(session)) return "redirect:/user/login";
        Question question = questionRepository.findById(id).orElse(null);
        model.addAttribute("question", question);
        if(!question.matchWriter(HttpSessionUtils.getLoginUserFromSession(session))) return "qna/show_failed";
        return "qna/updateForm";
    }

    @PutMapping("/{id}")
    public String updateQuestion(@PathVariable long id, Question modifiedQuestion, HttpSession session){
        if(!HttpSessionUtils.existLoginUserFromSession(session)) return "redirect:/user/login";
        Question question = questionRepository.findById(id).orElse(null);
        question.update(modifiedQuestion);
        questionRepository.save(question);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable long id, HttpSession session){
        if(!HttpSessionUtils.existLoginUserFromSession(session)) return "redirect:/user/login";
        Question question = questionRepository.findById(id).orElse(null);
        if(!question.matchWriter(HttpSessionUtils.getLoginUserFromSession(session))) return "qna/modify_failed";
        questionRepository.delete(question);
        return "redirect:/";
    }




}
