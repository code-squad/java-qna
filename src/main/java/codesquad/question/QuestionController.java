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
        //글쓴이 입력필드 삭제하려면 로그인한 세션 유저의 name을 question에 set해야하나?
        //현재는 readonly, value는 loggedInUser의 name을 지정
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
    public String show(@PathVariable long id, Model model) {
        model.addAttribute("question", questionRepository.findById(id).orElse(null));
        return "/qna/show";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable long id, Model model, HttpSession session) {
        if(!HttpSessionUtils.isLoggedInUser(session)) {
            return "/user/login";
        }

        //todo 중복제거...?
        Question question = questionRepository.findById(id).orElse(null);

//        if(!HttpSessionUtils.getUserFromSession(session).isMatchName(question.getWriter())) {
//            return "/qna/update_failed";
//        }

        model.addAttribute("question", question);
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
    public String delete(@PathVariable long id, HttpSession session) {
        if(!HttpSessionUtils.isLoggedInUser(session)) {
            return "/user/login";
        }

        User loggedInUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findById(id).orElse(null);

//        if(!loggedInUser.isMatchId(question.getWriter())) {
//            return "/qna/update_failed";
//        }

        questionRepository.delete(question);
        return "redirect:/questions";
    }
}