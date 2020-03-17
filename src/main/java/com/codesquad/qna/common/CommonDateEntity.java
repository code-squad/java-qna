package com.codesquad.qna.common;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class CommonDateEntity {

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public String getFormattedCreatedAt() {
        return createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public String getFormattedModifiedAt() {
        return modifiedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    @Override
    public String toString() {
        return "CommonDateEntity{" +
                "createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                '}';
    }
}
