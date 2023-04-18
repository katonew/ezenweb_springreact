package ezenweb.web.domain.board;

import ezenweb.web.domain.BaseTime;
import ezenweb.web.domain.member.MemberEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity@Table(name="board")
@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class BoardEntity extends BaseTime {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int bno; // PK 게시물 번호
    @Column(nullable = false) // null 불가
    private String btitle; // 게시글 제목
    @Column(columnDefinition = "longtext")
    private String bcontent; // 게시물 내용 [mysql 자료형 longtext 선택 ]

    @Column
    @ColumnDefault("0") // 기본값 0
    private int bview; // 조회수
    
    // 작성일, 수정일 상속받아 사용

    // FK(외래키) 설정 [ 카테고리번호 = cno , 회원번호 = mno ]
    
    
    
    // 카테고리 번호
    @ManyToOne // 다수가 하나에게 [ FK ===>PK ]
    @JoinColumn(name="cno") // FK필드명
    @ToString.Exclude // 해당필드는 toString을 사용하지 않음 [ *양방향시 필수 ]
    private CategoryEntity categoryEntity;

    // 회원번호
    @ManyToOne
    @JoinColumn(name="mno")
    @ToString.Exclude // 해당필드는 toString을 사용하지 않음 [ *양방향시 필수 ]
    private MemberEntity memberEntity;
    
    
    // PK 양방향 [ 해당 댓글 엔티티의 FK 필드와 매핑 ]
    // 댓글 목록
    @OneToMany(mappedBy = "boardEntity")
    @Builder.Default
    @ToString.Exclude
    private List<ReplyEntity> replyEntityArrayList = new ArrayList<>();

    // 출력용 entity todto
    public BoardDto toDto(){
        return BoardDto.builder()
                .bno(this.bno)
                .btitle(this.btitle)
                .bcontent(this.bcontent)
                .cno(this.getCategoryEntity().getCno())
                .cname(this.getCategoryEntity().getCname())
                .mno(this.getMemberEntity().getMno())
                .mname(this.getMemberEntity().getMname())
                .bview(this.bview)
                .bdate(
                        // 만약 작성일이 오늘이면
                        this.cdate.toLocalDate().toString().equals(LocalDateTime.now().toLocalDate().toString() ) ?
                                this.cdate.toLocalTime().format( DateTimeFormatter.ofPattern( "HH:mm:ss") ) :
                                this.cdate.toLocalDate().format( DateTimeFormatter.ofPattern( "yy-MM-dd") )
                )
                .build();
    }

}
