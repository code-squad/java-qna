package com.codessquad.qna.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController {
    @GetMapping("/user_not_found")
    public String goUserNotFoundPage() {
        return "error/user_not_found";
    }

    @GetMapping("/question_not_found")
    public String goQuestionNotFoundPage() {
        return "error/question_not_found";
    }
}
