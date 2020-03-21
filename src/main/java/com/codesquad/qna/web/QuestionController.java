package com.codesquad.qna.web;

import com.codesquad.qna.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @GetMapping("/form")
    public String questionForm(HttpSession session, Model model) {
        User writer = HttpSessionUtils.couldGetValidUserFromSession(session);
        model.addAttribute("userName", writer.getUserName());

        return "/qna/form";
    }
    @PostMapping("")
    public String writeQuestion(Question question, HttpSession session) {
        User writer = HttpSessionUtils.couldGetValidUserFromSession(session);
        question.setWriter(writer);
        DatabaseUtils.replaceEscapesToTags(question);
        questionRepository.save(question);
        logger.info("{} 질문글의 등록에 성공 하였습니다.", question);

        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String showQuestion(@PathVariable Long id, Model model) {
        Question selectedQuestion = questionRepository.findById(id).orElseThrow(QuestionNotFoundException::new);
        model.addAttribute("question", selectedQuestion);

        return "qna/show";
    }

    @GetMapping("/{id}/form")
    public String questionUpdateForm(@PathVariable Long id, Model model, HttpSession session) {
        User loginUser = HttpSessionUtils.couldGetValidUserFromSession(session);
        Question selectedQuestion = questionRepository.findById(id).orElseThrow(QuestionNotFoundException::new);

        if (selectedQuestion.isNotSameWriter(loginUser)) {
            throw new UserNotPermittedException();
        }

        DatabaseUtils.replaceTagsToEscapes(selectedQuestion);
        model.addAttribute("question", selectedQuestion);

        return "qna/updateForm";
    }

    @PutMapping("/{id}")
    public String updateQuestion(@PathVariable Long id, Question updatedQuestion, HttpSession session) {
        User writer = HttpSessionUtils.couldGetValidUserFromSession(session);
        Question selectedQuestion = questionRepository.findById(id).orElseThrow(QuestionNotFoundException::new);

        if (selectedQuestion.isNotSameWriter(writer)) {
            throw new UserNotPermittedException();
        }

        selectedQuestion.update(updatedQuestion);
        DatabaseUtils.replaceEscapesToTags(selectedQuestion);
        questionRepository.save(selectedQuestion);
        logger.info("{} 질문글 수정에 성공 하였습니다.", selectedQuestion);

        return "redirect:/questions/{id}";
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable Long id, HttpSession session) {
        User writer = HttpSessionUtils.couldGetValidUserFromSession(session);
        Question selectedQuestion = questionRepository.findById(id).orElseThrow(QuestionNotFoundException::new);

        if (selectedQuestion.isNotSameWriter(writer)) {
            throw new UserNotPermittedException();
        }

        logger.info("{} 질문글 삭제에 성공 하였습니다.", selectedQuestion);
        questionRepository.delete(selectedQuestion);

        return "redirect:/";

    }
}
