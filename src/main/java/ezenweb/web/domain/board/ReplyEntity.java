package ezenweb.web.domain.board;

import ezenweb.web.domain.member.MemberEntity;
import lombok.*;

import javax.persistence.*;

@Entity@Table(name="reply")
@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class ReplyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rno;

    @Column
    private String rcontent;

    @Column
    private String rdate;

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





}
