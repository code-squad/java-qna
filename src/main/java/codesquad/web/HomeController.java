package codesquad.web;

import codesquad.domain.QuestionRepository;
import com.github.jknack.handlebars.Handlebars;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private static final Logger log =  LoggerFactory.getLogger(HomeController.class);
    
    @Autowired
    QuestionRepository questionRepository;

    @GetMapping({"/", "index"})
    public String home(Model model) {
        model.addAttribute("posts", questionRepository.findAll(new Sort(Sort.Direction.DESC, "id")));
        log.debug("/index/success");
        return "index";
    }
}
