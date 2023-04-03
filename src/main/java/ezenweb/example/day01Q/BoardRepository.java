package ezenweb.example.day01Q;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // MVC 컨테이너에 빈 등록
public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {
    
}
