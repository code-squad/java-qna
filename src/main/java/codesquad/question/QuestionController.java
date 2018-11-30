package codesquad.question;

import codesquad.HttpSessionUtils;
import codesquad.user.User;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping("/qna")
public class QuestionController {
    private static final Logger log = getLogger(QuestionController.class);
    @Autowired
    private QnaRepository qnaRepository;

    @GetMapping("/form")
    public String form(HttpSession session) {
        log.debug("질문");
        HttpSessionUtils.getUserFromSession(session);
        return "qna/form";
    }

    @PostMapping("/question")
    public String question(HttpSession session, String contents, String title) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "user/login";
        }
        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        Question question = new Question(sessionUser, title, contents);
        qnaRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String qnaShow(Model model, @PathVariable Long id) {
        model.addAttribute("question", qnaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("질문이 없습니다.")));
        return "qna/show";
    }

    @GetMapping("/updateChange/{id}")
    public String qnaUpdate(@PathVariable Long id, Model model, HttpSession session) {
        Question question = qnaRepository.findById(id).get();
        model.addAttribute("question", question);
        if (!HttpSessionUtils.isValid(session, question)) {
            throw new IllegalStateException("you can't access the other's question");
        }
        return "qna/update";
    }

    @PutMapping("update/{id}")
    public String update(@PathVariable Long id, HttpSession session, Question newQuestion) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "user/login";
        }
        Question question = qnaRepository.findById(id).get();
        question.update(newQuestion);
        qnaRepository.save(question);
        return "redirect:/qna/{id}";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id, HttpSession session, Model model) {
        Question question = qnaRepository.findById(id).get();
        model.addAttribute("question", question);
        if (!HttpSessionUtils.isValid(session, question)) {
            throw new IllegalStateException("you can't delete the other's question");
        }
        qnaRepository.delete(question);
        return "redirect:/";
    }
}