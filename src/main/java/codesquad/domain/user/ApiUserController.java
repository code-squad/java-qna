package codesquad.domain.user;

import codesquad.domain.user.dao.UserRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping("/api/user/")
public class ApiUserController {

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = getLogger(ApiUserController.class);

    @PostMapping("/duplicationCheck")
    @ResponseBody
    public Boolean isDuplication(String userId) {
        logger.info("중복체크 : " + userId);
        if(userRepository.findByUserId(userId) == null) {
            return false;
        }
        return true;
    }
}
