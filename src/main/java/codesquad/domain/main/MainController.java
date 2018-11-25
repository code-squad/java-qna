package codesquad.domain.main;

import codesquad.domain.qna.Question;
import codesquad.domain.qna.dao.QnARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private QnARepository qnARepository;

    @GetMapping("/")
    public String index(Model model){
        System.out.println("Home 화면으로 이동");
        model.addAttribute("questions", qnARepository.findAll());
        return "/index";
    }
}
