package ezenweb.web.domain.todo;

import ezenweb.web.domain.board.ReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoEntityRepository extends JpaRepository<TodoEntity, Integer> {
}
