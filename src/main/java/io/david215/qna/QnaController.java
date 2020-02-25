package io.david215.qna;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Controller
public class QnaController {
    Map<Integer, Question> questions = new HashMap<>();

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("questions", questions.values());
        return "index";
    }

    @PostMapping("/questions/new")
    public String createNewQuestion(Question question) {
        ZonedDateTime time = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        String formattedTime = time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        question.setTime(formattedTime);
        questions.put(question.getId(), question);
        return "redirect:/";
    }

    @GetMapping("/questions/{id}/show")
    public String show(Model model, @PathVariable int id) {
        Question question = questions.get(id);
        model.addAttribute("question", question);
        return "qna/show";
    }
}
