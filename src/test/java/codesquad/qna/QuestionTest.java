package codesquad.qna;

import codesquad.user.User;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionTest {
    private User user1;
    private User user2;

    private Question question1;
    private Question question2;

    private Answer answer1;
    private Answer answer2;

    @Before
    public void setUp() throws Exception {
        user1 = new User();
        user2 = new User();
        user1.setId(1L);
        user2.setId(2L);

        question1 = new Question(user1, "라임", "라임은바보다");
        question2 = new Question(user1, "안녕", "캬캬캬캬");
        question1.setAnswers(new ArrayList<>());
        question2.setAnswers(new ArrayList<>());

        answer1 = new Answer();
        answer2 = new Answer();
        answer1.setWriter(user2);
        answer2.setWriter(user1);
    }

    @Test
    public void 게시글_작성자_확인() {
        assertThat(question1.isSameWriter(user1)).isEqualTo(true);
    }

    @Test
    public void 게시글_수정() {
        Question updateQuestion = new Question(user1, "변형된라임", "라임은천재다");
        question1.update(updateQuestion, user1);
        assertThat(question1.getWriter()).isEqualTo(user1);
        assertThat(question1.getTitle()).isEqualTo("변형된라임");
        assertThat(question1.getContents()).isEqualTo("라임은천재다");
    }

    @Test
    public void 게시글작성자_아이디만_수정가능() {
        Question updateQuestion = new Question(user1, "변형더된라임", "라임은천재다!!!!");
        question1.update(updateQuestion, user2);
        assertThat(question1.getTitle()).isEqualTo("라임");
    }

    @Test
    public void 로그인_사용자와_게시글작성자가_같을경우_게시글_삭제() {
        question1.deleted(user1);
        assertThat(question1.isDeleted()).isEqualTo(true);
    }

    @Test
    public void 게시글에_다른사용자_댓글있을때_게시글_삭제불가() {
        question2.getAnswers().add(answer1);
        question2.deleted(user1);
        assertThat(question2.isDeleted()).isEqualTo(false);
    }

    @Test
    public void 게시글에_자신의_댓글과_다른사용자_댓글이_섞여있을때_게시글_삭제불가() {
        question1.getAnswers().add(answer2);
        question1.getAnswers().add(answer1);
        question1.deleted(user1);
        assertThat(question1.isDeleted()).isEqualTo(false);
    }

    @Test
    public void 게시글작성자와_댓글작성자가_같을경우_게시글_삭제() {
        question1.getAnswers().add(answer2);
        question1.deleted(user1);
        assertThat(question1.isDeleted()).isEqualTo(true);
    }
}