package ezenweb.example.day03;

import lombok.*;

// Lombok 사용
@Data // getter, setter, tostring 포함
@AllArgsConstructor @NoArgsConstructor @Builder
public class NoteDto {
    private int nno;
    private String ncontents;

    // DTO -> entity [ 서비스에서 사용 ]
        // this : 현재 클래스 내 필드명
    public NoteEntity toEntity() {
        return NoteEntity.builder()
                .nno(this.nno)
                .ncontents(this.ncontents)
                .build();
    }
}
