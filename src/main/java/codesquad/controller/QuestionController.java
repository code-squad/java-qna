package codesquad.controller;

import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepo;

    @GetMapping("/")
    public String showQuestions(Model model) {
        model.addAttribute("questions", questionRepo.findAll(Sort.by(Sort.Order.desc("id"))));
        return "index";
    }

    @PostMapping("/questions")
    public String create(Question question) {
        try {
            questionRepo.save(question);
            return "redirect:/";
        } catch (DataAccessException e) {
            /* error 처리 컨트롤러로 리다이렉트 시켜야함 : 새로운 요청으로 만들어서 이전 form 데이터값 없애기 */
            System.out.println(e.getMessage());
            return "redirect:/error/db";
        }
    }

    @GetMapping("/questions/{id}")
    public String show(Model model, @PathVariable("id") Long id) {
        Optional<Question> optionalQuestion = questionRepo.findById(id);
        if (!optionalQuestion.isPresent()) {
            System.out.println("존재하지않는 게시글임");
            return "redirect:/error/db";
        }
        model.addAttribute("question", optionalQuestion.get());
        return "/question/show";
    }
}
