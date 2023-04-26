package ezenweb.web.service;

import ezenweb.web.domain.board.*;
import ezenweb.web.domain.member.MemberEntity;
import ezenweb.web.domain.member.MemberEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    }
    @Transactional
    public boolean addReply(ReplyDto dto){
        log.info("replyDto : " + dto);
        BoardEntity boardEntity = boardEntityRepository.findById(dto.getBno()).get();
        MemberEntity memberEntity = memberEntityRepository.findById(dto.getMno()).get();
        ReplyEntity replyEntity = replyEntityRepository.save(dto.toEntity());

        if(replyEntity.getRno()<1){
            return false;
        }


        replyEntity.setBoardEntity(boardEntity);
        boardEntity.getReplyEntityArrayList().add(replyEntity);
        replyEntity.setMemberEntity(memberEntity);
        memberEntity.getReplyEntityArrayList().add(replyEntity);

        log.info("replyEntity : " + replyEntity);
        return true;
    }
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
