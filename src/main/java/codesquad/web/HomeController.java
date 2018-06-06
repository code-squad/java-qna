package codesquad.web;

import codesquad.domain.QuestionPageable;
import codesquad.domain.QuestionRepository;
import com.github.jknack.handlebars.Handlebars;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private static final Logger log =  LoggerFactory.getLogger(HomeController.class);
    
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping({"/", "index"})
    public String home(Model model, QuestionPageable questionPageable) {
//    public String home(Model model, Pageable pageable) {
//        model.addAttribute("posts", questionRepository.findAll(new Sort(Sort.Direction.DESC, "id")));
        model.addAttribute("posts", questionRepository.findAll(questionPageable));
        log.debug("/index/success");
        return "index";
    }
}
