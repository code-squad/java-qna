package com.codessquad.qna.util;

import com.codessquad.qna.repository.User;
import org.apache.commons.lang3.ObjectUtils;

public class VerifyUtil {
    public static boolean isCorrectPassword(String originPassword, String currentPassword){
        return originPassword.equals(currentPassword);
    }

    public static boolean isCorrectUserFormat(User user) {
        boolean userIdIsExist = ObjectUtils.isNotEmpty(user.getUserId());
        boolean nameIsExist = ObjectUtils.isNotEmpty(user.getName());
        boolean passwordIsExist = ObjectUtils.isNotEmpty(user.getPassword());
        boolean emailIsExist = ObjectUtils.isNotEmpty(user.getEmail());

        return userIdIsExist && nameIsExist && passwordIsExist && emailIsExist;
    }
}
