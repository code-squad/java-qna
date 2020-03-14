package com.codessquad.qna.user;

import com.codessquad.qna.commons.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {

  String url;
  RestTemplate restTemplate;

  @BeforeEach
  void setUp() {
    url = "http://localhost:8080/";
    restTemplate = new RestTemplate();
  }

  public Result makeResult(LinkedHashMap response) {
    Result result = new Result(response.get("valid"), response.get("errorMessage"), response.get("object"));
    return result;
  }

  @Test
  void CREATE_NULL_USER() {
    url += "/api/users";

    //    Result response = restTemplate.getForObject(url, Result.class);
    //    Object response = restTemplate.getForObject(url, Object.class);
    //    Result result = makeResult((LinkedHashMap) response);

    //    assertEquals(true, result.isValid());


    User user = new User((long) 3, "badajigi", "test", "바다지기", "badajigi@slipp.net");
    HttpEntity<User> request = new HttpEntity<>(user);
    Object response = restTemplate.postForObject(url, request, Object.class);
    Result result = makeResult((LinkedHashMap) response);

    assertEquals(true, result.isValid());
  }

  @Test
  void GET_USER_ID_1() {
    url += "/api/users/1";

    //        Result response = restTemplate.getForObject(url, Result.class);
    Object response = restTemplate.getForObject(url, Object.class);
    Result result = makeResult((LinkedHashMap) response);

    System.out.println(result.getObject().toString());
    assertEquals(true, result.isValid());


    //    User user = new User((long) 3, "badajigi", "test", "바다지기", "badajigi@slipp.net");
    //    HttpEntity<User> request = new HttpEntity<>(user);
    //    Object response = restTemplate.postForObject(url, request, Object.class);
    //    Result result = makeResult((LinkedHashMap) response);

    //    assertEquals(true, result.isValid());
  }
}
