package codesquad.qna;

import codesquad.user.User;
import codesquad.utility.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.swing.text.html.Option;
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

    @GetMapping("/qnaForm")
    public String form(HttpSession session) {
        if (!HttpSessionUtils.isNullLoginUser(session)) {
            return "/user/login";
        }
        return "/qna/form";
    }

    @GetMapping("{id}/updateForm")
    public String update(@PathVariable long id, HttpSession session, Model model) {
        if (!HttpSessionUtils.isNullLoginUser(session)) {
            return "/user/login";
        }
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findById(id).orElse(null);
        if (!question.matchId(loginUser)) {
            return "/users/login";
        }
        model.addAttribute("questionInformation", question);
        return "/qna/updateForm";
    }

    @GetMapping("/{id}")            //index에서 질문하기 눌렀을때
    public String DetailContents(@PathVariable long id, Model model) {
        Question questions = questionRepository.findById(id).orElse(null);
        if (questions == null) {
            return "redirect:/";
        }
        model.addAttribute("questions", questions);
        return "/qna/show";
    }

    @PutMapping("/{id}")
    public String updatePersonalInformation(@PathVariable long id, Question updatedQuestion,HttpSession session) {
        if (!HttpSessionUtils.isNullLoginUser(session)) {       // 로그인 안한 상태로 수정을 하려면 로그인을 먼저 해야 한다.
            return "redirect:/";
        }
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findById(id).orElse(null);
        if (!question.matchId(loginUser)) {                       // 로그인한 사용자가 다른 사람의 정보를 수정할 수 없다.
            return "/users/login";
        }
        question.update(updatedQuestion);
        questionRepository.save(question);
        return String.format("redirect:/qna/%d",id);
    }

    @DeleteMapping("{id}/deleteForm")
    public String delete(@PathVariable long id, HttpSession session) {
        if (!HttpSessionUtils.isNullLoginUser(session)) {
            return "redirect:/";
        }
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findById(id).orElse(null);
        if (!question.matchId(sessionedUser)) {
            return "/users/login";
        }
        questionRepository.delete(question);
        return "redirect:/";
    }
}
