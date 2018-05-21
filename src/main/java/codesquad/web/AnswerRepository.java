package codesquad.web;

import org.springframework.data.jpa.repository.JpaRepository;

interface AnswerRepository extends JpaRepository<Answer, Long>{
    Answer findByQuestionId(Long questionsId);
}
