package codesquad;

import codesquad.qna.QuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/")
    public String index(Model model) {
        logger.trace("이건 안나와야지");
        logger.debug("이건 나와야지");
        model.addAttribute("questions", questionRepository.findAll());
        return "index";
    }

}
