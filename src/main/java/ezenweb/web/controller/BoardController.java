package ezenweb.web.controller;

import ezenweb.web.domain.board.BoardDto;
import ezenweb.web.domain.board.CategoryDto;
import ezenweb.web.domain.board.PageDto;
import ezenweb.web.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/board")
//@CrossOrigin( origins = "http://localhost:3000")
public class BoardController {

    // 서비스 객체들
    @Autowired
    private BoardService boardService;
    //--------------view 반환 ---------------//

    /*@GetMapping("")
    public Resource list(){
        return new ClassPathResource("templates/board/list.html");
    }
    //--------------Model 반환---------------//
    */
    // 1. 카테고리 등록
    @PostMapping("/category/write")
    public boolean categoryWrite(@RequestBody BoardDto boarddto) {
        log.info("c categoryWrite boarddto: " + boarddto);
        return  boardService.categoryWrite(boarddto);
    }
    // 4. 카테고리 출력 [ (DTO가 없을 경우) 반환타입 : 1. List 2. Map ]
    @GetMapping("/category/list")
    public List<CategoryDto> categoryList(  ){  log.info("c categoryList : " );
        List<CategoryDto> result = boardService.categoryList(  );
        return result;
    }
    // 2. 게시글 쓰기
    @PostMapping("")
    public byte boardWrite(@RequestBody BoardDto boarddto) {
        log.info("c boardWrite boarddto : " + boarddto);
        return boardService.boardWrite(boarddto);
    }
    // 3. 내가 쓴 게시물 출력
    @GetMapping("/myboards")
    public List<BoardDto> myboards() {
        return boardService.myboards();
    }

    // 게시물 출력
    @GetMapping("")
    public PageDto list(PageDto pageDto) {
        return boardService.list(pageDto);
    }
    // 게시물 상세 출력
    @GetMapping("/getboard")
    public BoardDto info(@RequestParam int bno) {
        log.info("bno :" + bno);
        return boardService.info(bno);
    }

    // 게시글 삭제
    @DeleteMapping("")
    public boolean bdelete(@RequestParam int bno) {
        return boardService.bdelete(bno);
    }

    // 게시글 수정
    @PutMapping
    public boolean bUpdate(@RequestBody BoardDto dto){
        return boardService.bupdate(dto);

    }

}
