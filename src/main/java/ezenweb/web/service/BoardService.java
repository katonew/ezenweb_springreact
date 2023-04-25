package ezenweb.web.service;

import ezenweb.web.domain.board.*;
import ezenweb.web.domain.member.MemberDto;
import ezenweb.web.domain.member.MemberEntity;
import ezenweb.web.domain.member.MemberEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

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
    public byte boardWrite(BoardDto boarddto) {
        log.info("s boardWrite boarddto : " + boarddto);
        // 1. 선택된 카테고리 번호를 이용한 카테고리 엔티티 찾기
        Optional<CategoryEntity> categoryEntityOptional =
                categoryEntityRepository.findById(boarddto.getCno());
        if(!categoryEntityOptional.isPresent()){return 1;} // 만약에 선택된 카테고리가 존재하지 않으면 리턴
        CategoryEntity categoryEntity = categoryEntityOptional.get();  // 카테고리 엔티티 추출

        // 2. 로그인 된 회원의 엔티티 찾기
            // 1. 인증 된 회원 찾기
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(o.equals("anonymousUser")){
            return 2;
        }
            // 2. 형변환
        MemberDto memberDto = (MemberDto)o;
            // 3. 회원 엔티티 찾기
        MemberEntity memberEntity = memberEntityRepository.findByMemail(memberDto.getMemail());


        // 3. 게시물 DB 등록
        BoardEntity boardEntity =
        boardEntityRepository.save(boarddto.toBoardEntity());
        if(boardEntity.getBno()<1){return 3;}




        // 4.. 양방향 관계 (게시글<-->카테고리)
            // 1. 카테고리 엔티티에 생성된 게시물 등록
        categoryEntity.getBoardEntityList().add(boardEntity);
            // 2. 생성된 게시글에 카테고리 엔티티 등록
        boardEntity.setCategoryEntity(categoryEntity);

        // 4. 양방향 관계 (게시글<-->멤버)
            // 1. 생성된 게시글 엔티티에 로그인 된 회원 등록
        boardEntity.setMemberEntity(memberEntity);
            // 2. 생성된 게시글에 카테고리 엔티티 등록
        memberEntity.getBoardEntityList().add(boardEntity);

        // 공지사항 게시물 정보 확인
        //Optional<CategoryEntity> optionalCategory = categoryEntityRepository.findById(1);
        //log.info("공지사항 정보 확인 : " + optionalCategory.get());

        // 확인
        log.info("board entity : " + boardEntity.toString());
        return 4;
    }
    // 3. 내가 쓴 게시물 출력
    public List<BoardDto> myboards() {
        List<BoardDto> list = new ArrayList<>();
        // 1. 로그인 인증 세션 Object--> dto 강제형변환
        MemberDto memberDto = (MemberDto)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // 2. 회원 엔티티 찾아 엔티티 안의 board entity 리스트로 담아 반환
        memberEntityRepository.findByMemail(memberDto.getMemail()).getBoardEntityList().forEach((e)->{
            list.add(e.toDto());
        });
        return list;
    }

    // 카테고리 출력
    @Transactional
    public List<CategoryDto> categoryList(  ){    log.info("s categoryList : " );
        List<CategoryEntity> categoryEntityList = categoryEntityRepository.findAll();

        List<CategoryDto> list = new ArrayList<>();
        categoryEntityList.forEach( (e)->{
            list.add( new CategoryDto( e.getCno() , e.getCname()) );
        });
        return list;
    }
    
    // 4. 카테고리 별 게시물 출력
    @Transactional
    public PageDto list( int cno, int page) {
        log.info("c list cno : " + cno + "page : " + page);
        // 1. pageable 인터페이스 [페이징처리 관련 api]
        // Pageable import domain
        Pageable pageable = PageRequest.of(page-1, 5, Sort.by(Sort.Direction.DESC, "bno"));
                // PageRequest.of(페이지번호[0부터시작], 페이지당 표시개수, 정렬방식[Sort.by])
                    // Sort.by(Sort.Direction.ACS,DESC, ' 정렬기준필드명')
        Page<BoardEntity> entityPage = boardEntityRepository.findBySearch(cno,pageable);
        //
        List<BoardDto> boardDtoList = new ArrayList<>();
        entityPage.forEach( (b)->{
            boardDtoList.add(b.toDto());
        });
        log.info("총 게시물 수 : " + entityPage.getTotalElements());
        log.info("총 페이지 수 : " + entityPage.getTotalPages());
        return PageDto.builder()
                .boardDtoList(boardDtoList)
                .totalCount(entityPage.getTotalElements())
                .totalPage(entityPage.getTotalPages())
                .cno(cno)
                .page(page)
                .build();
    }

    // 게시물 상세 출력
    @Transactional
    public BoardDto info(int bno) {
        return boardEntityRepository.findById(bno).get().toDto();
    }

    // 게시글 삭제
    @Transactional
    public boolean bdelete(int bno) {
        BoardEntity boardEntity = boardEntityRepository.findById(bno).get();
        boardEntityRepository.delete(boardEntity);
        return true;
    }



}
