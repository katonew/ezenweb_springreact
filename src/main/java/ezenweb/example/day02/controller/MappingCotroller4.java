package ezenweb.example.day02.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

// 스프링 관리하는 IOC 컨테이너 빈[객체] 등록
@RestController // HTTP 요청이 왔을때 해당 클래스로 핸들러 매핑
@Slf4j  // 스프링 로그 메소드 제공 [ 레벨 : trace < debug < info < warn < error ]
@RequestMapping("/color")
public class MappingCotroller4 {

    @GetMapping("/pink")
    public String getPink(){
        log.info("클라이언트로부터 getPink 메소드 요청이 들어옴 ");
        return "get 응답";
    }
    
    @PostMapping("/pink")
    public  String postPink(){
        log.info("클라이언트로부터 postPink 메소드 요청이 들어옴");
        return "post 응답";
    }

    @PutMapping("/pink")
    public String putPink(){
        log.info("클라이언트로부터 putPink 메소드 요청이 들어옴");
        return "put 응답";
    }

    @DeleteMapping("/pink")
    public String deletePink(){
        log.info("클라이언트로부터 deletePink 메소드 요청이 들어옴");
        return "delete 응답";
    }
}



/*
    스프링 부트 동작 구조


    크롬/ajax/js -----요청----> 서블릿 컨테이너 ----> 핸들러 매핑 ----> Dispatcher Servlet
    http://localhost:8080/orange                                     핸들러 매핑으로 해당 컨트롤러(스프링 컨테이너) 검색
                                                               -----> Mapping 검색
                                                                      @RequestMapping(value = "/orange", method = RequestMethod.GET)
                       <----------------응답------------------


 */