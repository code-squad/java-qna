package codesquad.question;

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

    @GetMapping("/form")
    public String questionForm(HttpSession session) {
        if(session.getAttribute("loginUser") != null) {
            System.out.println(session);
            return "qna/form";
        }
        return "redirect:/user/login";
    }

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable long id) {
        model.addAttribute("question", questionRepository.findById(id).orElse(null));
        return "/qna/show";
    }

    //인자3개인데 꼭 필요한 인자들인가? 메서드가 한가지일만 하는가?
    @GetMapping("/{id}/form")
    public String check(Model model, HttpSession session, @PathVariable long id) {
        Question question = questionRepository.findById(id).orElse(null);
        User loginUser = (User)session.getAttribute("loginUser");


        model.addAttribute("question", question);

        //아이디 일치확인 리팩토링
        if(loginUser != null && question.getWriter().equals(loginUser.getName())) {
            return "/qna/update_form";
        }
        return "/qna/update_failed";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable long id, Question updatedQuestion) {
        Question question = questionRepository.findById(id).orElse(null);
        question.update(updatedQuestion);
        questionRepository.save(question);

        return "redirect:/questions/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable long id, HttpSession session) {
        Question question = questionRepository.findById(id).orElse(null);
        User loginUser = (User)session.getAttribute("loginUser");

        //아이디 일치확인 리팩토링
        if(loginUser != null && question.getWriter().equals(loginUser.getName())) {
            questionRepository.delete(question);
            return "redirect:/questions";
        }
        return "/user/login";
    }
}