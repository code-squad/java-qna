//package codesquad;
//
//import com.fasterxml.jackson.annotation.JsonProperty;
//import org.springframework.data.annotation.CreatedDate;
//import org.springframework.data.annotation.LastModifiedDate;
//
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.MappedSuperclass;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.Objects;
//
//@MappedSuperclass
//public class AbstractEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @JsonProperty
//    private Long id;
//
//    @CreatedDate
//    private LocalDateTime createDate;
//
//    @LastModifiedDate
//    private LocalDateTime modifiedDate;
//
//    public boolean isMatchId(Long id) {
//        return this.id == id;
//    }
//
//    public String getFormattedCreateDate() {
//        if (createDate == null) {
//            return "";
//        }
//        return createDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof AbstractEntity)) return false;
//        AbstractEntity that = (AbstractEntity) o;
//        return Objects.equals(id, that.id);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id);
//    }
//
//    @Override
//    public String toString() {
//        return "AbstractEntity{" +
//                "id=" + id +
//                ", createDate=" + createDate +
//                ", modifiedDate=" + modifiedDate +
//                '}';
//    }
//}
