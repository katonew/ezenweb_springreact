package ezenweb.web.service;

import ezenweb.web.domain.product.ProductDto;
import ezenweb.web.domain.product.ProductEntity;
import ezenweb.web.domain.product.ProductEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductService { /* 주요기능과 DB처리 */
    @Autowired
    private ProductEntityRepository productEntityRepository;

    // 1.
    @Transactional
    public List<ProductDto> get(){
        log.info("product Service get");
        // 1. 모든 엔티티 호출
        List<ProductEntity> productEntityList = productEntityRepository.findAll();
        // 2. 모든 엔티티를 DTO로 변환
        List<ProductDto> productDtoList = productEntityList.stream().map( o -> o.toAdminDto()).collect(Collectors.toList());
        return productDtoList;
    }
    // 2.
    @Transactional
    public boolean post(ProductDto productDto){
        log.info("product Service post : " + productDto);
        // 1. id 생성 [오늘 날짜 +등록 밀리초+난수]
        String number = "";
        for(int i=0;i<3;i++){number += new Random().nextInt(10);}
        String pid = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddSSS"))+number;
        // dto에 생성한 id 넣기
        productDto.setId(pid);
        // db에 저장
        productEntityRepository.save(productDto.toSaveEntity());
        return true;
    }
    // 3.
    @Transactional
    public boolean put(ProductDto productDto){
        log.info("product Service put : " + productDto);
        // 1. 수정할 엔티티 찾기
        Optional<ProductEntity> optionalProductEntity = productEntityRepository.findById(productDto.getId());
        if(optionalProductEntity.isPresent()){
            ProductEntity productEntity = optionalProductEntity.get();
            productEntity.setId(productDto.getId());
            productEntity.setPcategory(productDto.getPcategory());
            productEntity.setPcomment(productDto.getPcomment());
            productEntity.setPname(productDto.getPname());
            productEntity.setPprice(productDto.getPprice());
            productEntity.setPmanufacturer(productDto.getPmanufacturer());
            productEntity.setPstate(productDto.getPstate());
            productEntity.setPstock(productDto.getPstock());
            return true;
        }
        return false;
    }
    // 4.
    @Transactional
    public boolean delete(String id){
        log.info("product Service delete : " + id);
        // 1. 삭제할 엔티티 찾기
        Optional<ProductEntity> optionalProductEntity = productEntityRepository.findById(id);
        // 2. 해당 엔티티가 존재하면
        if(optionalProductEntity.isPresent()){
            ProductEntity productEntity = optionalProductEntity.get();
            productEntityRepository.delete(productEntity);
            return true;
        }
        return false;
    }
}
