package codesquad.qna;

import codesquad.user.User;
import codesquad.utility.HttpSessionUtils;
import jdk.nashorn.internal.ir.Optimistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/qna")
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("")
    public String questionCreate(String title, String contents, HttpSession session) {
        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        Question newQuestion = new Question(sessionUser, title, contents);
        questionRepository.save(newQuestion);
        return "redirect:/";        //행위를 하고 다른 페이지를 보여주고 싶을때
    }

    @GetMapping("")
    public String form(HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/user/login";
        }
        return "/qna/form";
    }

    @GetMapping("{id}/update")
    public String update(@PathVariable long id, HttpSession session, Model model) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Optional<Question> maybeQuestion = questionRepository.findById(id).filter(question -> question.isSameWriter(loginUser));
        if (maybeQuestion.isPresent()){
            model.addAttribute("updateQuestion",maybeQuestion.get());
            return "/qna/update";
        }
        return String.format("redirect:/qna/%d",id);
    }

    @PutMapping("{id}")
    public String updateForm(@PathVariable long id,Question newQuestion){
        Question question = questionRepository.findById(id).get();
        question.update(newQuestion);
        questionRepository.save(question);
        return String.format("redirect:/qna/%d",id);
    }

    @GetMapping("/{id}")            //index에서 질문제목 눌렀을때
    public String DetailContents(@PathVariable long id, Model model,HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }
        Optional<Question> maybeQuestions = questionRepository.findById(id);
        if (!maybeQuestions.isPresent()) {
            return "redirect:/";
        }
        model.addAttribute("questions", maybeQuestions.get());
        return "/qna/show";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable long id, Model model,HttpSession session) {
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Optional<Question> maybeQuestion = questionRepository.findById(id).filter(question -> question.isSameWriter(loginUser));
        if (!maybeQuestion.isPresent()){
            return String.format("redirect:/qna/%d",id);
        }
        questionRepository.delete(maybeQuestion.get());
        return "redirect:/";
    }
}
