package com.codesquad.qna.web;

import com.codesquad.qna.domain.Question;
import com.codesquad.qna.domain.QuestionRepository;
import com.codesquad.qna.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    // 질문하기 눌렀을 때, 로그인 유무를 체크하여, 비 로그인 상태인 경우 로그인페이지로, 로그인 상태인 경우 질문 페이지로 이동
    @GetMapping("/form")
    public String create(HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/user/login";
        }
        return "/qna/form";
    }

    // 질문을 작성하고 나서, 해당 질문을 QuestionRepo에 저장하도록 하였습니다.
    // session에서 loginuser를 가져와서 해당 user의 name으로 글이 작성되도록 하였습니다.
    // 질문 작성 완료 후, HOME_DIR로 이동하여 전체 질문 리스트를 볼 수 있도록 하였습니다.
    @PostMapping("/form")
    public String make(String title, String contents, HttpSession session) {
        User loginUser = HttpSessionUtils.getUserFromSession(session).orElse(null);
        Question question = new Question(loginUser, title, contents);
        questionRepository.save(question);
        return HomeController.HOME_DIRECTORY;
    }

    // 질문 title을 눌렀을 때, 질문 상세보기 페이지로 이동하게 하였습니다.
    // {id}값으로 이동하기 때문에, questionRepo에서 id값의 질문을 가져와서 view에 전달하도록 하였습니다.
    @GetMapping("/{id}")
    public ModelAndView showDetail(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("/qna/show");
        modelAndView.addObject("question", questionRepository.getOne(id));
        System.out.println(questionRepository.getOne(id));
        return modelAndView;
    }

    // 질문 수정 버튼을 눌렀을 때, 수정하기 페이지로 이동하게 하였습니다.
    // 로그인하지 않은 유저의 경우에는 login페이지로 이동하게 하였고, 다른 사용자의 경우 403페이지로 이동합니다.
    // 수정을 하기 위해서 기존의 질문값이 필요하기 때문에 질문을 가져와서 전달하도록 하였습니다.
    @GetMapping("/{id}/updateForm")
    public String updateForm(@PathVariable Long id, HttpSession session, Model model) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/user/login";
        }
        User loginUser = HttpSessionUtils.getUserFromSession(session).orElse(null);
        Question question = questionRepository.getOne(id);
        if (question.authorizeUser(loginUser)) {
            model.addAttribute("question", question);
            return "/qna/update_form";
        }
        return HomeController.NOT_AUTHORIZE_DIRECTORY;
    }

    // 질문을 수정하고 난 뒤에, put매핑을 통해서 전달받은 값을 저장소에 저장하도록 하였습니다.
    @PutMapping("/{id}")
    public String updateQuestion(@PathVariable Long id, String title, String contents) {
        Question question = questionRepository.getOne(id);
        question.updateQuestion(title, contents);
        questionRepository.save(question);
        return HomeController.HOME_DIRECTORY;
    }

    // 질문 삭제를 할 때
    // 1. 사용자 일치 여부 확인 2. 댓글 작성자 일치 여부 확인 두개를 통과하면 질문을 삭제 가능하게 구현하였습니다.
    // 질문을 Repo에서 삭제하는것이 아니라, boolean값을 통해서 home화면에서 출력이 안되게 구현하였습니다.
    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable Long id, HttpSession session) {
        Question question = questionRepository.getOne(id);
        User loginUser = HttpSessionUtils.getUserFromSession(session).orElse(null);
        if (question.authorizeUser(loginUser)) {
            if (question.answerWriterCheck()) {
                return HomeController.NOT_AUTHORIZE_DIRECTORY;
            }
            question.deletQuestion();
            questionRepository.save(question);
            return HomeController.HOME_DIRECTORY;
        }
        return HomeController.NOT_AUTHORIZE_DIRECTORY;
    }
}
