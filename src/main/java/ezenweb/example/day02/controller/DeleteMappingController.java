package ezenweb.example.day02.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/delete")
public class DeleteMappingController {

    // 1.
    @DeleteMapping("/method1")
    public String method1( ParamDto dto){
        log.info("method1 : " + dto );
        return "method1 : " + dto;
    }
    // 2.
    @DeleteMapping("/method2")
    public Map<String , String> method2(@RequestParam Map<String , String> map){
        log.info("method2 : " + map );
        return map;
    }
}
