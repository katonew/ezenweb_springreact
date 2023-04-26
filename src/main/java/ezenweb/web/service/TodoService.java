package ezenweb.web.service;

import ezenweb.web.domain.todo.TodoDto;
import ezenweb.web.domain.todo.TodoEntity;
import ezenweb.web.domain.todo.TodoEntityRepository;
import ezenweb.web.domain.todo.TodoPageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public TodoPageDto get(int page){
        List<TodoDto> todoDtoList = new ArrayList<>();
        Pageable pageable = PageRequest.of(page-1, 5, Sort.by(Sort.Direction.DESC, "id"));
        Page<TodoEntity> entityList = todoEntityRepository.findAll(pageable);
        for( TodoEntity e : entityList){
            todoDtoList.add(e.toDto());
        }
        return TodoPageDto.builder()
                .todoDtoList(todoDtoList)
                .totalCount(entityList.getTotalElements())
                .totalPage(entityList.getTotalPages())
                .page(page)
                .build();
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
