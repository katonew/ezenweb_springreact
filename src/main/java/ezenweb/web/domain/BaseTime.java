package ezenweb.web.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) // @EnableJpaAuditing 를 appStart에 필수로 선언
public class BaseTime {
    // DB 생성 시각
    @CreatedDate
    public LocalDateTime cdate;
    // DB 수정 시각
    @LastModifiedDate
    public LocalDateTime udate;
}
