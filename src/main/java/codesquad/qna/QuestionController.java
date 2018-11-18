package codesquad.qna;

import codesquad.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/qna")
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("")
    public String questionCreate(Question question){
        questionRepository.save(question);
        return "redirect:/";        //행위를 하고 다른 페이지를 보여주고 싶을때
    }

    @GetMapping("/{id}")
    public String DetailContents(@PathVariable int id, Model model){
        model.addAttribute("questions",questionRepository.findById(id).orElse(null));
        return "/qna/show";
    }
}
