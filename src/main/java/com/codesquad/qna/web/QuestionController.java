package com.codesquad.qna.web;

import com.codesquad.qna.domain.Question;
import com.codesquad.qna.domain.QuestionRepostory;
import com.codesquad.qna.domain.User;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    private QuestionRepostory questionRepostory;

    @GetMapping("/form")
    public String moveQnaForm(HttpSession session) {
        //TODL : 로그인한 사용자만 질문하기 화면에 접근
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }
        return "/questions/form";
    }

    @PostMapping("")
    public String saveQuestions(String title, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }

        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        Question question = new Question(sessionUser, title, contents);
        questionRepostory.save(question);
        return "redirect:/questions";
    }

    @GetMapping("/{id}")
    public String viewQuestion(@PathVariable long id, Model model) {
        try {
            model.addAttribute("currentQuestion", questionRepostory.findById(id).orElseThrow(() -> new NotFoundException("자료가 없어용")));
            return "/questions/show";
        } catch (NotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/{id}/modify")
    public String moveUpdateForm(Model model, @PathVariable Long id, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }

        try {
            User sessionUser = HttpSessionUtils.getUserFromSession(session);
            //해당 게시물 정보 가져오기
            Question currentQuestion = questionRepostory.findById(id).orElseThrow(() -> new NotFoundException("게시물 없어욧!!"));
            model.addAttribute("currentQuestion", currentQuestion);
            //TODO : 겍체가 match일을 하도록 수정
            if(!currentQuestion.matchUser(sessionUser)) { //해당 Id 질문글의 writer가 session의 userId와 같은지 확인
                throw new IllegalStateException("자신의 게시글만 수정할 수 있는뎅??");
            }
            return "/questions/updateForm";
        } catch (NotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public String updateQuestion(Question updateQuestion, @PathVariable Long id, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }

        try {
            User sessionUser = HttpSessionUtils.getUserFromSession(session);
            Question currentQuestion = questionRepostory.findById(updateQuestion.getId()).orElseThrow(() -> new NotFoundException("게시물 없어영~!"));

            if (!currentQuestion.matchUser(sessionUser)) {
                throw new IllegalStateException("자신의 게시글만 수정할 수 있는뎅??");
            }

            currentQuestion.update(updateQuestion);
            questionRepostory.save(currentQuestion);
            return "redirect:/questions";
        } catch (NotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public String removeQuestion(Question deleteQuestion, @PathVariable Long id, HttpSession session) {
        if(!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }

        try {
            User sessionUser = HttpSessionUtils.getUserFromSession(session);
            Question currentQuestion = questionRepostory.findById(deleteQuestion.getId()).orElseThrow(() -> new NotFoundException("게시글 없는데요??!!!"));

            if(!currentQuestion.matchUser(sessionUser)) {
                throw new IllegalStateException("자기 글만 삭제할 수 있어요!!!");
            }

            questionRepostory.delete(currentQuestion);
            return "redirect:/questions";
        } catch (NotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("")
    public String viewQnaList(Model model) {
        model.addAttribute("questions", questionRepostory.findAll());
        return "/questions/list";
    }

}
