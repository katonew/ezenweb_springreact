package ezenweb.web.service;

import ezenweb.web.domain.todo.TodoDto;
import ezenweb.web.domain.todo.TodoEntity;
import ezenweb.web.domain.todo.TodoEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TodoService {
    @Autowired
    private TodoEntityRepository todoEntityRepository;

    @Transactional
    public List<TodoDto> get(){
        List<TodoDto> result = new ArrayList<>();
        List<TodoEntity> entityList = todoEntityRepository.findAll();
        for( TodoEntity e : entityList){
            result.add(e.toDto());
        }
        // 서비스 구현 후 리턴결과 axios에게 응답
        log.info("get result : " + result);
        return result;
    }
    @Transactional
    public boolean post(@RequestBody TodoDto todoDto){
        log.info("post todoDto : " + todoDto);
        todoEntityRepository.save(todoDto.toEntity());
        return true;
    }
    @Transactional
    public boolean put(@RequestBody TodoDto todoDto){
        Optional<TodoEntity> entityOptional = todoEntityRepository.findById(todoDto.getId());
        if(entityOptional.isPresent()){
            TodoEntity todoEntity = entityOptional.get();
            todoEntity.setDone(todoDto.isDone());
            todoEntity.setTitle(todoDto.getTitle());
        }
        return true;
    }
    @Transactional
    public boolean delete(@RequestParam int id){
        log.info("delete id : " + id);
        TodoEntity todoEntity = todoEntityRepository.findById(id).get();
        todoEntityRepository.delete(todoEntity);
        return true;
    }

}
