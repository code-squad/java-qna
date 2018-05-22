package codesquad.web;

import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;
import codesquad.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    private static final Logger log =  LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    QuestionRepository questionRepository;

    @PostMapping()
    public String questions(String title, String contents, HttpSession session) {
        if (!SessionUtils.isLoginUser(session)) {
            return "/users/loginForm";
        }

        User sessoinUser = SessionUtils.getUserFromSession(session);
        Question newQuestion = new Question(sessoinUser, title, contents);
        questionRepository.save(newQuestion);

        return "redirect:/";
    }

    @GetMapping("/form")
    public String form(HttpSession session) {
        if (!SessionUtils.isLoginUser(session)) {
            return "/users/loginForm";
        }

        log.debug("/questions/form/success");
        return "/qna/form";
    }

    @GetMapping("/{id}")
    public String showQuestion(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionRepository.findOne(id));

        return "/qna/show";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, HttpSession session, Model model) {
        // 글쓴이랑 현재 수정하려고 접근한 세션 유저의 아이디랑 일치해야 한다.
        User editingUser = SessionUtils.getUserFromSession(session);
        Question updateQuestion = questionRepository.findOne(id);

        if (!editingUser.isMatchedUserId(updateQuestion)) {
            throw new IllegalStateException("question.id.mismatch");
        }
        model.addAttribute("editedQuestion", updateQuestion);
        //일치하면 수정 페이지로 들어가자.
        return "/qna/updateForm";
    }

    @PutMapping("/{id}/update")
    public String update(@PathVariable Long id, Question updateQuestion, HttpSession session) {
        User editingUser = SessionUtils.getUserFromSession(session);
        Question oldQuestion = questionRepository.findOne(id);

        log.debug("updateQuestion : {}", updateQuestion);
        if (!editingUser.isMatchedUserId(updateQuestion)) {
            throw new IllegalStateException("question.id.mismatch");
        }
        if (!oldQuestion.update(updateQuestion)) {
            throw new IllegalStateException("question.update.fail");
        }

        questionRepository.save(oldQuestion);

        return "redirect:/questions/{id}";
    }

    @DeleteMapping("/{id}/form")
    public String delete(@PathVariable Long id, HttpSession session) {
        User deletingUser = SessionUtils.getUserFromSession(session);
        Question deleteQuestion = questionRepository.findOne(id);

        if (!SessionUtils.isLoginUser(session)) {
            return "/users/loginForm";
        }
        if (!deletingUser.isMatchedUserId(deleteQuestion)) {
            throw new IllegalStateException("question.id.mismatch");
        }
        questionRepository.delete(id);

        return "redirect:/";
    }
}