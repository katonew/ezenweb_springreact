package ezenweb.example.day02.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/post")
public class PostMappingController {
    // * 쿼리스트링 형식 [ 경로상의 변수가 보이는 경우 ] 의 매개변수 요청 제외
    // 1. @RequestBody[성공]
    @PostMapping("/method1")
    public ParamDto method1(@RequestBody ParamDto dto) {
        log.info("post method1 :  " + dto);
        return dto;
    }
    // 2. @RequestParam[실패]
    @PostMapping("/method2")
    public String method1(@RequestParam String param1, @RequestParam String param2) {
        log.info("post method2 :  " + param1 + " - " + param2);
        return "post method2 :  " + param1 + " - " + param2;
    }
    // 3. @RequestParam[실패]
    @PostMapping("/method3")
    public String method3(@RequestBody Map<String, String> map) {
        log.info("post method3 :  " +map);
        return "post method3 :  " + map;
    }
    // 4. @ModelAttribute[실패]
    @PostMapping("/method4")
    public ParamDto method4(@ModelAttribute ParamDto dto) {
        log.info("post method4 :  " + dto);
        return dto;
    }
}
