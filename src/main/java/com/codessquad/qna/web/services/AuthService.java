package com.codessquad.qna.web.services;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.domain.UserRepository;
import com.codessquad.qna.exceptions.UnauthorizedException;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
public class AuthService {
    public static final String AUTHENTICATION_ID = "authenticationId";
    private static final int AUTHENTICATION_TIME_OUT = 60 * 60;

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validateAuthentication(HttpServletRequest request, User entrant) throws UnauthorizedException {
        Optional<User> optionalOrigin = userRepository.findByAccountId(entrant.getAccountId());
        User origin = optionalOrigin.orElseThrow(UnauthorizedException::noMatchUser);
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

    public User getRequester(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw UnauthorizedException.notLogin();
        }
        Long id = (Long) session.getAttribute(AUTHENTICATION_ID);

        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("세션에 문제가 발생했어요."));
    }
}
