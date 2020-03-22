package com.codessquad.qna.repository;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbstractEntity {
    @Id
    @GeneratedValue
    private Long id;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public String getFormattedCreatedAt() {
        return getFormattedDate(createdAt,"yyyy-MM-dd HH:mm:ss");
    }

    public String getFormattedUpdatedAt() {
        return getFormattedDate(updatedAt,"yyyy-MM-dd HH:mm:ss");
    }

    private String getFormattedDate(LocalDateTime dateTime, String pattern) {
        if (dateTime == null)
            return "";
        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0: id.hashCode());
        return result;
    }
}
