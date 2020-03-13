package io.david215.forum.thread;

import io.david215.forum.user.User;
import io.david215.forum.user.UserController;
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
        Thread thread = findThread(id);
        model.addAttribute("thread", thread);
        return "thread/show";
    }

    private Thread findThread(Long id) {
        Optional<Thread> optionalThread = threadRepository.findById(id);
        return optionalThread.orElseThrow(ThreadNotFoundException::new);
    }

    @ExceptionHandler(ThreadNotFoundException.class)
    private String handleUserNotFoundException() {
        return "user/not-found";
    }

    private static class ThreadNotFoundException extends RuntimeException {
    }
}
