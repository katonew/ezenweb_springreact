package ezenweb.example.day04.domain.entity.product;

import ezenweb.example.day04.domain.dto.ProductDto;
import ezenweb.example.day04.domain.entity.BaseTime;
import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity // 엔티티 == DB
@Table(name = "item")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductEntity extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pno;
    @Column
    private String pname;
    @Column
    private String pcontent;

    public ProductDto toDto(){
        return ProductDto.builder()
                .pno(pno)
                .pname(pname)
                .pcontent(pcontent)
                .build();
    }
}
