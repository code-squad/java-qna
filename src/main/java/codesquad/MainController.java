package codesquad;

import codesquad.qna.Question;
import codesquad.qna.QuestionRepository;
import codesquad.util.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class MainController {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/") // ?key=value로 받을 수 있다.
    public String index(Model model, Optional<Integer> page) {
        int pageNum = page.isPresent() ? page.get() - 1 : 0;
        PageRequest pageRequest = new PageRequest(pageNum, PageUtil.DEFAULF_PAGE_SIZE, new Sort(Sort.Direction.DESC, "createDate"));
        Page<Question> result = questionRepository.findAllByDeleted(false, pageRequest);

        model.addAttribute("questions", result.getContent());
        model.addAttribute("pageUtil", new PageUtil(result.getTotalPages(), pageNum + 1));
        return "index";
    }

}
