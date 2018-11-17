package codesquad.user;

import javax.persistence.*;

//어노테이션 붙이면 자동으로 디비와 매핑, 데이터를 넣을 때 꺼낼 때
@Entity
public class User {
    //데이터베이스 테이블에는 키가 있어야한다 primary key, 유일한 값
    //어노테이션 Id, 데이터가 추가 될 때마다 자동으로 1씩 증가하면 데이터마다 다른 Id가질수있음
    //그 기능이 Gene~
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    @Column(nullable = false, length = 20)
    private String userId;
    private String password;
    private String name;
    private String email;

    void update(User updated) {
        this.setName(updated.name);
        this.setPassword(updated.password);
        this.setEmail(updated.email);
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
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
