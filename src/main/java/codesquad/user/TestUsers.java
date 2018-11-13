package codesquad.user;

import java.util.ArrayList;
import java.util.List;

public class TestUsers {
    public static List<User> addUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User("brad903", "1234", "브래드", "brad903@naver.com"));
        users.add(new User("leejh903", "1234", "이정현", "leeh903@gmail.com"));
        users.add(new User("abdaf", "1234", "장보고", "seafood@naver.com"));
        users.add(new User("bacddd", "1234", "홍길동", "honghong@naver.com"));
        users.add(new User("mrboo7", "1234", "장영희", "younghee@naver.com"));
        return users;
    }
}
