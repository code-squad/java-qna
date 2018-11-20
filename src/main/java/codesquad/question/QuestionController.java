

package codesquad.question;

import codesquad.HttpSessionUtils;
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


    @GetMapping("/form")
    public String questions(HttpSession session,Model model) {
        System.out.println("질문하기");
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }
        model.addAttribute("User",HttpSessionUtils.getUserFormSession(session));
        return "/qna/form";
    }

    @PostMapping("")
    public String questions(String title,String contents, HttpSession session) {
        System.out.println("qna 인스턴스 생성");
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }
        User sessionUser = HttpSessionUtils.getUserFormSession(session);
        Question newQuestion = new Question(sessionUser.getUserId(),title,contents);

        questionRepository.save(newQuestion);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String profile(Model model, @PathVariable long id) {
        Question question = questionRepository.findById(id).orElse(null);
        model.addAttribute("question", question);
        return "/qna/show";
    }

    @GetMapping("/{id}/form")
    public String updateForm(Model model, @PathVariable long id) {
        System.out.println("수정");
        Question question = questionRepository.findById(id).orElse(null);
        model.addAttribute("question", question);
        return "qna/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable long id, Question newQuestion) {
        System.out.println("업데이트");
        Question question = questionRepository.findById(id).orElse(null);
        question.update(newQuestion);
        questionRepository.save(question);
        return "redirect:/";
    }

}
