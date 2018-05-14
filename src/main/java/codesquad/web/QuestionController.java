package codesquad.web;

import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("questions")
public class QuestionController {
    @Autowired
    QuestionRepository qnaRepository;

    @GetMapping("/form")
    public String getForm() {
        return "/qna/form";
    }

    @PostMapping("")
    public String create(Question question) {
        qnaRepository.save(question);
        return "redirect:/questions";
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("questions", qnaRepository.findAll());
        return "qna/list";
    }

    @GetMapping("/{id}")
    public String getQnA(@PathVariable Long id, Model model) {
        model.addAttribute("questions", qnaRepository.findOne(id));
        return "/qna/show";
    }

    @GetMapping("{id}/form")
    public String updateForm(@PathVariable Long id, Model model) {
        Question qna = qnaRepository.findOne(id);
        model.addAttribute("qna", qna);
        return "/qna/updateForm";
    }

    @PutMapping("/{id}")
    public String editPost(@PathVariable Long id, Question newQna) {
        Question qna = qnaRepository.findOne(id);
        qna.update(newQna);
        qnaRepository.save(qna);
        return "redirect:/questions";
    }
}
