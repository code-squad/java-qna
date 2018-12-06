//package codesquad.qna;
//
//import codesquad.user.User;
//import org.junit.Before;
//import org.junit.Test;
//
//import static org.assertj.core.api.Java6Assertions.assertThat;
//
//public class QuestionTest {
//    Question question1;
//    User user1;
//
//    @Before
//    public void setUp() throws Exception {
//        user1 = new User();
//        user1.setId(1);
//
//        question1 = new Question(user1,"title","contents");
//
//    }
//
//    @Test
//    public void test(){
//        assertThat(question1.isSameWriter(user1)).isEqualTo(true);
//    }
//}