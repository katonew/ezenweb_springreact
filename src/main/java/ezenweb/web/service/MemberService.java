package ezenweb.web.service;

import ezenweb.web.domain.member.MemberDto;
import ezenweb.web.domain.member.MemberEntity;
import ezenweb.web.domain.member.MemberEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service // 서비스 레이어
@Slf4j
public class MemberService implements UserDetailsService {

    // [ 스프링 시큐리티 적용했을때 사용되는 로그인 메소드 ]
    @Override
    public UserDetails loadUserByUsername(String memail) throws UsernameNotFoundException {
        // 1. UserDetailsService 인터페이스 구현
        // 2. loadUserByUsername() 메소드 : 아이디 검증
            // 패스워드 검증 [ 시큐리티 자동 ]
        // 3. 검증 후 세션에 저장 할 dto 반환
        MemberEntity entity = memberEntityRepository.findByMemail(memail);
        if(entity==null){return null;}
        MemberDto dto = entity.toDto();
            // dto 권한(여러개) 넣어주기
        // 1. 권한 목록 만들기
        Set<GrantedAuthority> 권한목록 = new HashSet<>();
        // 2. 권한 객체 만들기 [DB에 존재하는 권한명으로 설정 ]
        SimpleGrantedAuthority 권한명 = new SimpleGrantedAuthority(entity.getMrole());
        // 3. 만든 권한 객체를 권한목록[컬렉션] 에 추가
        권한목록.add(권한명);
        // 4. UserDetails 에 권한 목록 대입
        dto.set권한목록(권한목록);
        log.info("dto :" + dto);
        return dto; // userDetails : 원본 데이터의 검증할 계정, 패스워드 포함
    }


    @Autowired
    private MemberEntityRepository memberEntityRepository;

    // 1. 일반 회원가입 [ 본 어플리케이션에서 가입한 회원 ]
    @Transactional
    public boolean write(MemberDto dto){ // 자바 클래스 내 메소드 이름은 중복 불가능
        // 스프링 시큐리티 에서 제공하는 암호화[사람이 이해하기 어렵고 컴퓨터는 이해할 수 있는 단어] 사용하기
            // DB 내에서 패스워드 감추기 , 정보가 이동하면서 패드워드 노출 방지
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            // 인코더 : 암호화  // 디코더 : 암호화 전으로 원상복구
        log.info("비크립트 암호화 사용 :  " + passwordEncoder.encode("1234"));
        // 입력받은 비밀번호를 암호화 한 후에 다시 dto에 저장
        dto.setMpassword((passwordEncoder.encode(dto.getMpassword())));
        // 일반 회원에는 user 라는 등급 부여
        dto.setMrole("user");
        log.info(dto.getMpassword());
        // dto를 entity로 변환 후 repository로 등록
        MemberEntity entity = memberEntityRepository.save(dto.toEntity());
        if(entity.getMno()>0){return true;}
        return false;
    }

    // ****로그인 [ 시큐리티 사용 시 ]
        // 시큐리티는 API [ 누군가 만들어놓은 메소드 안에서 수정하여 사용 ]

    // *2 로그인 [ 시큐리티 사용 하기 전 ]
    /*

    @Transactional
    public boolean login(MemberDto dto){


        // 1. 이메일로 엔티티 찾기
        MemberEntity entity = memberEntityRepository.findByMemail(dto.getMemail());
        // 2. 찾은 엔티티 안에는 암호화 된 패스워드가 들어있음
            // 엔티티 안에 있는 패스와드와 입력받은 패스워드와 비교
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if(passwordEncoder.matches(dto.getMpassword(),entity.getMpassword())){
            request.getSession().setAttribute("login" , entity.getMemail());
            return true;
        }
        return false;

        // 2. 입력받은 이메일과 패스워드가 동일한 지 한번에 확인
        Optional<MemberEntity> result = memberEntityRepository.findByMemailAndMpassword(dto.getMemail(), dto.getMpassword());
        log.info("result : "+ result.get());
        if(result.isPresent()){
            request.getSession().setAttribute("login" , result.get().getMno());
            return true;
        }

        // 3.
        boolean result = memberEntityRepository.existsByMemailAndMpassword(dto.getMemail(),
                dto.getMpassword());
        if(result){
            request.getSession().setAttribute("login" , dto.getMemail());
            return true;
        }

    }
    */

    // 2. 회원 정보 호출
    @Transactional
    public MemberDto info(){
        // 1. 시큐리티 인증[로그인] 된 UserDetails객체[세션]로 관리 [Spring]
            // SecurityContextHolder : 시큐리티 정보 저장소
            // SecurityContextHolder.getContext() : 시큐리티 저장 된 정보
            // SecurityContextHolder.getContext().getAuthentication() : 인증 정보 호출
        SecurityContextHolder.getContext().getAuthentication(); // 인증 전체 정보 호출
        // log.info("Auth : " + SecurityContextHolder.getContext().getAuthentication());
        // log.info("Principal : " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // 인증 된 회원의 정보 호출
        log.info("o :" + o);
        if(o=="anonymousUser"){return null;}
        // 2. 인증 된 객체 내 회원 정보 [ Principal ] 타입 변환
        return (MemberDto) o; // Object -> MemberDto


        /*
        // 일반 세선으로 로그인 정보를 관리했을때
        String memail = (String)request.getSession().getAttribute(("login"));
        if(memail != null){
            MemberEntity entity = memberEntityRepository.findByMemail(memail);
            return entity.toDto();
        }
        return null;
         */
    }
    // 2-1 세션에 존재하는 정보 제거 == 로그아웃
    /*@Transactional
    public boolean logout(){
        request.getSession().setAttribute("login" , null);
        return true;
    }*/

    // 3. 회원 수정
    @Transactional
    public boolean update(MemberDto dto){
        Optional<MemberEntity> entityOptional = memberEntityRepository.findById((dto.getMno()));
        if(entityOptional.isPresent()){
            MemberEntity entity = entityOptional.get();
            entity.setMname(dto.getMname());
            entity.setMphone(dto.getMphone());
            entity.setMrole(dto.getMrole());
            entity.setMpassword(dto.getMpassword());
            return true;
        }
        return false;
    }
    // 4. 회원 탈퇴
    @Transactional
    public boolean delete(int mno){
        Optional<MemberEntity> entityOptional = memberEntityRepository.findById(mno);
        if(entityOptional.isPresent()){
            memberEntityRepository.delete(entityOptional.get());
            return true;
        }
        return false;
    }


}
