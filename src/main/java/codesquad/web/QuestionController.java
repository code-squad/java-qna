package codesquad.web;

import codesquad.domain.QnA;
import codesquad.domain.QnARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("questions")
public class QuestionController {
    @Autowired
    QnARepository qnARepository;

    @GetMapping("/form")
    public String getForm() {
        return "/qna/form";
    }

    @PostMapping("")
    public String create(QnA qna) {
        qnARepository.save(qna);
        return "redirect:/questions";
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("questions", qnARepository.findAll());
        return "qna/list";
    }

    @GetMapping("/{id}")
    public String getQnA(@PathVariable Long id, Model model) {
        model.addAttribute("questions", qnARepository.findOne(id));
        return "/qna/show";
    }

    @GetMapping("{id}/form")
    public String updateForm(@PathVariable Long id, Model model) {
        QnA qna = qnARepository.findOne(id);
        model.addAttribute("qna", qna);
        return "/qna/updateForm";
    }

    @PutMapping("/{id}")
    public String editPost(@PathVariable Long id, QnA newQna) {
        QnA qna = qnARepository.findOne(id);
        qna.update(newQna);
        qnARepository.save(qna);
        return "redirect:/questions";
    }
}
