package ezenweb.example.day04.domain.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass // 상속을 주는 클래스로 이용 [ 부모클래스의 필드는 자식클래스가 사용가능 ]
@EntityListeners(AuditingEntityListener.class) // 엔티티를 실시간 감시
public class BaseTime {
    @CreatedDate
    private LocalDateTime cdate; // 레코드 생성날짜

    @LastModifiedDate
    private LocalDateTime udate; // 레코드 수정날짜

}