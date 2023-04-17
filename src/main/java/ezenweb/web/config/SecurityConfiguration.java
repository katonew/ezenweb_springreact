package ezenweb.web.config;

import ezenweb.web.controller.AuthSuccessFailHandler;
import ezenweb.web.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration // 스프링 빈에 등록 [ MVC 컴포넌트 ]
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private MemberService memberService;
    @Autowired
    private AuthSuccessFailHandler authSuccessFailHandler;

    // 인증[로그인] 관련 보안 담당 메소드
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService).passwordEncoder(new BCryptPasswordEncoder());
        // auth.userDetailsService(); : UserDetailsService 가 구현된 서비스 대입
        // .passwordEncoder() : 서비스 안에서 로그인 패스워드 검증 시 사용 된 암호화 객체 대입
    }

    // configure(HttpSecurity http) => http 관련 보안 담당 메소드
    @Override // 재정의 [ 설정 바꾸기 ]
    protected void configure(HttpSecurity http) throws Exception {
        // super.configure(http); super : 부모 클래스 호출
        http
                // 권한에 따른 HTTP GET 요청 제한
                .authorizeRequests() // HTTP 인증 요청
                    .antMatchers("/member/info/mypage") // 인증 시에만 사용할 URL
                        .hasRole("user")    // 위 URL 패턴에 요청 할 수 있는 권한 명
                    .antMatchers(("/admin/**")) //localhost:8080/admin 이하 페이지는 모두 제한
                        .hasRole("admin")
                    //.antMatchers(("/board/write"))// 글쓰기 페이지는 회원만 가능
                    //    .hasRole(("user"))
                    .antMatchers(("/**")) // localhost:8080 이하 페이지는 권한 해제
                        .permitAll() // 권한 해제
                    // 토큰에는 ROLE_USER이지만 앞의 ROLE은 생략
                .and()
                    .csrf() // 사이트 간 요청 위조 [ POST , PUT 은 HTTP 사용 불가능 ]
                        //.disable() // 모든 HTTP에 csrf 해제
                        // 특정 HTTP URL에만 해제
                        .ignoringAntMatchers("/member/info")        // 특정 매핑URL 은 CSRF 무시
                        .ignoringAntMatchers("/member/login")
                        .ignoringAntMatchers("/member/findId")
                        .ignoringAntMatchers("/member/findPw")
                        .ignoringAntMatchers("/board/write")
                        .ignoringAntMatchers("/board/category/write")
                .and() // 기능 추가 할 때 사용되는 메소드
                    .formLogin()
                        .loginPage("/member/login")             // 로그인 페이지로 사용할 매핑 URL
                        .loginProcessingUrl("/member/login")    // 로그인 처리할 매핑 URL
                        //.defaultSuccessUrl("/")                 // 로그인 성공 시 매핑될 URL
                        .successHandler(authSuccessFailHandler)
                        //.failureForwardUrl("/member/login")     // 로그인 실패했을 때 이동 할 매핑  URL
                        .failureHandler(authSuccessFailHandler)
                        .usernameParameter("memail")            // 로그인 시 사용될 계정 아이디의 필드명
                        .passwordParameter("mpassword")         // 로그인 시 사용될 계정 비밀번호의 필드명
                .and()
                    .logout()
                        .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))     // 로그아웃 처리할 매핑 URL
                        .logoutSuccessUrl("/")                                                         // 로그 아웃 성공 시 매핑될 URL
                        .invalidateHttpSession(true)                                                  // 세션 초기화
                .and()
                    .oauth2Login() // 소셜 로그인 설정
                        //.defaultSuccessUrl("/") // 로그인 성공시 URL
                        .successHandler(authSuccessFailHandler)
                        .userInfoEndpoint()
                        .userService(memberService); // oauth2  서비스를 처리할 서비스 구현

    }
}
