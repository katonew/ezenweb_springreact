package ezenweb.web.service;

import ezenweb.web.domain.board.*;
import ezenweb.web.domain.member.MemberEntity;
import ezenweb.web.domain.member.MemberEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ReplyService {

    @Autowired
    private ReplyEntityRepository replyEntityRepository;
    @Autowired
    private BoardEntityRepository boardEntityRepository;
    @Autowired
    private MemberEntityRepository memberEntityRepository;
    /*
    // 댓글 출력 => 게시물 출력시로 대체
    @Transactional
    public List<ReplyDto> getList(int bno){
        log.info("bno : " + bno);
        List<ReplyDto> result = new ArrayList<>();
        Optional<BoardEntity> optionalBoardEntity = boardEntityRepository.findById(bno);
        if(optionalBoardEntity.isPresent()){
           List<ReplyEntity> replyEntities = optionalBoardEntity.get().getReplyEntityArrayList();
           replyEntities.forEach(o->{
               result.add(o.toDto());
           });
        }
        List<ReplyEntity> entityList =
        replyEntityRepository.findAllByBno("bno");
        if(entityList.isEmpty()){
            entityList.forEach( (o)->{
                result.add(o.toDto());
            });
        }
        log.info("result : " + result);
        return result;
    }*/
    // 댓글 등록
    @Transactional
    public boolean addReply(ReplyDto dto){
        log.info("replyDto : " + dto);
        BoardEntity boardEntity = boardEntityRepository.findById(dto.getBno()).get();
        MemberEntity memberEntity = memberEntityRepository.findById(dto.getMno()).get();
        ReplyEntity replyEntity = replyEntityRepository.save(dto.toEntity());

        if(replyEntity.getRno()<1){
            return false;
        }

        // 댓글과 게시글의 양방향 관계
        replyEntity.setBoardEntity(boardEntity);
        boardEntity.getReplyEntityArrayList().add(replyEntity);
        // 댓글과 회원의 양방향 관계
        replyEntity.setMemberEntity(memberEntity);
        memberEntity.getReplyEntityArrayList().add(replyEntity);

        log.info("replyEntity : " + replyEntity);
        return true;
    }
    // 댓글 수정
    @Transactional
    public boolean replyUpdate(ReplyDto dto){
        Optional<ReplyEntity> optionalReplyEntity = replyEntityRepository.findById(dto.getRno());
        if(optionalReplyEntity.isPresent()){
            optionalReplyEntity.get().setRcontent(dto.getRcontent());
            return true;
        }
        return false;
    }

    // 댓글 삭제
    @Transactional
    public boolean deleteReply(int rno ){
        Optional<ReplyEntity> replyEntityOptional = replyEntityRepository.findById(rno);
        if (replyEntityOptional.isPresent()){
            ReplyEntity replyEntity = replyEntityOptional.get();
            replyEntityRepository.delete(replyEntity);
            return true;
        }
        return false;
    }


}
