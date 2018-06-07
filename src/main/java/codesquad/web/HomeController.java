package codesquad.web;

import codesquad.domain.PaginationHelper;
import codesquad.domain.Question;
import codesquad.domain.QuestionPageable;
import codesquad.domain.QuestionRepository;
import com.github.jknack.handlebars.Handlebars;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {
    private static final Logger log =  LoggerFactory.getLogger(HomeController.class);
    
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping({"/", "index"})
//    public String home(Model model, QuestionPageable questionPageable) {
    public String home(Model model, @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC, size = 15) Pageable pageable) {
//        model.addAttribute("posts", questionRepository.findAll(new Sort(Sort.Direction.DESC, "id")));
        Page<Question> questionPage = questionRepository.findAll(pageable);
        Handlebars handlebars = new Handlebars();
        handlebars.registerHelpers(new PaginationHelper());
        // helper를 사용하여 pagination을 구현하려는 시도중임
        // 페이지 그룹을 구하여 필요한 그룹만 출력하는 것을 짜는 중
        log.debug("this is home, page is {}", pageable.getPageNumber());
        model.addAttribute("posts", questionPage);
        log.debug("/index/success");
        return "index";
    }
}
