package ezenweb.example.day02.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/put")
public class PutMappingController {

    // 1.
    @PutMapping("/method1")
    public String method1(@RequestBody ParamDto dto){
        log.info("method1 : " + dto );
        return "method1 : " + dto;
    }
    // 2.
    @PutMapping("/method2")
    public Map<String , String> method2(@RequestBody Map<String , String> map){
        log.info("method2 : " + map );
        return map;
    }
}
