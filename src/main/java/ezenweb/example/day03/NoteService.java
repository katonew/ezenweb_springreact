package ezenweb.example.day03;

// 비지니스 로직 = 실질적인 업무 담당

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service // MVC 서비스
@Slf4j
public class NoteService {

    @Autowired
    NoteEntityRepository noteEntityRepository;

    // 1. 쓰기
    public boolean write(NoteDto dto){
        log.info("service write in " + dto); // dto안에는 nno X
        // 1. DTO --> ENtity로 변환 후 SAVE
        NoteEntity entity = noteEntityRepository.save(dto.toEntity());
        if(entity.getNno()>=0){
            // 레코드가 생성 되었으면 등록성공
            return true;
        }
        return false;
    }
    // 2. 출력 
    public ArrayList<NoteDto> get(){
        log.info("service get in");
        // 1. 모든 Entity 호출
        List<NoteEntity> entityList = noteEntityRepository.findAll();
        // 2. 모든 entity를 형변환
        ArrayList<NoteDto> list = new ArrayList<>();
        entityList.forEach(e->{
            list.add(e.toDto());
        });
        return list;
    }
    // 3. 삭제
    public boolean delete(int nno){
        log.info("service delete in " + nno);
        // 1. 삭제할 식별번호 [ PK ] 를 이용한 entity 검색 [ 검색 성공시 : 엔티티 / 실패시 : null ]
        Optional<NoteEntity> optionalNoteEntity = noteEntityRepository.findById(nno);
        // 2. 포장클래스 <엔티티>
        if(optionalNoteEntity.isPresent()){ // 포장 클래스 내 엔티티가 들어있으면
            NoteEntity noteEntity = optionalNoteEntity.get();
            noteEntityRepository.delete(noteEntity); // 찾은 엔티티를 리포지토리 통해 삭제하기
            return true;
        }
        return false;
    }
    // 4. 수정
    // @Transactional : 해당 메소드내 엔티티 객체 필드의 변화가 있을 경우 실시간으로 commit 처리
    @Transactional // import javax.transaction.Transactional
    public boolean update(NoteDto dto){
        log.info("service update in " + dto);
        // 1. 해당 PK 번호를 사용한 엔티티 검색
        Optional<NoteEntity> optionalNoteEntity = noteEntityRepository.findById( (dto.getNno() ));
        // 2. 포장 클래스
        if(optionalNoteEntity.isPresent()){
            NoteEntity noteEntity = optionalNoteEntity.get(); // 엔티티 꺼내기
            // 3. 객체 내 필드 변경 -> 엔티티객체 내 필드변경 --> 해당 레코드의 필드 값 변경
            noteEntity.setNcontents(dto.getNcontents());
            noteEntityRepository.save(noteEntity);
            return true;
        }
        return true;
    }
}
