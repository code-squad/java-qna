package com.codessquad.qna.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/error")
public class HttpErrorController {
    @GetMapping("/notFond")
    @ResponseBody
    public ResponseEntity sendNotFound() {
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/badRequest")
    @ResponseBody
    public ResponseEntity sendbadRequest() {
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

}
