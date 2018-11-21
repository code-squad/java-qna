package codesquad.qna;

import codesquad.HttpSessionUtil;
import codesquad.user.User;
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

    @GetMapping("")
    public String form(HttpSession session) {
        if(!HttpSessionUtil.isLoginUser(session)) {
            return "redirect:/user/login";
        }
        return "/qna/form";
    }

    @PostMapping("")
    public String create(String title, String contents, HttpSession session) {
        if (!HttpSessionUtil.isLoginUser(session)) {
            return "redirect:/user/login";
        }
        User user = HttpSessionUtil.getUserFromSession(session);
        questionRepository.save(new Question(user.getUserId(), title, contents));
        return "redirect:/";
    }

    @GetMapping("/{id}/show")
    public String showQna(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionRepository.findById(id).orElseThrow(NullPointerException::new));
        return "/qna/show";
    }

    @GetMapping("/{id}")
    public String update(@PathVariable Long id, Model model, HttpSession session) {
        if (!HttpSessionUtil.isLoginUser(session)) {
            return "redirect:/user/login";
        }

        User sessionedUser = HttpSessionUtil.getUserFromSession(session);
        Question question = questionRepository.findById(id).orElseThrow(NullPointerException::new);
        if (!sessionedUser.matchUserId(question.getWriter())) {
            throw new IllegalStateException("You can't update the another user");
        }

        model.addAttribute("question", questionRepository.findById(id).orElseThrow(NullPointerException::new));
        return "/qna/updateForm";
    }

    @PutMapping("/{id}")
    public String updateForm(@PathVariable Long id, Question updateQuestion, HttpSession session ) {
        if (!HttpSessionUtil.isLoginUser(session)) {
            return "redirect:/user/login";
        }

        User sessionedUser = HttpSessionUtil.getUserFromSession(session);
        Question question = questionRepository.findById(id).orElseThrow(NullPointerException::new);
        if (!sessionedUser.matchUserId(question.getWriter())) {
            throw new IllegalStateException("You can't update the another user");
        }

        question = questionRepository.findByIdAndWriter(id, sessionedUser.getUserId()).orElseThrow(NullPointerException::new);
        question.update(updateQuestion) ;
        questionRepository.save(question);

        return String.format("redirect:/questions/%d/show", id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        if (!HttpSessionUtil.isLoginUser(session)) {
            return "redirect:/user/login";
        }

        User sessionedUser = HttpSessionUtil.getUserFromSession(session);
        Question question = questionRepository.findById(id).orElseThrow(NullPointerException::new);
        if (!sessionedUser.matchUserId(question.getWriter())) {
            throw new IllegalStateException("You can't update the another user");
        }

        questionRepository.delete(questionRepository.findById(id).orElseThrow(NullPointerException::new));
        return "redirect:/";
    }
}
