package com.codessquad.qna;

import com.codessquad.qna.controller.PostsRepository;
import com.codessquad.qna.service.posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexController {
  private final PostsService postsService;

  @GetMapping("/")
  public String index(Model model){
    model.addAttribute("questions", postsService.findAllDesc());
    return "index";
  }
}