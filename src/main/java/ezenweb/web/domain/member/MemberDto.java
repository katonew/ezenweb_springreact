package ezenweb.web.domain.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Set;


// 시큐리티[UserDetails]  + Oauth2 소셜 회원
@Data
@AllArgsConstructor@NoArgsConstructor
@Builder
public class MemberDto implements UserDetails , OAuth2User {

    private int mno;                            // 1. 회원번호
    private String memail;                      // 2. 회원 아이디 [ 이메일 ]
    private String mpassword;                   // 3. 회원 비밀번호
    private String mname;                       // 4. 회원이름
    private String mphone;                      // 5. 회원전화번호
    private String mrole;                       // 6. 회원등급 [ 가입용 ]
    private Set<GrantedAuthority> 권한목록;     // 7. 회원등급 [ 인증용 ] 스프링 시큐리티
    private Map<String, Object> 소셜회원정보;   // 8. Oauth2 인증 회원정보
    
    // 추가
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
        return 권한목록;
    }

    @Override // 패스워드 반환
    public String getPassword() {
        return this.mpassword;
    }

    @Override // 계정 반환
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


    /*  Oauth2 재정의 */

    @Override //Oauth2 회원 정보
    public Map<String, Object> getAttributes() {
        return this.소셜회원정보;
    }

    @Override // Oauth2 아이디
    public String getName() {
        return this.mname;
    }
}
