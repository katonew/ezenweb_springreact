package ezenweb.example.day01Q;

import lombok.*;

import javax.persistence.*;

@Entity // DB 테이블과 해당 클래스와 매핑
@Table(name = "testboard") // DB 테이블명
@Setter@Getter@ToString // 롬북 getter &setter
@AllArgsConstructor@NoArgsConstructor // 롬북 제공하는 빈 생성자 풀생성자
@Builder // 롬북 빌더 패턴
public class BoardEntity {
    @Id // PK선언 [ JPA 엔티티 / 테이블 당 PK 무조건 하나 이상 선언 ]
    @GeneratedValue ( strategy = GenerationType.IDENTITY) // auto key
    private int bno;
    @Column // 필드 선언
    private String btitle;
    @Column // 필드 선언
    private String bcontent;
}
