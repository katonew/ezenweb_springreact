package ezenweb.web.controller;

import ezenweb.web.domain.file.FileDto;
import ezenweb.web.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/chat")
@Slf4j
public class Chatcontroller {

    @Autowired
    private FileService fileService;

    @PostMapping("/fileupload") // chat 관련 첨부파일 업로드
    public FileDto fileUpload(@RequestParam("attachFile") MultipartFile multipartFile){

        return fileService.fileUpload(multipartFile);
    }

    @GetMapping("/filedownload") // chat 관련 첨부파일 다운로드
    public void filedownload(@RequestParam("uuidFile") String uuidFile){
        log.info("uuidFile: " + uuidFile);
        fileService.filedownload(uuidFile);
    }

}
