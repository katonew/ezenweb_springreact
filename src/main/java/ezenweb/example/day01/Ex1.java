package ezenweb.example.day01;

public class Ex1 {
    public static void main(String[] args) {

        // 1. dto
        Dto dto = new Dto(1,"qwe","qwe",100,"010-4444-4444");
        // 2. lombok [@NoArgsConstructor]
        LombokDto lombokDto1 = new LombokDto();
        // 3. lombok [@AllArgsConstructor]
        LombokDto lombokDto2 = new LombokDto(1,"qwe","qwe",100,"010-4444-4444");

        // 4. lombok [@Getter&Sestter]
        System.out.println("getter : " + lombokDto2.getMid());
        lombokDto1.setMid("asd");

        // 5. lombok [@ToString]
        System.out.println("tostring() : " + lombokDto2.toString());

        // 6. lombok [@Bulider]
        LombokDto lombokDto3 = LombokDto.builder()
                .mno(1)
                .mid("asd")
                .mpassword("asd")
                .point(100)
                .phone("010-5555-5555")
                .build();
        // 생성자 안쓰고 아이디와 패스워드가 저장된 객체 생성
        // 객체 생성시 매개변수 개수 상관X / 순서 상관 X
        System.out.println(lombokDto3.toString());

    }
}
