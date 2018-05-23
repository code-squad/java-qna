package codesquad.web;

import codesquad.domain.model.Question;
import codesquad.domain.repository.AnswerRepository;
import codesquad.domain.repository.QuestionRepository;
import codesquad.domain.model.User;
import codesquad.domain.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static codesquad.domain.utils.HttpSessionUtils.USER_SESSION_KEY;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    private final Logger log = LoggerFactory.getLogger(QuestionController.class);

    @GetMapping("/form")
    public String create(HttpSession session, Model model) {
        Result result = Result.valid(session);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "user/login";
        }
        return "/qna/form";
    }

    @PostMapping("")
    public String question(String title, String contents, HttpSession session, Model model) {
        Result result = Result.valid(session);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "user/login";
        }
        User sessionUser = (User) session.getAttribute(USER_SESSION_KEY);
        Question question = Question.builder()
                .writer(sessionUser)
                .contents(contents)
                .title(title).build();
        log.info("Submit Question {} : ", question.toString());
        questionRepository.save(question);
        return String.format("redirect:/questions/%d", question.getId());
    }

    @GetMapping("/{id}")
    public String showQuestion(@PathVariable("id") Long id, Model model) {
        model.addAttribute("question", questionRepository.findOne(id));
        model.addAttribute("answersLength", answerRepository.findAllByQuestionId(id).size());
        return "qna/show";
    }

    @GetMapping("/{id}/form")
    public String showQuestionUpdatePage(@PathVariable("id") Long id, Model model, HttpSession session) {
        Question question = questionRepository.findById(id).get();
        Result result = Result.valid(session, question);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "user/login";
        }
        model.addAttribute("question", question);
        return "qna/updateForm";
    }

    @PutMapping("/{id}")
    public String updateQuestion(@PathVariable("id") Long id, Question updateQuestion, HttpSession session, Model model) {
        Question question = questionRepository.findById(id).get();
        Result result = Result.valid(session, question);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "user/login";
        }
        question.update(updateQuestion);
        questionRepository.save(question);
        return String.format("redirect:/questions/%d", id);
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable("id") Long id, HttpSession session, Model model) {
        Result result = questionRepository.findById(id)
                .map(q -> Result.valid(session, q))
                .orElseThrow(() -> new RuntimeException("해당하는 id의 질문이 없습니다."));
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "user/login";
        }
        questionRepository.deleteById(id);
        return "redirect:/";
    }
}
