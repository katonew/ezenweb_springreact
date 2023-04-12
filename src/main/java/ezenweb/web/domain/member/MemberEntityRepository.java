package ezenweb.web.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberEntityRepository extends JpaRepository<MemberEntity, Integer> {

    // 1. 해당 이메일로 엔티티 찾기
        // 인수로 들어온 email과 동일한 엔티티[레코드] 찾아서 반환
        // sql : select *from member where meamil = ?;
    MemberEntity findByMemail(String memail);
    // 2. 해당 이메일과 비밀번호가 일치한 엔티티 존재 여부 확인
        // 인수로 들어온 meamil과 mpassword가 모두 일치한 엔티티[레코드] 찾아서 존재여부 반환
        // sql : select *from member where meamil =? and mpassword =?;
    Optional<MemberEntity> findByMemailAndMpassword(String memail, String mpassword);


}

/*
    .findById(pk값)     : 해당하는 pk값을 검색 후 존재하면 레코드[엔티티] 반환
    .findAll()         : 모든 레코드[엔티티] 반환
    .save(엔티티)      : 해당 엔티티를 DB레코드에 insert
    .delete(엔티티) : 해당 엔티티를 DB레코드에서 delete
    @Transactional --> setter 메소드 이용 : 수정
    ----------------- 그 외 추가 메소드 만들기
    검색

        .findBy필드(인수)       --> select *from member where meamil = ?;
        .findBy필드명And필드명  --> select *from member where meamil = ? and mpassword =?;
        .findBy필드명or필드명   --> select *from member where  meamil = ? or mpassword =?;


*/
