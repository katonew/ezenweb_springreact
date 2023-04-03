package ezenweb.example.day01;

public class Ex2 {
    public static void main(String[] args) {

        // 1. builder 패턴을 이용한 객체 생성
        LombokDto dto = LombokDto.builder()
                .mno(1).mid("가나다").mpassword("qwe")
                .phone("010-1111-1111").point(1000) .build();
        
        // 2. toString 이용한 메소드 사용
        System.out.println("dto : "+dto.toString());
       
        // 3. Dao 이용한 DB처리
        Dao dao = new Dao();
        // 4. DB에 저장하기
        boolean result = dao.setmember(dto);
        System.out.println("result : " + result);

        // 5. JPA




    } // main e
} // class e
