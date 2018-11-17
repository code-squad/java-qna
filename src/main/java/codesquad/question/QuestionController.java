package codesquad.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping("/qna")
public class QuestionController {

    @Autowired
    private QnaRepository qnaRepository;

    @PostMapping("/qna/question")
    public String question(Question question) {
        qnaRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/qna/{id}")
    public String qnaShow(Model model, @PathVariable Long id) {
        model.addAttribute("question", qnaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("질문이 없습니다.")));
        return "qna/show";
    }
}
