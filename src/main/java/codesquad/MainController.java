package codesquad;

import codesquad.question.QuestionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    private static final Logger logger = LogManager.getLogger(MainController.class);
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/")
    public String list(Model model) {
        logger.info("main page");
        model.addAttribute("questions",questionRepository.findAllByDeleted(false));
        return "index";
    }
}

