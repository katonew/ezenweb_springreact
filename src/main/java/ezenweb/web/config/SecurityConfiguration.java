package ezenweb.web.config;

import ezenweb.web.controller.AuthSuccessFailHandler;
import ezenweb.web.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

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
                .authorizeHttpRequests() // 인증 권한에 따른 요청 제한
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/board/update").hasRole("USER")
                    //.antMatchers("/board/delete").access("hasRole('ADMIN') or hasRole('USER')")
                    .antMatchers("board/delete").hasAnyRole("USER","Admin")
                    .antMatchers("/board/write").hasRole("USER")
                    .antMatchers("/**").permitAll()
                .and()
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
                        .defaultSuccessUrl("/") // 로그인 성공시 URL
                        //.successHandler(authSuccessFailHandler)
                        .userInfoEndpoint()
                        .userService(memberService); // oauth2  서비스를 처리할 서비스 구현

        http.cors(); // cors 정책 사용 선언
        http.csrf().disable(); // csrf 사용해제

    } // configure e

    /*// 스프링 시큐리티에 CORS 정책 설정 [ 리액트[3000]의 요청 받기 위해서 ]
    @Bean // 빈등록
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // 주소
        corsConfiguration.setAllowedMethods(Arrays.asList("HEAD","GET","POST","PUT","DELETE")); // 메소드
        corsConfiguration.setAllowedHeaders(Arrays.asList("Content-Type","Cache-Control","Authorization")); // 설정
        corsConfiguration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",corsConfiguration);
        return source;
    }*/
} // class e
