package ezenweb.web.controller;

import ezenweb.web.domain.board.ReplyDto;
import ezenweb.web.service.ReplyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/reply")
public class ReplyController {

    @Autowired
    private ReplyService replyService;
    /*
    // 댓글 출력
    @GetMapping("")
    public List<ReplyDto> getList(@RequestParam int bno) {
        log.info("bno : " + bno);
        return replyService.getList(bno);
    }*/
    // 댓글 작성
    @PostMapping("")
    public boolean addReply(@RequestBody ReplyDto dto){
        log.info("replyDto : " + dto);
        return replyService.addReply(dto);
    }
    // 댓글 수정
    @PutMapping("")
    public boolean replyUpdate(@RequestBody ReplyDto dto){
        return replyService.replyUpdate(dto);
    }
    
    // 댓글 삭제
    @DeleteMapping("")
    public boolean deleteReply(@RequestParam int rno){
        return replyService.deleteReply(rno);
    }
}
