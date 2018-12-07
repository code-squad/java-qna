package codesquad.domain.user;

import codesquad.domain.user.dao.UserRepository;
import codesquad.domain.util.Result;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping("/api/users")
public class ApiUserController {

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = getLogger(ApiUserController.class);

    @PostMapping("/duplicationCheck")
    public Result isDuplication(String userId) {
        logger.info("중복체크 : " + userId);
        if(userRepository.findByUserId(userId) == null) {
            return Result.fail("등록된 아이디입니다!");
        }
        return Result.ok();
    }

    @PostMapping("")
    public Result create(User user) {
        logger.info("회원가입!");
        try {
            userRepository.save(user);
        } catch (Exception e) {
            logger.info("회원가입 -> 아이디 중복!");
            return Result.fail("이미 등록된 아이디를 입력하셨습니다!");
        }
        return Result.ok();
    }
}
