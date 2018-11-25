package codesquad.qna.answers;

import codesquad.util.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
    @Autowired
    AnswerRepository answerRepository;

    @PostMapping
    public String create(Answer answer, HttpSession session){
        if(!HttpSessionUtils.existLoginUserFromSession(session)) return "redirect:/user/login";
        answerRepository.save(answer);
        return "redirect:";
    }

    @GetMapping("/{id}/form")
    public String ShowUpdateView(@PathVariable long id, Model model, HttpSession session){
        if(!HttpSessionUtils.existLoginUserFromSession(session)) return "redirect:/user/login";
        Answer answer = answerRepository.findById(id).orElse(null);
        model.addAttribute("answer", answer);
        if(!answer.matchWriter(HttpSessionUtils.getLoginUserFromSession(session))) {
            model.addAttribute("question", answer.getQuestion());
            return "qna/show_failed";
        }
        return "qna/answerForm";
    }

    @PutMapping("/{id}")
    public String updateAnswer(@PathVariable long id, String contents, HttpSession session, Model model){
        if(!HttpSessionUtils.existLoginUserFromSession(session)) return "redirect:/user/login";
        Answer answer = answerRepository.findById(id).orElse(null);
        try {
            answer.updateContents(contents, HttpSessionUtils.getLoginUserFromSession(session));
        } catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "qna/modify_failed";
        }
        answerRepository.save(answer);
        return "redirect:..";
    }

    @DeleteMapping("{id}")
    public String deleteAnswer(@PathVariable long id, HttpSession session, Model model){
        if(!HttpSessionUtils.existLoginUserFromSession(session)) return "redirect:/user/login";
        Answer answer = answerRepository.findById(id).orElse(null);
        try{
            answer.delete(HttpSessionUtils.getLoginUserFromSession(session));
        } catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "qna/modify_failed";
        }
        answerRepository.save(answer);
        return "redirect:..";
    }
}
