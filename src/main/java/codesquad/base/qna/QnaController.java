package codesquad.base.qna;

import codesquad.base.user.User;
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
    public String qnaForm(Question question) {
        qnaRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable Long id){
        Question quest = qnaRepository.findById(id).orElseGet(null);
        model.addAttribute("quest",quest);

        return "/qna/show";
    }



}
