package codesquad.qna;

import codesquad.HttpSessionUtil;
import codesquad.Result;
import codesquad.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    private static final Logger logger = getLogger(QuestionController.class);


    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("")
    public String form(HttpSession session) {

        logger.trace("TRACE MESSAGE");
        logger.debug("DEBUG MESSAGE");
        logger.info("INFO MESSAGE");
        logger.warn("WARN MESSAGE");
        logger.error("ERROR MESSAGE");



        if(!HttpSessionUtil.isLoginUser(session)) {
            return "/user/login_failed";
        }
        return "/qna/form";
    }

    @PostMapping("")
    public String create(String title, String contents, HttpSession session) {
        if (!HttpSessionUtil.isLoginUser(session)) {
            return "/user/login_failed";
        }
        User user = HttpSessionUtil.getUserFromSession(session);
//        System.out.println("User : " + user );
//        logger.debug("User : {}", user );
        questionRepository.save(new Question(user, title, contents));
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionRepository.findById(id).orElseThrow(NullPointerException::new));
        return "/qna/show";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        Question question = questionRepository.findById(id).orElseThrow(NullPointerException::new);
        Result result = valid(session, question);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "/user/login_failed";
        }

        model.addAttribute("question", question);
        return "/qna/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, String title, String contents, HttpSession session, Model model ) {
        Question question = questionRepository.findById(id).orElseThrow(NullPointerException::new);
        Result result = valid(session, question);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "/user/login_failed";
        }
        question.update(title, contents);
        questionRepository.save(question);
        return "redirect:/questions/" + id;
        //return String.format("redirect:/questions/%d", id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, HttpSession session, Model model) {
        Question question = questionRepository.findById(id).orElseThrow(NullPointerException::new);
        Result result = valid(session, question);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "/user/login_failed";
        }
        if (!question.delete()) {
            model.addAttribute("errorMessage", "작성자가 작성하지 않은 댓글이 있습니다. 삭제가 불가능합니다.");
            return "/user/login_failed";
        }
        question.delete();
        questionRepository.save(question);

        return "redirect:/";
    }

    private Result valid(HttpSession session, Question question) {
        if (!HttpSessionUtil.isLoginUser(session)) {
            return Result.fail("로그인이 필요합니다.");
        }
        User loginUser = HttpSessionUtil.getUserFromSession(session);
        if (!question.isSameWriter(loginUser)) {
            return Result.fail("자신이 쓴 글만 수정, 삭제가 가능합니다.");
        }
        return Result.ok();
    }
}
