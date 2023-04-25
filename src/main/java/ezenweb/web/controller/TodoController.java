package ezenweb.web.controller;

import ezenweb.web.domain.todo.TodoDto;
import ezenweb.web.domain.todo.TodoPageDto;
import ezenweb.web.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/todo")
//@CrossOrigin(origins = {"http://localhost:3000","http://192.168.17.54:3000"}) // 해당 컨트롤러는 해당 URL의 요청을 CORS 정책 적용
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping("/getTodo")
    public TodoPageDto get(@RequestParam int page) {
        // 서비스 구현 후 리턴결과 axios에게 응답
        return todoService.get(page);
    }
    @PostMapping("")
    public boolean post(@RequestBody TodoDto todoDto){
        return todoService.post(todoDto);
    }
    @PutMapping("")
    public boolean put(@RequestBody TodoDto todoDto){
        return todoService.put(todoDto);
    }

    @DeleteMapping("")
    public boolean delete(@RequestParam int id){
        return todoService.delete(id);
    }

}
