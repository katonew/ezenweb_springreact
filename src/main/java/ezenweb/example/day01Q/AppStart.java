package ezenweb.example.day01Q;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // mvc, 내장톰캣, restful API, 스프링 컨테이너 기본 세팅 등등
public class AppStart {
    public static void main(String[] args) {
        // SpringApplication.run(현재 클래스명.class);
        SpringApplication.run(AppStart.class);
    }
}
