package com.codessquad.qna.user;

import com.codessquad.qna.commons.CommonUtils;
import com.codessquad.qna.commons.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class ApiUserController {

  @Autowired
  private UserRepository userRepository;

  /**
   * Feat : User 를 생성합니다.
   * Desc :
   * Return : redirect:/users/list
   */
  @PostMapping("")
  public Result create(@RequestBody User user) {
    log.debug("### /api/users");
    log.debug("### " + user.toString());

    //    if (user == null) return Result.fail(HttpStatus.BAD_REQUEST.getReasonPhrase());

    userRepository.save(user);

    return Result.ok();
  }

  /**
   * Feat : User 의 상세 정보를 가져옵니다.
   * Desc : user가 존재하지 않으면 customErrorCode 에 따라 처리합니다.
   * Return : /users/profile
   */
  @GetMapping("/{id}")
  public Result profile(@PathVariable Long id) {
    log.debug("### /api/users/{id}");
    User user = new CommonUtils().getUser(id);
    log.debug("### " + user.toString());
    return Result.ok(user);
  }
}

//2020-03-12 02:31:27:526 DEBUG [http-nio-8080-exec-3] o.s.c.l.LogFormatUtils: POST "/users", parameters={masked}
//    "2020-03-12 02:31:27:527 DEBUG [http-nio-8080-exec-3] o.s.w.s.h.AbstractHandlerMapping: Mapped to com.codessquad.qna.user.UserController#create(User)
//    "2020-03-12 02:31:27:527 DEBUG [http-nio-8080-exec-3] o.s.o.j.s.OpenEntityManagerInViewInterceptor: Opening JPA EntityManager in OpenEntityManagerInViewInterceptor
//    "2020-03-12 02:31:27:528 DEBUG [http-nio-8080-exec-3] o.s.o.j.JpaTransactionManager: Found thread-bound EntityManager [SessionImpl(1782016778<open>)] for JPA transaction
//    "2020-03-12 02:31:27:529 DEBUG [http-nio-8080-exec-3] o.s.t.s.AbstractPlatformTransactionManager: Creating new transaction with name [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]: PROPAGATION_REQUIRED,ISOLATION_DEFAULT
//    "2020-03-12 02:31:27:529 DEBUG [http-nio-8080-exec-3] o.h.e.t.i.TransactionImpl: On TransactionImpl creation, JpaCompliance#isJpaTransactionComplianceEnabled == false
//    "2020-03-12 02:31:27:529 DEBUG [http-nio-8080-exec-3] o.h.e.t.i.TransactionImpl: begin
//    "2020-03-12 02:31:27:529 DEBUG [http-nio-8080-exec-3] o.s.o.j.JpaTransactionManager: Exposing JPA transaction as JDBC [org.springframework.orm.jpa.vendor.HibernateJpaDialect$HibernateConnectionHandle@66d8cda8]

//    "2020-03-12 02:31:27:530 DEBUG [http-nio-8080-exec-3] o.h.e.s.ActionQueue: Executing identity-insert immediately
//    "2020-03-12 02:31:27:531 DEBUG [http-nio-8080-exec-3] o.h.e.j.s.SqlStatementLogger:
//    insert
//    into
//    user
//    (id, email, name, password, user_id)
//    values
//    (null, ?, ?, ?, ?)
//    "Hibernate:
//    insert
//    into
//    user
//    (id, email, name, password, user_id)
//    values
//    (null, ?, ?, ?, ?)
