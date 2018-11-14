package codesquad.base;

import codesquad.Question;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QnaController {

    List<Question> quests = new ArrayList<>();

    @PostMapping("/questions")
    public String form(Question quest) {
        quest.setIndex(quests.size());
        quests.add(quest);
        return "redirect:/";
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("quests", quests);

        System.out.println("index" + quests);
        return "/index";
    }

    @GetMapping("/index/{index}")
    public String show(Model model, @PathVariable("index") int index) {
        for (Question quest : quests) {
            if (quest.getIndex() == index) {
                model.addAttribute("quest", quest);
                System.out.println("show" + quest);
            }
        }
        return "/qna/show";
    }
}
