package codesquad.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
abstract public class AbstractEntiry {
    private static final String FULL_DATE_FORMAT = "yyyy.MM.dd. HH:mm:ss";

    @Id
    @GeneratedValue
    @JsonProperty
    private Long id;

    @CreatedDate
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    protected AbstractEntiry() {}

    protected AbstractEntiry(String id) {
        // Test에서 id에 null을 주니깐 NFE이 발생해서
        if (id == null) {
            this.id = null;
        }
        if (id != null) {
            this.id = Long.valueOf(id);
        }
    }

    public Long getId() {
        return id;
    }

    public String getFormattedCreateDate() {
        if (createDate == null) {
            return "";
        }
        return createDate.format(DateTimeFormatter.ofPattern(FULL_DATE_FORMAT));
    }

    public String getFormattedModifiedDate() {
        return getFormattedDate(modifiedDate, FULL_DATE_FORMAT);
    }

    private String getFormattedDate(LocalDateTime dateTime, String format) {
        if (dateTime == null) {
            return "";
        }
        return dateTime.format(DateTimeFormatter.ofPattern(format));
    }

    abstract boolean isMatchedUser(User otherUser);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractEntiry that = (AbstractEntiry) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
