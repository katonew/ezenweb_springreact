package ezenweb.web.service;

import ezenweb.web.domain.member.MemberDto;
import ezenweb.web.domain.member.MemberEntity;
import ezenweb.web.domain.member.MemberEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Optional;

@Service // 서비스 레이어
@Slf4j
public class MemberService {

    @Autowired
    private MemberEntityRepository memberEntityRepository;
    @Autowired
    private HttpServletRequest request; // 요청 객체

    // 1. 회원가입
    @Transactional
    public boolean write(MemberDto dto){ // 자바 클래스 내 메소드 이름은 중복 불가능
        MemberEntity entity = memberEntityRepository.save(dto.toEntity());
        if(entity.getMno()>0){return true;}
        return false;
    }
    // *2 로그인 [ 시큐리티 사용 하기 전 ]
    @Transactional
    public boolean login(MemberDto dto){

        /*
        // 1. 이메일로 엔티티 찾기
        MemberEntity entity = memberEntityRepository.findByMemail(dto.getMemail());
        log.info("entity : "+ entity);


        // 2. 패스워드 검증
        if(entity.getMpassword().equals(dto.getMpassword())){
            // == 스택 메모리 내 데이터 비교
            // .equals() 힙 메모리 내 데이터 비교
            // .matches() : 문자열 주어진 패턴 포함 동일여부 체크
            // 입력받은 패스워드와 이메일로 가져온 엔티티의 비밀번호가 일치하면
            // 세션 사용
            request.getSession().setAttribute("login" , entity.getMno());
            return true;
        }
        */
            
        // 2. 입력받은 이메일과 패스워드가 동일한 지 한번에 확인
        Optional<MemberEntity> result = memberEntityRepository.findByMemailAndMpassword(dto.getMemail(), dto.getMpassword());
        log.info("result : "+ result.get());
        if(result.isPresent()){
            request.getSession().setAttribute("login" , result.get().getMno());
            return true;
        }
        return false;
    }

    // 2. 회원 정보 호출
    @Transactional
    public MemberDto info(int mno){
        Optional<MemberEntity> entityOptional = memberEntityRepository.findById(mno);
        if(entityOptional.isPresent()){
            MemberEntity entity = entityOptional.get();
            return entity.toDto();
        }
        return null;
    }
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
