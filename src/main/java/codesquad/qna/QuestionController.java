package codesquad.qna;

import codesquad.user.User;
import codesquad.utility.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

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
        try {
            Question question = questionRepository.findById(id).orElse(null);
            hasPermission(session, question) ;
            model.addAttribute("question", question);
            return "/qna/updateForm";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/users/login";
        }
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

    private boolean hasPermission(HttpSession session, Question question) {
        if (!HttpSessionUtils.isNullLoginUser(session)) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        if (question.isSameWriter(loginUser)) {
            throw new IllegalStateException("자신이 쓴 글만 수정, 삭제가 가능합니다.");
        }
        return true;
    }

    @PutMapping("/{id}")
    public String updatePersonalInformation(@PathVariable long id, Question updatedQuestion, Model model,HttpSession session) {
        try {
            Question question = questionRepository.findById(id).orElse(null);
            hasPermission(session, question) ;
            question.update(updatedQuestion);
            questionRepository.save(question);
            return String.format("redirect:/qna/%d", id);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/users/login";
        }
    }

    @DeleteMapping("{id}/deleteForm")
    public String delete(@PathVariable long id, Model model,HttpSession session) {
        try {
            Question question = questionRepository.findById(id).orElse(null);
            hasPermission(session, question) ;
            questionRepository.delete(question);
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/users/login";
        }

    }
}
