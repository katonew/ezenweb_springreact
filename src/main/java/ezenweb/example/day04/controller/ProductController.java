package ezenweb.example.day04.controller;

import ezenweb.example.day04.domain.dto.ProductDto;
import ezenweb.example.day04.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/item")
public class ProductController {

    @Autowired
    private ProductService service;

    // 1. HTML 반환 [ REACT.JS  사용하기 전]
    @GetMapping("")
    public Resource index(){
        return new ClassPathResource("templates/item.html");
    }
    // 2. RESTful API
        // 1. 저장
    @PostMapping("/write")
    public boolean write(@RequestBody ProductDto dto){
        return service.write(dto);
    }
        // 2. 수정
    @PutMapping("/update")
    public boolean update(@RequestBody ProductDto dto){
        return service.update(dto);
    }
        // 3. 삭제
    @DeleteMapping("/delete")
    public boolean delete(@RequestParam int pno){
        return service.delete(pno);
    }
        // 4. 출력
    @GetMapping("/get")
    public ArrayList<ProductDto> get(){
        return service.getList();
    }
    // 3. 유효성 검사

}
