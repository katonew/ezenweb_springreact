package ezenweb.web.domain.product;

import ezenweb.web.domain.BaseTime;
import ezenweb.web.domain.file.FileDto;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    // 제약조건 : pk객체가 삭제되면 fk의 행방
    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.REMOVE) // pk필드 선언시 mappedBy="FK에서 식별하는 PK명"
    @ToString.Exclude
    @Builder.Default
    private List<ProductImgEntity> productImgEntityList = new ArrayList<>();

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
    public ProductDto toMainDto(){
        List<FileDto> list =
        this.getProductImgEntityList().stream().map(
            img-> img.toFileDto()
        ).collect(Collectors.toList());

        return ProductDto.builder()
                .id(this.id)
                .pprice(this.pprice)
                .pname(this.pname)
                .pcategory(this.pcategory)
                .pmanufacturer(this.pmanufacturer)
                .pstate(this.pstate)
                .pstock(this.pstock)
                .files(list)
                .build();
    }

}

/*
    JPA는 1개 이상의 ID 필수
    JPA로 필드명 선언이 언더바 사용X


*/
