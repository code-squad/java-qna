package com.codessquad.qna.question;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String writer;
    @Column(nullable = false)
    private String comment;
    @Column(nullable = false)
    private LocalDateTime createdDateTime;

}
