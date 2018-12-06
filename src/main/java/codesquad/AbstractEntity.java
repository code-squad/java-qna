package codesquad;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)  //시간 어노테이션을 인식한다. (근데 QnaApplication에서도 @EnableJpaAuditing 설정했잖아?)
public class AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    //db에 자동으로 새로 추가된 데이터의 pId 번호+1
    private long pId;

    @CreatedDate
    private LocalDateTime createDate;

    @LastModifiedDate   //변경사항이 발생할 때의 시간을 자동으로 업데이트
    private LocalDateTime modifiedDate;

    public long getPId() {
        return pId;
    }

    public void setPId(long pId) {
        this.pId = pId;
    }

    public String getDate() {
        return getFormattedDate(createDate, "yyyy-MM-dd HH:mm");
    }

    public String getModifiedDate() {
        return getFormattedDate(modifiedDate, "yyyy-MM-dd HH:mm");
    }

    private String getFormattedDate(LocalDateTime dateTime, String format) {
        if (dateTime == null) {
            return "";
        }
        return dateTime.format(DateTimeFormatter.ofPattern(format));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractEntity that = (AbstractEntity) o;
        return pId == that.pId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pId);
    }
}
