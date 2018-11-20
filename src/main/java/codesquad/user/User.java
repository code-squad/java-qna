package codesquad.user;

import codesquad.question.Question;

import javax.persistence.*;

@Entity
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    @Column(nullable = false, length = 20)
    private String userId;
    private String password;
    private String name;
    private String email;

    void update(User updated) {
        this.name = updated.name;
        this.password = updated.password;
        this.email = updated.email;
    }

    //시간관계상 널체크무시
    //todo null체크
    public boolean matchPassword(String password) {
        return this.password.equals(password);
    }

    public boolean matchPassword(User other) {
        return this.password.equals(other.password);
    }

//    todo : get메소드 사용안하기 가능?
    public boolean matchQuestionWriter(Question question) {
        return this.userId.equals(question.getWriter());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
