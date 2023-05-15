package ezenweb.web.service;

import ezenweb.web.domain.file.FileDto;
import ezenweb.web.domain.product.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.transaction.Transactional;
import java.io.File;
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

    @Autowired
    private ProductImgEntityRepository productImgEntityRepository;

    @Autowired
    private FileService fileService;
    
    // main 출력 용 현재 판매중인 제품만 호출
    @Transactional
    public List<ProductDto> mainGet() {
        List<ProductEntity> productEntityList = productEntityRepository.findAllState();
        List<ProductDto> productDtoList = productEntityList.stream().map(
                o -> {return o.toMainDto();}
        ).collect(Collectors.toList());
        return productDtoList;
    }
    // 1. admin 출력용 모든 제품 호출
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
        // 2. dto에 생성한 id 넣기
        productDto.setId(pid);
        // 3. db에 저장
        ProductEntity productEntity = productEntityRepository.save(productDto.toSaveEntity());
        // 4. 첨부파일 저장
        // 만약에 첨부파일이 1개 이상이면
        if(productDto.getPimgs().size()!=0){
                // 하나씩 업로드
            productDto.getPimgs().forEach( (img)->{
                // 업로드된 파일 결과
                FileDto fileDto = fileService.fileUpload(img);
                // DB저장
                ProductImgEntity productImgEntity = productImgEntityRepository.save(
                        ProductImgEntity.builder()
                            .originalFilename(fileDto.getOriginalFilename())
                            .uuidFile(fileDto.getUuidFile())
                            .build());
                // 단방향 : 이미지 객체에 제품객체 등록
                productImgEntity.setProductEntity(productEntity);
                // 양방향 : 상품안의 이미지리스트에 이미지 등록
                productEntity.getProductImgEntityList().add(productImgEntity);
            });
        }
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
            // 파일도 같이 삭제
            ProductEntity productEntity = optionalProductEntity.get();
            if(productEntity.getProductImgEntityList().size()>0){
                productEntity.getProductImgEntityList().forEach( (img)->{
                    File file = new File(fileService.path+img.getUuidFile());
                    // 해당 경로에 파일이 존재하면 삭제
                    if(file.exists()){file.delete();}
                });
            }
            productEntityRepository.delete(productEntity);
            return true;
        }
        return false;
    }
}
