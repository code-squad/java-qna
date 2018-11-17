

package codesquad.question;

import codesquad.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;


    @PostMapping("/create")
    public String questions(Question question) {
        System.out.println("AAA");
        questionRepository.save(question);
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
