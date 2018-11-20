package codesquad.qna;

import codesquad.util.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/qna")
public class QnaController {
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
    public String showModifyQuestion(@PathVariable long id, Model model, HttpSession session){
        if(!HttpSessionUtils.existLoginUserFromSession(session)) return "redirect:/user/login";
        Question question = questionRepository.findById(id).orElse(null);
        model.addAttribute("question", question);
        if(!HttpSessionUtils.getLoginUserFromSession(session).matchName(question.getWriter())) return "qna/show_failed";
        return "qna/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable long id, Question modifiedQuestion, HttpSession session){
        if(!HttpSessionUtils.existLoginUserFromSession(session)) return "redirect:/user/login";
        Question question = questionRepository.findById(id).orElse(null);
        if(!HttpSessionUtils.getLoginUserFromSession(session).matchName(question.getWriter())) return "qna/update_failed";

        question.update(modifiedQuestion);
        questionRepository.save(question);
        return "redirect:/";
    }


}
