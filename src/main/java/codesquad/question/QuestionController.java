package codesquad.question;

import codesquad.HttpSessionUtils;
import codesquad.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RequestMapping("/questions")
@Controller
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("")
    public String create(Question question) {
        questionRepository.save(question);
        return "redirect:/questions";
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "index";
    }

    //질문하기 버튼 (로그인:질문지 폼으로 / 비로그인:로그인 폼으로)
    @GetMapping("/form")
    public String questionForm(HttpSession session) {
        if(HttpSessionUtils.isLoggedInUser(session)) {
            System.out.println(session);
            return "qna/form";
        }

        return "redirect:/user/login";
    }

    //질문 상세보기 버튼
    @GetMapping("/{id}")
    public String show(Model model, @PathVariable long id) {
        model.addAttribute("question", questionRepository.findById(id).orElse(null));
        return "/qna/show";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable long id, Model model, HttpSession session) {
        if(!HttpSessionUtils.isLoggedInUser(session)) {
            return "/user/login";
        }

        User loggedInUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findById(id).orElse(null);
        model.addAttribute("question", question);

        if(!loggedInUser.isMatchName(question.getWriter())) {
            return "/qna/update_failed";
        }

        return "/qna/update_form";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable long id, Question updatedQuestion) {
        Question question = questionRepository.findById(id).orElse(null);
        question.update(updatedQuestion);
        questionRepository.save(question);

        return "redirect:/questions/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable long id, Model model, HttpSession session) {
        if(!HttpSessionUtils.isLoggedInUser(session)) {
            return "/user/login";
        }

        User loggedInUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findById(id).orElse(null);
        model.addAttribute(question);

        if(!loggedInUser.isMatchName(question.getWriter())) {
            return "/qna/update_failed";
        }

        questionRepository.delete(question);
        return "redirect:/questions";
    }
}