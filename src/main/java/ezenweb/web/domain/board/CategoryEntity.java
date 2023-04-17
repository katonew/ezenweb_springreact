package ezenweb.web.domain.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity@Table(name="bcategory")
@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int cno;

    @Column
    private String cname;

    // 양방향
    // 카테고리[PK<----> 게시물 [FK]
    // PK테이블에는 FK의 흔적을 남긴 적이 없음 [ 필드 존재 X 객체존재 O ]
    @OneToMany(mappedBy="categoryEntity") // 하나가 다수에게
    @Builder.Default // .builder 사용시 현재 필드 기본값으로 설정
    private List<BoardEntity> boardEntityList = new ArrayList<>();
}
