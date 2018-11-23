package codesquad.question;

import codesquad.HttpSessionUtils;
import codesquad.answer.Answer;
import codesquad.answer.AnswerRepository;
import codesquad.user.User;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RequestMapping("/questions")
@Controller
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping("")
    public String create(Question question, HttpSession session) {
        //set메서드 사용무방...?
        question.setWriter(HttpSessionUtils.getUserFromSession(session));
        questionRepository.save(question);
        return "redirect:/questions";
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "index";
    }

    @GetMapping("/form")
    public String questionForm(HttpSession session) {
        if(HttpSessionUtils.isLoggedInUser(session)) {
            System.out.println(session);
            return "qna/form";
        }

        return "redirect:/user/login";
    }

    @GetMapping("/{id}")
    public String profile(Model model, @PathVariable long id) {
        System.out.println("질문 상세 페이지");
        Question question = questionRepository.findById(id).orElse(null);
        List<Answer> answers = answerRepository.findByQuestionId(id);

        model.addAttribute("answers", answers);
        model.addAttribute("countOfAnswers", answers.size());
        model.addAttribute("question", question);
        return "/qna/show";
    }

//    @GetMapping("/{id}")
//    public String show(@PathVariable long id, Model model) {
//        model.addAttribute("question", questionRepository.findById(id).orElse(null));
//        List<Answer> answers = answerRepository.findByQuestionId(id);
//        model.addAttribute("answers", answers);
//        return "/qna/show";
//    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable long id, Model model, HttpSession session) {
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
        Question question = questionRepository.findById(id).orElse(null);
        questionRepository.findById(id).orElse(null).update(updatedQuestion);

        questionRepository.save(question);
        return "redirect:/questions/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable long id, HttpSession session) {
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