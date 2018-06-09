package codesquad.web;

import codesquad.domain.PaginationUtil;
import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping({"/", "index"})
    public String home(Model model, @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 5) Pageable pageable, String page) {
        if (page == null) {
            page = "0";
        }
        int currentPageNo = Integer.parseInt(page) + 1;
        log.debug("current page number is {}", currentPageNo);

        Page<Question> questionPage = questionRepository.findAll(pageable);
        model.addAttribute("pageBlock", PaginationUtil.getCurrentPageBlockHTML(currentPageNo, questionPage.getTotalPages()));
        model.addAttribute("posts", questionPage);

        log.debug("/index/success");
        return "index";
    }
}
