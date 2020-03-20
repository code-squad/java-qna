package com.codessquad.qna;


import com.codessquad.qna.domain.QuestionRepository;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {

    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private QuestionRepository questionRepository;
    private User hyunjun;

    @Test
    public void getBean() {
        UserController bean = applicationContext.getBean(UserController.class);
        assertThat(bean).isNotNull();
    }

    @BeforeEach
    void setUp() {
        hyunjun = new User();
        hyunjun.setId((long) 1);
        hyunjun.setUserId("guswns1659");
        hyunjun.setName("hyunjunb");
        hyunjun.setPassword("pass");
        hyunjun.setEmail("guswns1659@gamil.com");
    }

    //    @GetMapping("/form")
//    public String userForm() {
//        return "user/form";
//    }
    @Test
    void testCreateForm() throws Exception {
        mockMvc.perform(get("/users/form")).andExpect(status().isOk()).andExpect(view().name("user/form"));
    }

    @Test
    void testCreate() throws Exception {
        mockMvc.perform(post("/users/create").requestAttr("user", new User())).andExpect(status().is3xxRedirection());
    }

    @Test
    void testLoginForm() throws Exception {
        mockMvc.perform(get("/users/loginForm")).andExpect(status().isOk()).andExpect(view().name("user/logi"));
    }

    //    @GetMapping("")
//    public String users(Model model) {
//        model.addAttribute("users", userRepository.findAll());
//        return "user/list";
//    }
    @Test
    void testUsers() throws Exception {
        mockMvc.perform(get("/users")).andExpect(status().isOk()).andExpect(model().attribute("users", userRepository.findAll())).andExpect(view().name("user/list"));
    }
    //    @PostMapping("/create")
//    public String create(User user) {
//        userRepository.save(user);
//        return "redirect:/users";
//    }

}
