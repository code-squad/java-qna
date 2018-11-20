package codesquad.qna;

import codesquad.user.HttpSessionUtils;
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
    public String qnaForm(HttpSession session) {
        User user = (User)session.getAttribute(HttpSessionUtils.USER_SESSION_KEY);
        if(user != null){
            return "qna/form";
        }
        return "user/login";
    }

    @PostMapping("/create")
    public String create(Question question) {
        System.out.println("excute create!");
//        getQuestionRepository().create(question);
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/{index}")
    public String showpage(@PathVariable Long index, Model model) {
        model.addAttribute("list", questionRepository.findById(index).orElse(null));
        return "qna/show";
    }

    @PostMapping("/{questionId}")
    public String modify(Question modifyQuestion, @PathVariable Long questionId, Model model) {
        System.out.println("index : " + questionId);
        Question question = questionRepository.findById(questionId).orElse(null);
        question.update(modifyQuestion);
        questionRepository.save(question);
        model.addAttribute("list", question);
        return "qna/show";
    }

    @PutMapping("/{writer}/modify")
    public String modify(@PathVariable String writer, HttpSession session, Model model, Long id) {
        User user = (User)session.getAttribute(HttpSessionUtils.USER_SESSION_KEY);
        if(user.matchUserId(writer)){
            model.addAttribute("modifyForm", questionRepository.findById(id).orElse(null));
            return "qna/modifyForm";
        }
        return "qna/modify_failed";
    }

    @DeleteMapping("/delete")
    public String delete(String writer, Long questionId, HttpSession session) {
        System.out.println("질문 아이디 : " + writer);
        User user = (User)session.getAttribute(HttpSessionUtils.USER_SESSION_KEY);
        if(user.matchUserId(writer)) {
            Question question = questionRepository.findById(questionId).orElse(null);
            questionRepository.delete(question);
            return "redirect:/";
        }
        return "qna/show";
    }
}
