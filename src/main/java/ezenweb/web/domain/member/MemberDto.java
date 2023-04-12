package ezenweb.web.domain.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.time.LocalDateTime;
import java.util.Collection;


// 시큐리티[UserDetails] + 일반 dto
@Data
@AllArgsConstructor@NoArgsConstructor
@Builder
public class MemberDto implements UserDetails {

    private int mno;            // 1. 회원번호
    private String memail;      // 2. 회원 아이디 [ 이메일 ]
    private String mpassword;   // 3. 회원 비밀번호
    private String mname;       // 4. 회원이름
    private String mphone;      // 5. 회원전화번호
    private String mrole;       // 6. 회원등급
    private LocalDateTime cdate;
    private LocalDateTime udate;
    //toEntity
    public MemberEntity toEntity() {
        return MemberEntity.builder()
              .mno(this.mno)
              .memail(this.memail)
              .mpassword(this.mpassword)
              .mname(this.mname)
              .mphone(this.mphone)
              .mrole(this.mrole)
              .build();
    }

    @Override // 인증 된 권한 반환
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override // 패스워드 반환
    public String getPassword() {
        return this.mpassword;
    }

    @Override // 계쩡 반환
    public String getUsername() {
        return this.memail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
