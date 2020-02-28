package com.codessquad.qna.error;

import com.codessquad.qna.common.CommonConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController {

    @GetMapping("/user-not-found")
    public String goUserNotFoundPage() {
        return CommonConstants.ERROR_USER_NOT_FOUND;
    }

    @GetMapping("/question-not-found")
    public String goQuestionNotFoundPage() {
        return CommonConstants.ERROR_QUESTION_NOT_FOUND;
    }

    @GetMapping("/cannot-edit-other-user-info")
    public String goCannotEditOtherUserInfoPage() {
        return CommonConstants.ERROR_CANNOT_EDIT_OTHER_USER_INFO;
    }
}
