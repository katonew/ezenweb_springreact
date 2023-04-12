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
    // 2. 해당 이메일과 비밀번호가 일치한 엔티티 반환
        // 인수로 들어온 meamil과 mpassword가 모두 일치한 엔티티[레코드] 찾아서 존재여부 반환
        // sql : select *from member where meamil =? and mpassword =?;
    Optional<MemberEntity> findByMemailAndMpassword(String memail, String mpassword);
    // 3. [ 중복체크 ] 만약에 동일한 이메일이 존재하면 true 없으면 false
    boolean existsByMemail(String memail);
    // 4. 만약에 동일한 이메일과 패스워드가 존재하면 true 없으면 false
    boolean existsByMemailAndMpassword(String memail, String mpassword);

    // 아이디 찾기 [ 매개변수 : 이름과 전화번호 ]
    Optional<MemberEntity> findByMnameAndMphone(String mname, String mphone);
    // 비밀번호 찾기 [ 매개변수 : 아이디와 전화번호 ]
    boolean existsByMemailAndMphone(String memail, String mphone);

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
        
    검색여부 [ true , false ]
        .existsBy필드명

    Obtional
        MemberEntity
        Optional<MemberEntity>
    검색된 레코드 반환
        Optional<MemberEntity> : 레코드 1개
        List<MemberEntity> : 레코드 여러개
        boolean : 검색결과 [true, false]

*/
