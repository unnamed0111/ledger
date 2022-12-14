package com.portfolio.ledger.controller;

import com.portfolio.ledger.dto.UploadFileDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@Log4j2
public class FileController {

    @Value("${com.portfolio.upload.path}")
    private String uploadPath;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String upload(UploadFileDTO uploadFileDTO) {
        log.info("........................POST UPLOAD FILE........................");
        log.info(uploadFileDTO);

        if(uploadFileDTO.getFiles() != null) {
            uploadFileDTO.getFiles().forEach(multipartFile -> {
                String originalName = multipartFile.getOriginalFilename();

                log.info(originalName);

                String uuid = UUID.randomUUID().toString();
                Path savePath = Paths.get(uploadPath, uuid + "_" + originalName);

                try {
                    multipartFile.transferTo(savePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        return null;
    }
}
