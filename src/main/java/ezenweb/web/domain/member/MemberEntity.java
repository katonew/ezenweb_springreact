package ezenweb.web.domain.member;

import ezenweb.web.domain.BaseTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "member")
public class MemberEntity extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mno;            // 1. 회원번호
    @Column
    private String memail;      // 2. 회원 아이디 [ 이메일 ]
    @Column
    private String mpassword;   // 3. 회원 비밀번호
    @Column
    private String mname;       // 4. 회원이름
    @Column
    private String mphone;      // 5. 회원전화번호
    @Column
    private String mrole;       // 6. 회원등급/권한 명
    // toDto출력용
    public MemberDto toDto() {
        return MemberDto.builder()
              .mno(this.mno)
              .memail(this.memail)
              .mpassword(this.mpassword)
              .mname(this.mname)
              .mphone(this.mphone)
              .mrole(this.mrole)
              .cdate(this.cdate)
              .udate(this.udate)
              .build();
    }
}
