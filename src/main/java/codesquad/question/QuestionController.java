package codesquad.question;

import codesquad.answer.Answer;
import codesquad.answer.AnswerRepository;
import codesquad.exception.Result;
import codesquad.user.User;
import codesquad.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @GetMapping("/form")
    public String form(Model model, HttpSession session) {
        if (isValid(model, session, null)) return "/user/login";
        return "/qna/form";
    }

    @PostMapping("")
    public String create(String title, String contents, Model model, HttpSession session) {
        if (isValid(model, session, null)) return "/user/login";

        User sessionedUser = SessionUtil.getUserFromSession(session);
        Question newQuestion = Question.newInstance(sessionedUser, title, contents);
        questionRepository.save(newQuestion);

        return "redirect:/";
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("questions", questionRepository.findByDeleted(false));
        return "/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable long id, Model model) {
        List<Answer> answers = answerRepository.findByQuestionIdAndDeleted(id, false);

        model.addAttribute("answers", answers);
        model.addAttribute("answersSize", answers.size());
        model.addAttribute("question", questionRepository.findById(id).get());

        return "/qna/show";
    }

    @GetMapping("{id}/form")
    public String updateForm(@PathVariable long id, Model model, HttpSession session) {
        Question question = questionRepository.findById(id).orElse(null);
        if (isValid(model, session, question)) return "/user/login";

        model.addAttribute("question", questionRepository.findById(id).get());

        return "/qna/updatedForm";
    }

    @PutMapping("{id}")
    public String update(@PathVariable long id, Model model, Question updatedQuestion, HttpSession session) {
        Question question = questionRepository.findById(id).orElse(null);
        if (isValid(model, session, question)) return "/user/login";

        question.update(updatedQuestion);
        questionRepository.save(question);

        return "redirect:/questions/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable long id, Model model, HttpSession session) {
        Question question = questionRepository.findById(id).orElse(null);
        if (isValid(model, session, question)) return "/user/login";

        List<Answer> answers = answerRepository.findByQuestionIdAndDeleted(id, false);

        model.addAttribute("answers", answers);
        model.addAttribute("answersSize", answers.size());
        model.addAttribute("question", questionRepository.findById(id).get());
        if (isdeleteValid(model, question)) return "/qna/show";

        questionRepository.save(question);

        return "redirect:/questions";
    }

    private boolean isValid(Model model, HttpSession session, Question question) {
        Result result = valid(session, question);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return true;
        }
        return false;
    }

    private Result valid(HttpSession session, Question question) {
        if (!SessionUtil.isSessionedUser(session)) {
            return Result.fail("You need login");
        }
        if(question == null) {
            return Result.success();
        }
        User sessionedUser = SessionUtil.getUserFromSession(session);
        if (!question.isSameWriter(sessionedUser)) {
            return Result.fail("You can't edit the other user's question");
        }

        return Result.success();
    }

    private boolean isdeleteValid(Model model, Question question) {
        Result result = question.delete();
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return true;
        }

        return false;
    }
}