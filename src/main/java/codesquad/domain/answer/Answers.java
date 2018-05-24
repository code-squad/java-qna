package codesquad.domain.answer;

import codesquad.domain.question.Question;
import org.hibernate.annotations.Where;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import java.util.List;

@Embeddable
public class Answers {

    @OneToMany(mappedBy = "question", cascade = CascadeType.PERSIST)
    @Where(clause = "deleted = false")
    @OrderBy("id ASC")
    private List<Answer> answers;

    public Answers() {
    }

    public Answers(Answer answer) {
        answers.add(answer);
    }

    public void delete(Question question) {
        answers.forEach(answer -> answer.delete(question));
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public int getSize() {
        return answers.size();
    }
}
