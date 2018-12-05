package codesquad.domain.main;

import codesquad.domain.qna.Question;
import codesquad.domain.qna.dao.QnARepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
public class MainController {

    @Autowired
    private QnARepository qnARepository;

    private static final Logger logger = getLogger(MainController.class);


    @GetMapping("/")
    public String index(Model model){
        logger.info("Home 화면으로 이동");
        model.addAttribute("questions", qnARepository.findByDeleted(false));
        return "/index";
    }
}
