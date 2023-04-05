package ezenweb.example.day03;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController // MVC 컨트롤러
@Slf4j // 로그
@RequestMapping("/note") // 공통 URL
public class NoteController {
    // 지역변수에서는 선언 불가
    @Autowired // 생성자 자동 주입 [ * 단 스프링 컨테이너에 등록이 되어 있는 경우 ]
    NoteService noteservice;

    /* --------------- RESTful API -------------------- */
    // 1. 쓰기
    @PostMapping("/write")
    public boolean write(@RequestBody NoteDto dto){
        // @Autowired
        boolean result = noteservice.write(dto);
        return result;
    }
    // 2. 출력
    @GetMapping("/get")
    public ArrayList<NoteDto> get(){
        ArrayList<NoteDto> result = noteservice.get();
        return result;
    }
    // 3. 삭제
    @DeleteMapping("/delete")
    public boolean delete(@RequestParam int nno){
        boolean result = noteservice.delete(nno);
        return result;
    }
    // 4. 수정
    @PutMapping("/update")
    public boolean update(@RequestBody NoteDto dto){
        boolean result = noteservice.update(dto);
        return result;
    }
}
