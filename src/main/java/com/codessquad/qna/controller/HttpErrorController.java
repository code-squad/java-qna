package com.codessquad.qna.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/error")
public class HttpErrorController {
    @GetMapping("/notFond")
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public void sendNotFound() {
    }

    @GetMapping("/badRequest")
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public void sendBadRequest() {
    }

    @GetMapping("/unauthorized")
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public void sendUnauthorized() {
    }
}
