package ezenweb.web.domain.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.sql.Date;

@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class BoardDto {
    private int bno;
    private String btitle;
    private String bcontent;
    private int cno;
    private String cname;

    // 추가
    private int mno;
    private String mname;
    private String bdate; // 작성일
    private int bview; // 조회수

    // 댓글 목록


    // 엔티티 변환 메소드
    // 1. toCategoryEntity
    public CategoryEntity toCategoryEntity(){
        return CategoryEntity.builder()
                .cname(this.cname).build();
    }
    // 2. toBoardEntity
    public BoardEntity toBoardEntity(){
        return BoardEntity.builder()
                .btitle(this.btitle)
                .bcontent(this.bcontent)
                .build();
    }

}
