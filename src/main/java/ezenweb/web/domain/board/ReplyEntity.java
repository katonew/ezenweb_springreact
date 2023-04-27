package ezenweb.web.domain.board;

import ezenweb.web.domain.BaseTime;
import ezenweb.web.domain.member.MemberEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity@Table(name="reply")
@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class ReplyEntity extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rno;

    @Column
    private String rcontent;

    @Column
    private int rindex;

    // 게시물 FK
    @ManyToOne // 다수가 하나에게 [ FK ===>PK ]
    @JoinColumn(name="bno") // FK필드명
    @ToString.Exclude // 해당필드는 toString을 사용하지 않음 [ *양방향시 필수 ]
    private BoardEntity boardEntity ;

    // 작성자 FK
    @ManyToOne
    @JoinColumn(name="mno")
    @ToString.Exclude // 해당필드는 toString을 사용하지 않음 [ *양방향시 필수 ]
    private MemberEntity memberEntity;

    public ReplyDto toDto(){
        return ReplyDto.builder()
                .rno(this.rno)
                .rcontent(this.rcontent)
                .rdate(
                    // 만약 작성일이 오늘이면
                    this.cdate.toLocalDate().toString().equals(LocalDateTime.now().toLocalDate().toString() ) ?
                            this.cdate.toLocalTime().format( DateTimeFormatter.ofPattern( "HH:mm:ss") ) :
                            this.cdate.toLocalDate().format( DateTimeFormatter.ofPattern( "yy-MM-dd") )
                )
                .rindex(this.rindex)
                .mname(this.memberEntity.getMname())
                .mno(this.memberEntity.getMno())
                .build();
    }



}
