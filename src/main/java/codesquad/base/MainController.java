package codesquad.base;

import codesquad.base.qna.QnaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

    @Autowired
    private QnaRepository qnaRepository;

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("quests", qnaRepository.findAll());
        return "/index";
    }



//    @GetMapping("/index/{{index}}")
//    public String show(Model model){
//        model.addAttribute("users", qnaRepository.findAll());
//        return "/index";
//    }


}