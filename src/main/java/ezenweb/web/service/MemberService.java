package ezenweb.web.service;

import ezenweb.web.domain.member.MemberDto;
import ezenweb.web.domain.member.MemberEntity;
import ezenweb.web.domain.member.MemberEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service // 서비스 레이어
public class MemberService {

    @Autowired
    private MemberEntityRepository memberEntityRepository;


    // 1. 회원가입
    @Transactional
    public boolean write(MemberDto dto){ // 자바 클래스 내 메소드 이름은 중복 불가능
        MemberEntity entity = memberEntityRepository.save(dto.toEntity());
        if(entity.getMno()>0){return true;}
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
