package ezenweb.web.domain.board;

import ezenweb.web.domain.member.MemberEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity@Table(name="board")
@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class BoardEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int bno;
    @Column
    private String btitle;
    @Column
    private String bcontent;

    // FK(외래키) 설정
    // 카테고리 번호
    @ManyToOne // 다수가 하나에게 [ FK ===>PK ]
    @JoinColumn(name="cno") // FK필드명
    @ToString.Exclude // 해당필드는 toString을 사용하지 않음 [ *양방향시 필수 ]
    private CategoryEntity categoryEntity;

    @ManyToOne
    @JoinColumn(name="mno")
    @ToString.Exclude // 해당필드는 toString을 사용하지 않음 [ *양방향시 필수 ]
    private MemberEntity memberEntity;

    @OneToMany(mappedBy = "boardEntity")
    @Builder.Default
    @ToString.Exclude
    private List<ReplyEntity> replyEntityArrayList = new ArrayList<>();



}
