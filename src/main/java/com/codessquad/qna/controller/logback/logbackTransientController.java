package com.codessquad.qna.controller.logback;

import ch.qos.logback.classic.Logger;
import com.codessquad.qna.controller.users.UsersAPIController;
import com.codessquad.qna.service.users.UsersService;
import com.codessquad.qna.web.dto.users.UsersListResponseDto;
import com.codessquad.qna.web.dto.users.UsersResponseDto;
import java.util.List;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin //what is this?
@RestController
@RequestMapping("/logbacktest")
public class logbackTransientController {

  @Autowired
  UsersService usersService;

  @RequestMapping("/all")
  public List<UsersListResponseDto> getAll() {
    Logger logger = (Logger) LoggerFactory.getLogger(UsersAPIController.class);

    logger.trace("trace -- Hello world.");
    logger.debug("debug -- Hello world.");
    logger.info("info -- Hello world.");
    logger.warn("warn -- Hello world.");
    logger.error("error -- Hello world.");

    return usersService.findAllDesc();
  }

  @RequestMapping("/{id}")
  public UsersResponseDto getPerson(@PathVariable("id") Long id) {
    return usersService.findById(id);
  }
}
