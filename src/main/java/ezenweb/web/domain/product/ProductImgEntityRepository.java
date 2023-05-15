package ezenweb.web.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImgEntityRepository extends JpaRepository<ProductImgEntity, String> {

}
