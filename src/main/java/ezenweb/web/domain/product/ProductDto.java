package ezenweb.web.domain.product;

import ezenweb.web.domain.file.FileDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;

@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class ProductDto {
    private String id;// 제품번호 => autokey 사용하지 않고 오늘 날짜를 이용하여 직접 입력
    private String pname;// 제품명
    private int pprice;// 제품가격
    private String pcategory;// 제품카테고리
    private String pcomment;// 제품설명
    private String pmanufacturer;// 제조사
    private byte pstate;// 제품상태 [ 0 : 판매중, 1 : 판매중지, 2 : 재고없음 ]
    private int pstock;// 제품재고/수량

    // 관리자페이지용 추가필드
    private String cdate;
    private String udate;
    // 첨부파일 입력용 추가필드
    private List<MultipartFile> pimgs;
    // 첨부파일 출력용
    private List<FileDto> files = new ArrayList<>();
    // 1. 저장용 [관리자페이지]
    public ProductEntity toSaveEntity() {
        return ProductEntity.builder()
            .id(this.id)
            .pname(this.pname)
            .pcomment(this.pcomment)
            .pprice(this.pprice)
            .pcategory(this.pcategory)
            .pmanufacturer(this.pmanufacturer)
            .pstate(this.pstate)
            .pstock(this.pstock)
            .build();
    }


}
