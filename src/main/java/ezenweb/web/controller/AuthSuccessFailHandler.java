package ezenweb.web.controller;

// 인증성공했을때와 실패했을때 시큐리티 컨트롤 재구현(override)
// AuthSuccessFailHandler : 인증 성공했을때에 대한 인터페이스
// onAuthenticationSuccess : 인증이 성공했을때 실행되는 메소드

//AuthenticationFailureHandler : 인증 실패했을때에 대한 인터페이스
// onAuthenticationFailure : 인증이 실패했을때 실행되는 메소드
import com.fasterxml.jackson.databind.ObjectMapper;
import ezenweb.web.domain.member.MemberDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class AuthSuccessFailHandler implements AuthenticationSuccessHandler , AuthenticationFailureHandler {

    //@Autowired 사용 불가
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override // 인수 : request, response, authentication : 인증 성공한 정보
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("authentication : " + authentication.toString());
        MemberDto dto = (MemberDto)authentication.getPrincipal(); // 로그인 성공한 객체
        String json = objectMapper.writeValueAsString(dto);

        // ajax에게 전송
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println(json);
    }


    @Override // 인수 : request, response, exception : 예외 [ 인증 실패한 예외 객체 ]
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info("exception : " + exception.toString());
        String json = objectMapper.writeValueAsString(false);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println(json);
    }
}
