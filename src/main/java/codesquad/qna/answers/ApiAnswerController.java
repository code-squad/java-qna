package codesquad.qna.answers;

import codesquad.qna.questions.Question;
import codesquad.qna.questions.QuestionRepository;
import codesquad.user.User;
import codesquad.util.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {
    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    QuestionRepository questionRepository;


    @PostMapping
    public Answer create(@PathVariable Long questionId, String contents, HttpSession session){
        if(!HttpSessionUtils.existLoginUserFromSession(session)) return null;
        User loginUser = HttpSessionUtils.getLoginUserFromSession(session);
        Question theQuestion = questionRepository.findById(questionId).orElse(null);
        Answer theAnswer = new Answer(loginUser, theQuestion, contents);
        return answerRepository.save(theAnswer);
    }

//    @GetMapping("/{id}/form")
//    public String ShowUpdateView(@PathVariable long id, Model model, HttpSession session){
//        if(!HttpSessionUtils.existLoginUserFromSession(session)) return "redirect:/user/login";
//        Answer answer = answerRepository.findById(id).orElse(null);
//        model.addAttribute("answer", answer);
//        if(!answer.matchWriter(HttpSessionUtils.getLoginUserFromSession(session))) {
//            model.addAttribute("question", answer.getQuestion());
//            return "qna/show_failed";
//        }
//        return "qna/answerForm";
//    }
//
//    @PutMapping("/{id}")
//    public String updateAnswer(@PathVariable long id, String contents, HttpSession session, Model model){
//        if(!HttpSessionUtils.existLoginUserFromSession(session)) return "redirect:/user/login";
//        Answer answer = answerRepository.findById(id).orElse(null);
//        try {
//            answer.updateContents(contents, HttpSessionUtils.getLoginUserFromSession(session));
//        } catch (IllegalStateException e){
//            model.addAttribute("errorMessage", e.getMessage());
//            return "qna/modify_failed";
//        }
//        answerRepository.save(answer);
//        return "redirect:..";
//    }
//
    @DeleteMapping("{id}")
    public Boolean deleteAnswer(@PathVariable long id, HttpSession session, Model model){
        if(!HttpSessionUtils.existLoginUserFromSession(session)) return new Boolean(false);
        Answer answer = answerRepository.findById(id).orElse(null);
        try{
            answer.delete(HttpSessionUtils.getLoginUserFromSession(session));
        } catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return new Boolean(false);
        }
        answerRepository.save(answer);
        return new Boolean(true);
    }
}
