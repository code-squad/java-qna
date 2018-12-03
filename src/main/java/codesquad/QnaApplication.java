package codesquad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QnaApplication {
    public static void main(String[] args) {
        SpringApplication.run(QnaApplication.class, args);
    }
}

/*
TODO ★★★★★
stream에서 map한 값은 object type인지 확인해보기 Stream, Optional 공부 다시!!!
싱글톤 패턴
HTML CSS HTTP 등
객체지향
get,post,requestmapping
경로지정
자바 빈
put메소드 쓸때 히든태그만 추가하면 되는지...?

redirect를 통해 다른 url로 연결시킬지
바로 html파일을 넘길지

show에서 답변개수 동적으로 변경시켜주기
로그인 안하고 답변하기 버튼 눌렀을떄 에러알림창말고 로그인페이지로 이동하도록(가능하면 문구와 함께...?)
*/

