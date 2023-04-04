package ezenweb.example.day02.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/gethtml")
public class ResourceMappingController {
    @GetMapping("/test1")
    public Resource gettest(){
        return new ClassPathResource("templates/test1.html");
    }
    // Resource : import org.springframework.core.io
    // ClassPathResource : import org.springframework.core.io.ClassPathResource
    // return new ClassPathResource("HTML파일경로/파일명");

}
