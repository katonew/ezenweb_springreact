package ezenweb.web.controller;

import ezenweb.web.domain.board.BoardDto;
import ezenweb.web.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/board")
public class BoardController {
    @Autowired
    private BoardService boardService;

    // 1. 카테고리 등록
    @PostMapping("/category/write")
    public boolean categoryWrite(@RequestBody BoardDto boarddto) {
        log.info("c categoryWrite boarddto: " + boarddto);
        return  boardService.categoryWrite(boarddto);
    }
    // 2. 게시글 쓰기
    @PostMapping("/write")
    public boolean boardWrite(@RequestBody BoardDto boarddto) {
        log.info("c boardWrite boarddto : " + boarddto);
        return boardService.boardWrite(boarddto);
    }
    // 3. 내가 쓴 게시물 출력
    @GetMapping("/myboards")
    public List<BoardDto> myboards() {
        return boardService.myboards();
    }


}
