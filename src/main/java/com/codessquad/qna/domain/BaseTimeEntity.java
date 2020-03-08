package com.codessquad.qna.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

  @CreatedDate
  protected LocalDateTime createdTime;

  @LastModifiedDate
  protected LocalDateTime modifiedTime;

  public String getCreatedTime() {
    return getFormattedTime(createdTime);
  }

  public String getModifiedTime() {
    return getFormattedTime(modifiedTime);
  }

  String getFormattedTime(LocalDateTime localDateTime) {
    return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(localDateTime);
  }
}
