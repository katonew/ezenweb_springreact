package ezenweb.example.day01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppStart {

    @Autowired
    private static Testmember2Repository  testmember2Repository;

    public static void main(String[] args) {
        SpringApplication.run(AppStart.class);
        LombokDto dto = new LombokDto().builder()
                .mid("qweqwe")
                .mpassword("qweqwe")
                .build();

        testmember2Repository.save(dto.toEntity());

    }
}


/*
    실행 후 -> localhost:8080 접속
    Whitelabel Error Page --> 정상작동
    This application has no explicit mapping for /error, so you are seeing this as a fallback.
    
    Mon Apr 03 14:30:49 KST 2023
    There was an unexpected error (type=Not Found, status=404).
    
    
    JPA <------>DB 연동 실패
        1. application.properties 작성된 URL , 정보 오타 있거나 실제로 DB가 없을경우
    Description:
    Failed to configure a DataSource: 'url' attribute is not specified and no embedded datasource could be configured.
    Reason: Failed to determine a suitable driver class
*/