package ezenweb.example.day03;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// 객체=레코드, 테이블=클래스 ==> DB 객체화 하기
@Entity // 테이블과 해당 클래스 객체간 매핑[연결]
@Table(name = "note")
@Data // getter, setter, tostring 포함
@AllArgsConstructor@NoArgsConstructor@Builder
public class NoteEntity {
    @Id // primary key // JPA 사용시 1개 이상 필수
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int nno;
    @Column
    private  String ncontents;

    // entity --> DTO [ 서비스에서 사용 ]
    public NoteDto toDto(){
        return NoteDto.builder()
                .nno(this.nno)
                .ncontents(this.ncontents)
                .build();
    }
}
