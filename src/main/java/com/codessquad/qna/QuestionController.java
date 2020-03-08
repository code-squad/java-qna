package com.codessquad.qna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;

@Controller
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    ///목록에서 질문하기 눌렀을 때
    @GetMapping("/question/form")
    public String form(HttpSession session) {
         if (!HttpSessionUtil.isLoginUser(session)) {
            System.out.println("sign in first to ask questions");
            return "loginForm";
        }
        return "qna/form";
    }

    ///질문을 작성했을 때 (질문 목록으로 이동)
    @PostMapping("/questions")
    public String ask(String title, String contents, HttpSession session) {
        User writer = HttpSessionUtil.getUserFromSession(session);
        System.out.println(writer);
        Question question = new Question(writer, title, contents);
        questionRepository.save(question);

        return "redirect:/posts";
    }

    /// 목록에서 게시물 클릭했을 때
    @GetMapping("/questions/{postNumber}/contents")
    public String seeQuestions(@PathVariable Long postNumber, Model model) {

        Question question = findQuestion(postNumber);
        model.addAttribute("question", question);
//        model.addAttribute("answers", question.getAnswers());
        System.out.println("Related Comments : "  + answerRepository.findByRelatedPostNumber(postNumber));

        return "QuestionContents";
    }

    /// 질문 수정하기
    @GetMapping("/questions/{postNumber}/form")
    public String editContents(@PathVariable Long postNumber, Model model, HttpSession session) {
        ///질문을 수정할 수 있는 권한 확인 1) 로그인 된 상태인가
        if (!HttpSessionUtil.isLoginUser(session)) {
            return "loginForm";
        }

        ///질문을 수정할 수 있는 권한 확인 2) 질문의 작성자와 로그인한 유저가 일치하는가
        User loggedInUser = HttpSessionUtil.getUserFromSession(session);
        Question question = findQuestion(postNumber);

        if (question.isSameWriter(loggedInUser)) {
            System.out.println("logged in user : " + loggedInUser.getName() + "writer : " + question.getWriter().getName());
            System.out.println("you cannot delete or edit other's post");
            return "redirect:/posts";
        }

        model.addAttribute("question", question);
        return "qna/updateForm";
    }

    @PutMapping("/questions/{postNumber}/edit")
    public String update(@PathVariable Long postNumber, String title, String contents) {
        Question question = findQuestion(postNumber);
        question.updateContents(title, contents);
        questionRepository.save(question);
        return "redirect:/questions/{postNumber}/contents";
    }

    @DeleteMapping("/questions/{postNumber}/delete")
    public String delete(@PathVariable Long postNumber, HttpSession session) {
        ///질문을 삭제할 수 있는 권한 확인 1) 로그인 된 상태인가
        if (!HttpSessionUtil.isLoginUser(session)) {
            return "loginForm";
        }

        ///질문을 삭제할 수 있는 권한 확인 2) 질문의 작성자와 로그인한 유저가 일치하는가
        User loggedInUser = HttpSessionUtil.getUserFromSession(session);
        Question question = findQuestion(postNumber);
        if (question.isSameWriter(loggedInUser)) {
            System.out.println("you cannot delete or edit other's post");
            return "redirect:/posts";
        }

        Question uselessQuestion = questionRepository.findById(postNumber).get();
        questionRepository.delete(uselessQuestion);
        return "redirect:/posts";
    }

    private Question findQuestion(Long postNumber) {
        return questionRepository.findById(postNumber).orElseThrow(() -> new EntityNotFoundException("/error/notFound"));
    }

}
