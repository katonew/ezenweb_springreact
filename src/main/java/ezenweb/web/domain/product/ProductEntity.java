package ezenweb.web.domain.product;

import ezenweb.web.domain.BaseTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data@AllArgsConstructor@NoArgsConstructor@Builder
@Entity@Table(name = "product")
public class ProductEntity extends BaseTime {

    @Id
    private String id;// 제품번호
    //autokey 사용하지 않고 오늘 날짜를 이용하여 직접 입력

    @Column(nullable = false)
    private String pname;// 제품명

    @Column(nullable = false)
    private int pprice;// 제품가격

    @Column(nullable = false)
    private String pcategory;// 제품카테고리

    @Column(nullable = false,columnDefinition = "TEXT")
    private String pcomment;// 제품설명

    @Column(nullable = false,length = 100)
    private String pmanufacturer;// 제조사

    @ColumnDefault("0")
    @Column(nullable = false)
    private byte pstate;// 제품상태 [ 0 : 판매중, 1 : 판매중지, 2 : 재고없음 ]

    @ColumnDefault("0")
    @Column(nullable = false)
    private int pstock;// 제품재고/수량

    // 제품이미지 [ one to many ]
    // 구매내역 [ one to many ]

    // 1. 출력용 [ 관리자페이지에서 ]
    public ProductDto toAdminDto() {
        return ProductDto.builder()
                .id(this.id)
                .pname(this.pname)
                .pcomment(this.pcomment)
                .pprice(this.pprice)
                .pcategory(this.pcategory)
                .pmanufacturer(this.pmanufacturer)
                .pstate(this.pstate)
                .pstock(this.pstock)
                .cdate(this.cdate.toString().substring(0,10))
                .udate(this.udate.toString().substring(0,10))
                .build();
    }
    // 2. 출력용 [ 메인페이지에서 ]
    //public ProductDto toMainDto(){ }

}

/*
    JPA는 1개 이상의 ID 필수
    JPA로 필드명 선언이 언더바 사용X


*/
