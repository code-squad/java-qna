package codesquad.qna.questions;

import codesquad.util.HttpSessionUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    private static final Logger logger = getLogger(QuestionController.class);

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
        if(!question.matchWriter(HttpSessionUtils.getLoginUserFromSession(session))) {
            model.addAttribute("errorMessage", "permission denied. 다른 사용자의 글은 수정할 수 없습니다.");
            return "qna/modify_failed";
        }
        return "qna/updateForm";
    }

    @PutMapping("/{id}")
    public String updateQuestion(@PathVariable long id, Question modifiedQuestion, HttpSession session, Model model){
        if(!HttpSessionUtils.existLoginUserFromSession(session)) return "redirect:/user/login";
        Question question = questionRepository.findById(id).orElse(null);
        try {
            question.update(modifiedQuestion);
        }catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "qna/modify_failed";
        }
        questionRepository.save(question);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable long id, HttpSession session, Model model){
        if(!HttpSessionUtils.existLoginUserFromSession(session)) return "redirect:/user/login";
        Question question = questionRepository.findById(id).orElse(null);
        try {
            question.delete(HttpSessionUtils.getLoginUserFromSession(session));
        }catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "qna/modify_failed";
        }
        questionRepository.save(question);
        return "redirect:/";
    }




}
