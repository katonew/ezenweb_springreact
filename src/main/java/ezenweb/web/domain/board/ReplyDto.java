package ezenweb.web.domain.board;

import ezenweb.web.domain.member.MemberDto;
import ezenweb.web.domain.member.MemberEntityRepository;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class ReplyDto {
    private int rno;
    private String rcontent;
    private String rdate;
    private int rindex;
    // 회원
    private int mno;
    private String mname;
    // 게시판
    private int bno;


    @Autowired
    MemberEntityRepository memberEntityRepository;
    @Autowired
    BoardEntityRepository boardEntityRepository;

    public ReplyEntity toEntity(){
        return ReplyEntity.builder()
                .rno(this.rno)
                .rcontent(this.rcontent)
                .rindex(this.rindex)
                .rdate(new Date().toString())

                .build();
    }


}
