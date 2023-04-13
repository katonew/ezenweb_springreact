package ezenweb.web.controller;

import ezenweb.web.domain.member.MemberDto;
import ezenweb.web.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController // Controller+Responsebody
@Slf4j // 로그가능
@RequestMapping("/member")
public class MemberController {
    @GetMapping("/signup")
    public Resource getSignup(){return new ClassPathResource("templates/member/signup.html");}
    @GetMapping("/login")
    public Resource getLogin(){return new ClassPathResource("templates/member/login.html");}


    // @Autowired 없이 객체[빈] 자동 생성
    // MemberService service = new MemberService();
    // @Autowired 사용할 때 객체[빈] 자동 생성
    @Autowired
    MemberService memberService;

    // 1. 회원가입[C]
    @PostMapping("/info")   // URL 같아도 HTTP 메소드 다르므로 식별 가능
    public boolean write(@RequestBody MemberDto dto){ // 자바 클래스 내 메소드 이름은 중복 불가능
        log.info("member info write : " + dto);
        return memberService.write(dto);
    }
    // 2. 회원정보 호출 [R]
    @GetMapping("/info")
    public MemberDto info(){
        return memberService.info();
    }

    // 3. 회원정보 수정[U]
    @PutMapping("/info")
    public boolean update(@RequestBody MemberDto dto){
        log.info("member info update : " + dto);
        return memberService.update(dto);
    }
    // 4. 회원정보 탈퇴[D]
    @DeleteMapping("/info")
    public boolean delete(@RequestParam int mno){
        log.info("member info delete : " + mno);
        return memberService.delete(mno);
    }



    /*
    // ------------스프링 시큐리티 사용 전----------------- //
    // 로그인
    @PostMapping("/login")
    public boolean login(@RequestBody MemberDto dto){
        return memberService.login(dto);
    }
    */
    // 세션에 존재하는 정보 제거 == 로그아웃
    @GetMapping("/logout")
    public boolean logout(){
        return true;
    }

}
