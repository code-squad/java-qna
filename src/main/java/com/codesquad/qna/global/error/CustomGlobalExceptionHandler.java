package com.codesquad.qna.global.error;

import com.codesquad.qna.global.error.exception.BusinessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public void springHandleBusinessException(HttpServletResponse response, BusinessException e) throws IOException {
        response.sendError(e.getErrorCode().getStatus());
    }
}
