package codesquad;

import codesquad.qna.questions.Question;
import codesquad.qna.questions.QuestionRepository;
import codesquad.util.PagingUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
public class MainController {
    private static final Logger logger = getLogger(MainController.class);

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/")
    public String start(Model model, @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC, size = 2) Pageable pageable){
        Page<Question> pages =  questionRepository.findAllByDeleted(false, pageable);
        logger.debug("pages Total : {}", pages.getTotalPages());
        logger.debug("curPage : {}", pageable.getPageNumber());
        PagingUtil pageUtil = new PagingUtil(pageable.getPageNumber(), pages.getTotalPages()-1);
        model.addAttribute("pageUtil", pageUtil);
        model.addAttribute("questions", pages);
        return "index";
    }
}
    