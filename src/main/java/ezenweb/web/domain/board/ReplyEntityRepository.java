package ezenweb.web.domain.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyEntityRepository extends JpaRepository<ReplyEntity, Integer> {
    @Query(value = "select * from reply where bno = :bno", nativeQuery = true)
    List<ReplyEntity> findAllByBno(String bno);
}
