package com.codessquad.qna.web.services;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.domain.UserRepository;
import com.codessquad.qna.exceptions.NotFoundException;
import com.codessquad.qna.exceptions.PermissionDeniedException;
import com.codessquad.qna.exceptions.UnauthorizedException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import static com.codessquad.qna.exceptions.UnauthorizedException.NOT_LOGIN;
import static com.codessquad.qna.web.services.AuthService.AUTHENTICATION_ID;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException(id);
        });
    }

    public void register(User newUser) {
        userRepository.save(newUser);
    }

    public void isOwner(HttpServletRequest request, Long targetUserId) {
        try {
            Long authenticationId = (Long)request.getSession(false).getAttribute(AUTHENTICATION_ID);

            if(!authenticationId.equals(targetUserId)) {
                throw new PermissionDeniedException();
            }
        }catch (NullPointerException exception) {
            throw new UnauthorizedException(NOT_LOGIN);
        }
    }

    public void edit(HttpServletRequest request, Long targetUserId, User newUser) {
        isOwner(request, targetUserId);
        User targetUser = getUserById(targetUserId);
        register(targetUser.merge(newUser));
    }
}
