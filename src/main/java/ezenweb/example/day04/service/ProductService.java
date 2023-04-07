package ezenweb.example.day04.service;

import ezenweb.example.day04.domain.dto.ProductDto;
import ezenweb.example.day04.domain.entity.product.ProductEntity;
import ezenweb.example.day04.domain.entity.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    // 컨테이너에 등록된 리포지토리 빈 자동 주입
    @Autowired
    private ProductRepository productRepository;
    
    // 1. 저장
    public boolean write(ProductDto dto){
        // 1. 입력받은 dto를 엔티티로 변환 후에 save 메소드에 대입 후 생성된 엔티티 얻기
        ProductEntity entity = productRepository.save(dto.toEntity());
        if(entity.getPno()>0){return true;}
        return false;
    }

    // 2.  수정
    @Transactional
    public boolean update(ProductDto dto){
        // 1. 수정할 번호를 이용한 엔티티 찾기
        Optional<ProductEntity> optionalProductEntity =
        productRepository.findById(dto.getPno());
        if(optionalProductEntity.isPresent()){
            // 3. 만약에 있으면 true 없으면 false
            // 4. 포장 객체 안에 있는 엔티티 호출
            ProductEntity entity = optionalProductEntity.get();
            entity.setPname(dto.getPname());
            entity.setPcontent(dto.getPcontent());
            return true;
        }
        return false;
    }

    // 3. 삭제
    public boolean delete(int pno){
        // 1. 수정할 번호를 이용한 엔티티 찾기
        Optional<ProductEntity> optionalProductEntity = productRepository.findById(pno);
        // 2. 만약에 있으면 true 없으면 false
        if(optionalProductEntity.isPresent()){
            ProductEntity entity = optionalProductEntity.get();
            productRepository.delete(entity);
            return true;
        }
        return false;
    }
    
    // 4. 출력
    public ArrayList<ProductDto> getList(){
        ArrayList<ProductDto> list = new ArrayList<>();
        List<ProductEntity> entityList = productRepository.findAll();
        entityList.forEach(entity -> {
            ProductDto dto = new ProductDto();
            dto.setPno(entity.getPno());
            dto.setPname(entity.getPname());
            dto.setPcontent(entity.getPcontent());
            list.add(dto);
        });
        return list;
    }

}
