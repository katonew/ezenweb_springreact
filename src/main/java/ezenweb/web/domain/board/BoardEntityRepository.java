package ezenweb.web.domain.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardEntityRepository extends JpaRepository<BoardEntity, Integer> {
    // JPA 형식이 아닌 순수 SQL 적용하는 함수 정의
        // 동일한 cno 찾기
        // [jsp] = > select *from board where cno = ?
            // ps.setInt(1,cno);
        // [JPA] select *from board where cno = :cno
            // :cno (해당 함수의 매개변수 이름)
    // * cno가 0 인 경우 조건이 존재하지 않는다.
    // cno가 0이면
    @Query(value = "select * from board where if(:cno=0, cno like '%%' , cno = :cno)", nativeQuery = true)
    Page<BoardEntity> findBySearch(int cno, Pageable pageable);
}
