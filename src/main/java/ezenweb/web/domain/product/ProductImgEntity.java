package ezenweb.web.domain.product;

import ezenweb.web.domain.file.FileDto;
import lombok.*;

import javax.persistence.*;

@Entity@Table(name = "productimg")
@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class ProductImgEntity {
    // 1. 이미지 식별이름
    @Id
    private String uuidFile;
    // 2. 이미지 이름
    @Column
    private String originalFilename;
    // 3. 제품 fk
    @ManyToOne
    @JoinColumn(name = "id") // DB 테이블에 표시될 FK 필드명
    @ToString.Exclude // 순환참조 방지 -> 양방향일때 필수
    private ProductEntity productEntity;

    public FileDto toFileDto(){
        return FileDto.builder()
                .uuidFile(this.uuidFile)
                .originalFilename(this.originalFilename)
                .build();
    }
}
