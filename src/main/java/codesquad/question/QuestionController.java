package codesquad.question;

import codesquad.HttpSessionUtils;
import codesquad.answer.Answer;
import codesquad.answer.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping("/questions")
@Controller
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping("")
    public String create(HttpSession session, String title, String contents) {
        System.out.println("create question");

        questionRepository.save(new Question(session, title, contents));
        return "redirect:/questions";
    }

    @GetMapping("")
    public String list(Model model) {
        System.out.println("view question list(home)");

        model.addAttribute("questions", questionRepository.findAll());
        return "index";
    }

    @GetMapping("/form")
    public String questionForm(HttpSession session) {
        System.out.println("view question form");

        if(!HttpSessionUtils.isLoggedInUser(session)) {
            return "redirect:/user/login";
        }

        return "/qna/form";
    }

    @GetMapping("/{id}")
    public String read(@PathVariable long id, Model model) {
        System.out.println("view question");

        Question question = questionRepository.findById(id).orElse(null);
        List<Answer> answers = answerRepository.findByQuestionId(id);

        model.addAttribute("answers", answers);
        model.addAttribute("countOfAnswers", answers.size());
        model.addAttribute("question", question);
        return "/qna/show";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable long id, Model model, HttpSession session) {
        System.out.println("view question update form");

        if(!HttpSessionUtils.isLoggedInUser(session)) {
            return "/user/login";
        }

        Question question = questionRepository.findById(id).orElse(null);
        if(!question.isMatchWriter(HttpSessionUtils.getUserFromSession(session))) {
            return "/qna/update_failed";
        }

        model.addAttribute("question", question);
        return "/qna/update_form";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable long id, Question updatedQuestion) {
        System.out.println("update question");

        Question question = questionRepository.findById(id).orElse(null);
        question.update(updatedQuestion);

        questionRepository.save(question);
        return "redirect:/questions/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable long id, HttpSession session) {
        System.out.println("delete question");

        if(!HttpSessionUtils.isLoggedInUser(session)) {
            return "/user/login";
        }

        Question question = questionRepository.findById(id).orElse(null);
        if(!question.isMatchWriter(HttpSessionUtils.getUserFromSession(session))) {
            return "/qna/update_failed";
        }

        questionRepository.delete(question);
        return "redirect:/questions";
    }
}