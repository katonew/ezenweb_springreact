package ezenweb.web.service;

import ezenweb.web.domain.file.FileDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.UUID;

@Service
@Slf4j
public class FileService {

    @Autowired
    private HttpServletResponse response;// 응답개게


    // * 첨부파일 저장 할 경로 [ 1. 배포 전 2. 배포 후 ]
    String path = "C:\\Users\\504\\IdeaProjects\\ezenweb_springreact\\build\\resources\\main\\static\\static\\media\\";
    // C:\Users\504\IdeaProjects\ezenweb_springreact\src\main\resources\static\static\media


    // chat 관련 첨부파일 업로드
    public FileDto fileUpload(MultipartFile multipartFile){

        // 1. 첨부파일 존재하는지 확인
        if(!multipartFile.getOriginalFilename().equals("")){ // 첨부파일이 존재하면
            //*만약에 다른이미지인데 파일명이 동일하면 중복발생 [ 식별 불가 ]
            String fileName = UUID.randomUUID().toString()+"_"+multipartFile.getOriginalFilename();

            // 2. 경로 + 파일명 조합해서 file 클래스 생성
            File file = new File(path+fileName);
            // 3. 업로드 : multipartFile.transferTo(저장할 File 클래스의 객체);
            try {
                multipartFile.transferTo(file);
            }catch (Exception e) { log.info("fileUpload Error : " + e);}
            // 4. 반환
            return FileDto.builder()
                    .originalFilename(multipartFile.getOriginalFilename())
                    .uuidFile(fileName)
                    .sizeKb(multipartFile.getSize()/1024+"kb")
                    .build();
        }
        return null;
    }

    // chat 관련 첨부파일 다운로드
    public void filedownload(String uuidFile){ //spring에는 다운로드에 관한 API가 없음
        log.info("uuidFile : " + uuidFile);
        String pathFile = path+uuidFile;
        try {
            // 1. 다운로드 형식 구성
            response.setHeader(
                "Content-Disposition", // 헤더구성 [ 브라우저 다운로드 형식 ]
                "attchment;filename = "+ URLEncoder.encode(uuidFile.split("_")[1],"UTF-8")// 다운로드 시 표시될 이름
            );
            // 2. 다운로드 스트림 구성
            File file = new File(pathFile);
            // 3. 입력 스트림 [ 서버가 먼저 다운로드 할 파일의 바이트 읽어오기 ]
            BufferedInputStream fin = new BufferedInputStream(new FileInputStream(file));
            byte[] bytes = new byte[(int)file.length()]; // 파일의 길이(용량) 만큼 바이트 배열 선언
            fin.read(bytes); // 읽어온 바이트들을 bytes배열 저장
            // 4. 출력 스트림 [ 서버가 읽어온 바이트를 출력할 스트림  = 대상 : response = 현재 서비스 요청한 클라이언트에게 ]
            BufferedOutputStream fout = new BufferedOutputStream(response.getOutputStream());
            fout.write(bytes); // 입력 스트림에서 읽어온 바이트 배열을 내보내기
            fout.flush(); // 스트림 메모리 초기화
            fin.close(); fout.close(); // 스트림 닫기
        }catch (Exception e) { log.info("filedownload Error : " + e);}

    }
}
