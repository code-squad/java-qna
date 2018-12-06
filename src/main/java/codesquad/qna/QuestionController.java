package codesquad.qna;

import codesquad.result.Result;
import codesquad.user.User;
import codesquad.utility.HttpSessionUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping("/qna")
public class QuestionController {
    private static final Logger log = getLogger(QuestionController.class);
    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("")
    public String questionCreate(String title, String contents, HttpSession session) {
        log.debug("질문생성 하기 TEST");
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

    @GetMapping("/{id}/modify")
    public String modifyForm(@PathVariable long id, HttpSession session, Model model) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Optional<Question> maybeQuestion = questionRepository.findById(id).filter(question -> question.isSameWriter(loginUser));
        if (maybeQuestion.isPresent()) {
            model.addAttribute("modifyQuestion", maybeQuestion.get());
            return "/qna/modify";
        }
        return String.format("redirect:/qna/%d", id);

//        Question question = questionRepository.findById(id).orElse(null);
//        Result result = valid(session, question);
//        if (!result.isValid()) {
//            model.addAttribute("errorMessage", result.getErrorMessage());
//            return "/user/login";
//        }
//        model.addAttribute("question", question);
//        return "/qna/modify";
    }

    private Result valid(HttpSession session, Question question) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return Result.fail("로그인이 필요합니다.");
        }
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        if (!question.isSameWriter(loginUser)) {
            return Result.fail("자신이 쓴 글만 수정, 삭제가 가능합니다.");
        }
        return Result.ok();
    }

    @PutMapping("/{id}")
    public String modify(@PathVariable long id, Question newQuestion, HttpSession session) {
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findById(id).get();
        question.update(newQuestion, loginUser);
        questionRepository.save(question);
        return String.format("redirect:/qna/%d", id);
    }

    @GetMapping("/{id}")            //index에서 질문의 제목 눌렀을때
    public String detailContents(@PathVariable long id, Model model, HttpSession session) {
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
    public String delete(@PathVariable long id, Model model, HttpSession session) {
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Optional<Question> maybeQuestion = questionRepository.findById(id).filter(question -> question.isSameWriter(loginUser));
        if (!maybeQuestion.isPresent()) {
            return String.format("redirect:/qna/%d", id);
        }
        Question question = maybeQuestion.get();
        question.isDelete();
        questionRepository.save(question);
        return "redirect:/";
    }
}
