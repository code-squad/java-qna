package io.david215.forum.thread;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class ThreadController {
    Map<Integer, Thread> threads = new HashMap<>();

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("threads", threads.values());
        return "home";
    }

    @PostMapping("/threads")
    public String createNewThread(Thread thread) {
        threads.put(thread.getId(), thread);
        return "redirect:/";
    }

    @GetMapping("/threads/{id}")
    public String show(Model model, @PathVariable int id) {
        if (threads.containsKey(id)) {
            Thread thread = threads.get(id);
            model.addAttribute("thread", thread);
            return "thread/show";
        }
        return "thread/not-found";
    }
}
