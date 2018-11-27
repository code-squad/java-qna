package codesquad.qna;

import codesquad.answer.AnswerRepository;
import codesquad.util.SessionUtil;
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

    @Autowired
    private AnswerRepository answerRepository;

    @GetMapping("/form")
    public String form(HttpSession session) {
        if (!SessionUtil.isLoginUser(session)) return "redirect:/users/login";

        return "qna/form";
    }

    @PostMapping()
    public String create(String title, String contents, HttpSession session) {
        if (!SessionUtil.isLoginUser(session)) return "redirect:/users/login";

        questionRepository.save(new Question(SessionUtil.getUserFromSesssion(session), title, contents));
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        Question question = questionRepository.findById(id).orElseThrow(IllegalAccessError::new);

        model.addAttribute("question", question);
        return "qna/show";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        if (!permissionCheck(session, id)) return "redirect:/users/login";

        model.addAttribute("question", questionRepository.findById(id).orElseThrow(IllegalAccessError::new));
        return "qna/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, Question newQuestion, HttpSession session) {
        if(!SessionUtil.isLoginUser(session)) return "redirect:/users/login";

        Question question = questionRepository.findById(id).orElseThrow(IllegalAccessError::new);
        question.update(newQuestion,SessionUtil.getUserFromSesssion(session));
        questionRepository.save(question);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        if (!permissionCheck(session, id)) return "redirect:/users/login";

        questionRepository.deleteById(id);
        return "redirect:/";
    }

    private boolean permissionCheck(HttpSession session, Long id) {
        return SessionUtil.permissionCheck(session, questionRepository
                .findById(id)
                .orElseThrow(IllegalAccessError::new)
                .getUser()
        );
    }
    
}
