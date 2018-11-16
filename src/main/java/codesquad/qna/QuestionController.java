package codesquad.qna;

import codesquad.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/qna")
public class QuestionController {
//    private List<Question> questions = new ArrayList<>();
    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("/create")
    public String create(Question question){
        System.out.println("execute create!!");
        System.out.println("user1 : " + question);
        questionRepository.save(question);
        return "redirect:";        //행위를 하고 다른 페이지를 보여주고 싶을때
    }

    @GetMapping("")
    public String list(Model model){
        model.addAttribute("questions",questionRepository.findAll());
        return "/index";
    }

    @GetMapping("/{id}")
    public String list(@PathVariable int id, Model model){
        model.addAttribute("questions",questionRepository.findById(id).orElse(null));
        return "/qna/show";
    }
}
