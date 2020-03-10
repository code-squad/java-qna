package io.david215.forum.thread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

@Controller
public class ThreadController {
    @Autowired
    ThreadRepository threadRepository;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("threads", threadRepository.findAllByOrderByTimeDesc());
        return "home";
    }

    @PostMapping("/threads")
    public String createNewThread(Thread thread) {
        threadRepository.save(thread);
        return "redirect:/";
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public String handleBlank() {
        return "redirect:/post-failed";
    }

    @GetMapping("/threads/{id}")
    public String show(Model model, @PathVariable long id) {
        Optional<Thread> optionalThread = threadRepository.findById(id);
        if (optionalThread.isPresent()) {
            Thread thread = optionalThread.get();
            model.addAttribute("thread", thread);
            return "thread/show";
        }
        return "thread/not-found";
    }
}
