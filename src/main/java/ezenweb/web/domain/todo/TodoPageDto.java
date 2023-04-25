package ezenweb.web.domain.todo;

import ezenweb.web.domain.board.BoardDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class TodoPageDto {
    // 1. 전체 게시물 수
    private long totalCount;
    // 2. 전체 페이지 수
    private int totalPage;
    // 3. 현재 페이지의 게시물 dto 들
    private List<TodoDto> todoDtoList;
    // 4. 현재 페이지 번호
    private int page;
}
