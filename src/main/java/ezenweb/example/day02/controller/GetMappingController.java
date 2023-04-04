package ezenweb.example.day02.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController // @ResponseBody + @Controller
@Slf4j
@RequestMapping("/get") // 공통URL
public class GetMappingController {
    // -----------매개 변수 요청--------------- //
    // 1. HttpServletRequest request : request 객체를 이용한 매개변수 요청 [ JSP 주로 사용 ]
    @GetMapping("/method1")
    public String method1(HttpServletRequest request) {
        String param1 = request.getParameter("param1");
        log.info("클라이언트로부터 받은 변수 : " + param1);
        return "1.받은 데이터 그대로 전달 : " + param1;
    }
    // 2. @PathVariable : URL 경로상의 변수 요청
    @GetMapping("/method2/{param1}/{param2}")
    public String method2(@PathVariable String param1,
                          @PathVariable String param2 ){
        log.info("클라이언트로부터 받은 변수 : " + param1 +" - "+ param2);
        return "2.받은 데이터 그대로 전달 : " + param1 +" - "+ param2;
    }
    // 3.RequestParam
    @GetMapping("/method3")
    public String method3(@RequestParam String param1, String param2){
        log.info("클라이언트로부터 받은 변수 : " + param1 +" - "+ param2);
        return "3.받은 데이터 그대로 전달 : " + param1  +" - "+ param2;
    }
    // 리턴타입이 String : Content-Type ==> TEXT

    // 4.RequestParam
    @GetMapping("/method4")
    public Map<String, String> method4(@RequestParam Map<String, String> params){
        log.info("클라이언트로부터 받은 변수 : " + params );
        return params;
    }
    // 리턴타입이 Map<String, String> : Content-Type ==> JSON

    // 5.
    @GetMapping("/method5")
    public ParamDto method5(ParamDto dto){
        log.info("클라이언트로부터 받은 변수 : " + dto);
        return dto;
    }
    // 6. GET,DELETE @RequestBody 사용불가
    @GetMapping("/method6")
    public String method6(@RequestBody ParamDto dto){
        log.info("클라이언트로부터 받은 변수 : " + dto);
        return "6.받은 데이터 그대로 전달 : " + dto;
    }

    // 7. GET,DELETE @RequestParam 사용불가
    @GetMapping("/method7")
    public String method7(@RequestParam ParamDto dto){
        log.info("클라이언트로부터 받은 변수 : " + dto);
        return "7.받은 데이터 그대로 전달 : " + dto;
    }

    // 8. @ModelAttribute
    @GetMapping("/method8")
    public String method8(@ModelAttribute ParamDto dto){
        log.info("클라이언트로부터 받은 변수 : " + dto);
        return "8.받은 데이터 그대로 전달 : " + dto;
    }
}
