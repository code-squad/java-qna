package com.codessquad.qna.web.services;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.domain.UserRepository;
import com.codessquad.qna.exceptions.PermissionDeniedException;
import com.codessquad.qna.exceptions.UnauthorizedException;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static com.codessquad.qna.exceptions.UnauthorizedException.NOT_LOGIN;
import static com.codessquad.qna.exceptions.UnauthorizedException.NO_MATCH_USER;

@Service
public class AuthService {
    public static final String AUTHENTICATION_ID = "authenticationId";
    private static final int AUTHENTICATION_TIME_OUT = 60 * 60;

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validateAuthentication(HttpServletRequest request, User entrant) throws UnauthorizedException {
        /**
         * 함수 파라미터가 Supplier<? extends Throwable>이다.
         * UnauthorizedException 생성자는 UnauthorizedException(String message) 이다.
         * 따라서 UnauthorizedException::new -> Function<String, UnauthorizedException>
         * Function<String, UnauthorizedException> 타입은 Supplier<? extends Throwable>로 캐스팅되지 않기 때문에
         * 아래 코드애서 UnauthorizedException::new 함수를 사용할 수 없다.
         */
        Optional<User> optionalOrigin = userRepository.findByAccountId(entrant.getAccountId());


        User origin = optionalOrigin.orElseThrow(() -> new UnauthorizedException(NO_MATCH_USER));
        origin.verify(entrant);

        HttpSession session = request.getSession(true);
        session.setAttribute(AUTHENTICATION_ID, origin.getId());
        session.setMaxInactiveInterval(AUTHENTICATION_TIME_OUT);
    }

    public void expireAuthentication(HttpServletRequest request, HttpServletResponse response) {
        //TODO NullPointerException이 발생했을 때, 아래 코드는 실행되지 않는다.
        // 고민해 보자.
        HttpSession session = request.getSession(false);
        session.invalidate();

        Cookie cookie = new Cookie("JSESSIONID", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public User getRequester(HttpServletRequest request) throws UnauthorizedException {
        try {
            Long requesterId = (Long) request.getSession(false).getAttribute(AUTHENTICATION_ID);

            return userRepository.findById(requesterId).orElseThrow(() -> new RuntimeException("이런 문제가 가능할까?"));
        } catch (NullPointerException exception) {
            throw new UnauthorizedException(NOT_LOGIN);
        }
    }

    public void hasAuthorization(HttpServletRequest request, User targetUser) throws UnauthorizedException, PermissionDeniedException {
        User requester = getRequester(request);

        if (!requester.equals(targetUser)) {
            throw new PermissionDeniedException();
        }
    }
}
