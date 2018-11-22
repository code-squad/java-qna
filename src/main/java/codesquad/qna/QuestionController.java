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
        Question newQuestion = new Question(sessionUser.getUserId(), title, contents);
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

    @GetMapping("{writer}/updateForm")
    public String update(@PathVariable String writer,HttpSession session,Model model){
        if (!HttpSessionUtils.isNullLoginUser(session)) {       // 로그인 안한 상태로 수정을 하려면 로그인을 먼저 해야 한다.
            return "/user/login";
        }

        User sessionedUser = HttpSessionUtils.getUserFromSession(session);

        if (!sessionedUser.matchUserId(writer)) {                       // 로그인한 사용자가 다른 사람의 정보를 수정할 수 없다.
            throw new IllegalArgumentException("자신의 정보만 수정할 수 있습니다.");
        }
        model.addAttribute("questionInformation",questionRepository.findByWriter(writer));
        return "/qna/updateForm";
    }

    @GetMapping("/{id}")
    public String DetailContents(@PathVariable Long id, Model model) {
        model.addAttribute("questions", questionRepository.findById(id).orElse(null));
        return "/qna/show";
    }

    @PutMapping("/{writer}")
    public String updatePersonalInformation(@PathVariable String writer, Question updatedQuestion, HttpSession session) {
        if (!HttpSessionUtils.isNullLoginUser(session)) {       // 로그인 안한 상태로 수정을 하려면 로그인을 먼저 해야 한다.
            return "/user/login";
        }

        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if (!sessionedUser.matchUserId(writer)) {                       // 로그인한 사용자가 다른 사람의 정보를 수정할 수 없다.
            throw new IllegalArgumentException("자신의 정보만 수정할 수 있습니다.");
        }

        Question question = questionRepository.findByWriter(writer);
        question.update(updatedQuestion);
        questionRepository.save(question);
        return "redirect:/";
    }

    @DeleteMapping("{writer}/deleteForm")
    public String delete(@PathVariable String writer,HttpSession session){
        if (!HttpSessionUtils.isNullLoginUser(session)) {
            return "/user/login";
        }

        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if (!sessionedUser.matchUserId(writer)) {
            throw new IllegalArgumentException("자신의 정보만 수정할 수 있습니다.");
        }

        Question question = questionRepository.findByWriter(writer);
        questionRepository.delete(question);
        return "redirect:/";
    }


}
