package com.bit.auction.common;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class CkEditorImageUtils {
    public String parseFileInto(MultipartFile uploadFile, String realPath) throws IOException {
        String originalFileName = uploadFile.getOriginalFilename();
        String ext = originalFileName.substring(originalFileName.indexOf("."));
        String newFileName = UUID.randomUUID() + ext;
        realPath = System.getProperty("user.dir") + "\\src\\";

        String savePath = realPath + "/main/resources/static/upload/" + newFileName;
        String uploadPath = realPath + "\\main\\resources\\static\\upload\\" + newFileName;

        Path path = Paths.get(savePath).toAbsolutePath();
        uploadFile.transferTo(path.toFile());

        return uploadPath;
    }
}
