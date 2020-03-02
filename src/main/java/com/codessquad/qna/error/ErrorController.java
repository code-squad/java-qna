package com.codessquad.qna.error;

import com.codessquad.qna.constants.ErrorConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController {

    @GetMapping("/user-not-found")
    public String goUserNotFoundPage() {
        return ErrorConstants.ERROR_USER_NOT_FOUND;
    }

    @GetMapping("/question-not-found")
    public String goQuestionNotFoundPage() {
        return ErrorConstants.ERROR_QUESTION_NOT_FOUND;
    }

    @GetMapping("/cannot-edit-other-user-info")
    public String goCannotEditOtherUserInfoPage() {
        return ErrorConstants.ERROR_CANNOT_EDIT_OTHER_USER_INFO;
    }
}
