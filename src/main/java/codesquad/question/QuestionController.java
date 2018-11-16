package codesquad.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("")
    public String post(Question question) {
        question.setTime(getTodayDate());
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/{index}")
    public String detail(@PathVariable Long index, Model model) throws QuestionNotFoundException {
        Question question = questionRepository.findById(index).
                orElseThrow(() -> new QuestionNotFoundException("해당 질문을 찾을 수 없습니다."));
        model.addAttribute("question", question);
        return "qna/show";
    }

    private String getTodayDate() {
        Date today = new Date();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd ");
        SimpleDateFormat time = new SimpleDateFormat("HH:mm");
        return date.format(today) + time.format(today);
    }

}
