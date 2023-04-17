package ezenweb.web.service;

import ezenweb.web.domain.board.*;
import ezenweb.web.domain.member.MemberDto;
import ezenweb.web.domain.member.MemberEntity;
import ezenweb.web.domain.member.MemberEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BoardService {
    @Autowired
    private CategoryEntityRepository categoryEntityRepository;
    @Autowired
    private BoardEntityRepository boardEntityRepository;
    @Autowired
    private MemberEntityRepository memberEntityRepository;


    @Transactional
    // 1. 카테고리 등록
    public boolean categoryWrite(BoardDto boarddto) {
        log.info("s categoryWrite boarddto: " + boarddto);
        // 입력받은 cname을 dto에서 카테고리 entity로 형변환 시켜 save
        CategoryEntity entity =
        categoryEntityRepository.save(boarddto.toCategoryEntity());
        // 만약에 생성된 entity의 pk가 1보다 크면 save 성공
        if(entity.getCno()>=1){
            return true;
        }
        return false;
    }

    @Transactional
    // 2. 게시글 쓰기
    public boolean boardWrite(BoardDto boarddto) {
        log.info("s boardWrite boarddto : " + boarddto);
        // 1. 선택된 카테고리 번호를 이용한 카테고리 엔티티 찾기
        Optional<CategoryEntity> categoryEntityOptional =
                categoryEntityRepository.findById(boarddto.getCno());
        // 2.만약에 선택된 카테고리가 존재하지 않으면 리턴
        if(!categoryEntityOptional.isPresent()){return false;}
        // 3. 카테고리 엔티티 추출
        CategoryEntity categoryEntity = categoryEntityOptional.get();

        // 4. 게시물 쓰기
        BoardEntity boardEntity =
        boardEntityRepository.save(boarddto.toBoardEntity());
        if(boardEntity.getBno()<1){return false;}


        // 5. 로그인 된 회원의 엔티티 찾기
            // 1. 인증 된 회원 찾기
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(o.equals("anonymousUser")){
            return false;
        }
            // 2. 형변환
        MemberDto memberDto = (MemberDto)o;
            // 3. 회원 엔티티 찾기
        MemberEntity memberEntity = memberEntityRepository.findByMemail(memberDto.getMemail());


        // 6. 양방향 관계 (게시글<-->카테고리)
            // 1. 카테고리 엔티티에 생성된 게시물 등록
        categoryEntity.getBoardEntityList().add(boardEntity);
            // 2. 생성된 게시글에 카테고리 엔티티 등록
        boardEntity.setCategoryEntity(categoryEntity);

        // 7. 양방향 관계 (게시글<-->멤버)
            // 1. 생성된 게시글 엔티티에 로그인 된 회원 등록
        boardEntity.setMemberEntity(memberEntity);
            // 2. 생성된 게시글에 카테고리 엔티티 등록
        memberEntity.getBoardEntityList().add(boardEntity);

        // 확인
        log.info("board entity : " + boardEntity.toString());


        return true;
    }
    // 3. 내가 쓴 게시물 출력
    public List<BoardDto> myboards() {
        return null;
    }
    // 2. 게시글 쓰기

    // 3. 내가 쓴 게시글 출력

}
