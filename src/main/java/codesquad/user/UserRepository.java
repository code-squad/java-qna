package codesquad.user;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private UserRepository() {
    }

    private static class Holder {
        public static final List<User> users = new ArrayList<>();

        static {  // for test
            users.add(new User(1, "brad903", "1234", "브래드", "brad903@naver.com"));
            users.add(new User(2, "leejh903", "1234", "이정현", "leeh903@gmail.com"));
            users.add(new User(3, "abdaf", "1234", "장보고", "seafood@naver.com"));
            users.add(new User(4, "bacddd", "1234", "홍길동", "honghong@naver.com"));
            users.add(new User(5, "mrboo7", "1234", "장영희", "younghee@naver.com"));
        }
    }

    public static List<User> getInstance() {
        return UserRepository.Holder.users;
    }
}
