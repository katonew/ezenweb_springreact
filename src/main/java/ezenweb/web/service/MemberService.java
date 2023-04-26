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
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.*;

// UserDetailsService : 일반유저 서비스
// OAuth2UserService : oauth2 유저 서비스
// OAuth2UserService<OAuth2UserRequest, OAuth2User> : loadUser 구현

@Service // 서비스 레이어
@Slf4j
public class MemberService implements UserDetailsService, OAuth2UserService<OAuth2UserRequest, OAuth2User> {


    // Oauth2 로그인 유저 가 로그인 했을때의 메소드
    @Override // 토큰결과 [ JSON VS Map ]
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        // 1. 인증[로그인] 결과 토큰 확인
        OAuth2UserService oAuth2UserService = new DefaultOAuth2UserService();
        log.info("SNS에서 넘어온 정보 : "+ oAuth2UserService.loadUser(userRequest));

        // 2. 전달받은 정보 객체
        // !!!! oauth 로그인한 유저의 정보  => oAuth2User.getAttributes()
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);
            log.info(("회원 정보 : " + oAuth2User.getAttributes()));
            
        // 3. 클라이언트id 식별 [ 구글인지 네이버인지 카카오인지 ]
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        log.info("클라이언트 ID : " + registrationId);
        String email = null;
        String name = null;
        if(registrationId.equals("kakao")){ // 만약 카카오 회원이면
            Map<String,Object> kakao_account = (Map<String,Object>) oAuth2User.getAttributes().get("kakao_account");
            Map<String,Object> profile = (Map<String,Object>) kakao_account.get("profile");
            email = (String)kakao_account.get("email");
            name = (String)profile.get("nickname");
            log.info("email : " + email);
            log.info("name : " + name);
            log.info("kakao_account : " + kakao_account);
        }else if (registrationId.equals("naver")) { // 만약에 네이버 회원이면
            Map<String , Object> response = (Map<String, Object>) oAuth2User.getAttributes().get("response");
            email = (String)response.get("email");
            name = (String)response.get("nickname");
        }else if (registrationId.equals("google")) { // 만약에 구글 회원이면
            email = (String)oAuth2User.getAttributes().get("email");          // 구글의 이메일 호출
            name = (String)oAuth2User.getAttributes().get("name");            // 구글의 이름 호출
        }
        // 인가 객체 [ Oauth2User --->MemberDto 통합DTO(일반+oauth2)]
        MemberDto dto = new MemberDto();
        dto.set소셜회원정보(oAuth2User.getAttributes());
        dto.setMemail(email);
        dto.setMname(name);
        Set<GrantedAuthority> 권한목록 = new HashSet<>();
        SimpleGrantedAuthority 권한 = new SimpleGrantedAuthority("ROLE_user");
        권한목록.add(권한);
        dto.set권한목록(권한목록);

        // 1. DB저장 전에 해당 이메일로 된 이메일이 존재하는지 검사
        MemberEntity entity =  memberEntityRepository.findByMemail(email);
        if(entity==null){ // 첫 방문
            // DB처리 [첫 방문시에만 / 두번째부터는 DB 수정 ]
            dto.setMrole("oauth2user"); // DB에 저장할 권한명
            memberEntityRepository.save(dto.toEntity());
        }else{ // 두번째 방문 이상 수정 처리
            entity.setMname(name);
        }
        dto.setMno(entity.getMno()); // 위에 생성된 혹은 검색된 엔티티의 회원번호
        return dto;
    }


    // [ 스프링 시큐리티 적용했을때 사용되는 일반 유저 로그인 메소드 ]
    @Override
    public UserDetails loadUserByUsername(String memail) throws UsernameNotFoundException {
        // 1. UserDetailsService 인터페이스 구현
        // 2. loadUserByUsername() 메소드 : 아이디 검증
            // 패스워드 검증 [ 시큐리티 자동 ]
        // 3. 검증 후 세션에 저장 할 dto 반환
        MemberEntity entity = memberEntityRepository.findByMemail(memail);
        if(entity==null){throw new UsernameNotFoundException("해당 계정이 없습니다");}
        MemberDto dto = entity.toDto();
            // dto 권한(여러개) 넣어주기
        // 1. 권한 목록 만들기
        Set<GrantedAuthority> 권한목록 = new HashSet<>();
        // 2. 권한 객체 만들기 [DB에 존재하는 권한명(!! ROLE_권한명 )으로 설정 ]
        // 권한 없을경우 : ROLE_ANONYMOUS , 권한 있을경우 ROLE_USER , ROLE_ADMIN 등
        SimpleGrantedAuthority 권한명 = new SimpleGrantedAuthority("ROLE_"+entity.getMrole());
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
    public int write(MemberDto dto){ // 자바 클래스 내 메소드 이름은 중복 불가능
        // 아이디 중복체크
        MemberEntity checkEntity = memberEntityRepository.findByMemail(dto.getMemail());
        if(checkEntity!=null){return 3;}
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
        if(entity.getMno()>0){return 1;}
        return 2;
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
        log.info("Auth : " + SecurityContextHolder.getContext().getAuthentication());
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
        SecurityContextHolder.getContext().getAuthentication(); // 인증 전체 정보 호출
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int mno = ((MemberDto)o).getMno();
        dto.setMno(mno);
        Optional<MemberEntity> entityOptional = memberEntityRepository.findById((dto.getMno()));
        if(entityOptional.isPresent()){
            MemberEntity entity = entityOptional.get();
            entity.setMname(dto.getMname());
            entity.setMphone(dto.getMphone());
            return true;
        }
        return false;
    }
    // 4. 회원 탈퇴
    @Transactional
    public boolean delete(String mpassword){
        SecurityContextHolder.getContext().getAuthentication(); // 인증 전체 정보 호출
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int mno = ((MemberDto)o).getMno();
        Optional<MemberEntity> entityOptional = memberEntityRepository.findById(mno);
        if(entityOptional.isPresent()){
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            boolean result = passwordEncoder.matches(mpassword,entityOptional.get().getMpassword());
            if(result){
                memberEntityRepository.delete(entityOptional.get());
            }
            return result;
        }
        return false;
    }

    // 아이디 찾기 이름 전화번호 일치할 경우 아이디 알려주기
    @Transactional
    public String findId(MemberDto dto){
        log.info("dto : " + dto);
        Optional<MemberEntity> entityOptional = memberEntityRepository.findByMnameAndMphone(dto.getMname(), dto.getMphone());
        if(entityOptional.isPresent()){
            log.info(entityOptional.get().getMemail());
            return entityOptional.get().getMemail();
        }
        return null;
    }
    // 비밀번호 찾기 아이디와 전화번호 일치할 경우 임시 비민번호 변경 후 알려주기
    @Transactional
    public int findPw(MemberDto dto){
        log.info("dto : " + dto);
        boolean result = memberEntityRepository.existsByMemailAndMphone(dto.getMemail(), dto.getMphone());
        if(result){
            MemberEntity entity = memberEntityRepository.findByMemail(dto.getMemail());
            Random rand = new Random();
            int randomNum = rand.nextInt(900000) + 100000;
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            entity.setMpassword(passwordEncoder.encode(String.valueOf(randomNum)));
            return randomNum;
        }
        return 0;
    }
    // 회원 정보 수정 이름 전화번호 변경
    // 회원 탈퇴 [ 비밀번호 재입력 받아 일치할 경우 탈퇴

    // 5. 아이디 중복 확인
    public boolean idcheck( String memail ){
        return memberEntityRepository.existsByMemail( memail  );
    }


}
