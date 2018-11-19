package codesquad.Qna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/questions")
public class QnaController {
    @Autowired
    private QnaRepository qnaRepository;


    @PostMapping("")
    public String form(Question quest) {
        qnaRepository.save(quest);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable("id") long id) {
       model.addAttribute("quests", qnaRepository.findById(id).orElseGet(null));
        return "/qna/show";
    }
}
