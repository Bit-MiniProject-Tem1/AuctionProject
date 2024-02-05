package com.bit.auction.admin.dto;


import com.bit.auction.admin.entity.Faq;
import com.bit.auction.admin.entity.FaqAttachedFile;
import lombok.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FaqAttachedFileDTO {
    private Long faqId;
    private Long fileId;
    private String fileName;
    private String filePath;
    private String fileType;
    private String faqAttachedFileStatus;
    private String newFileName;

    public FaqAttachedFile toEntity(Faq faq) {
        return FaqAttachedFile.builder()
                .faq(faq)
                .fileId(this.fileId)
                .fileName(this.fileName)
                .filePath(this.filePath)
                .fileType(this.fileType)
                .faqAttachedFileStatus(this.faqAttachedFileStatus)
                .newFileName(this.newFileName)
                .build();
    }

    public static String getFileContentType(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        String contentType = Files.probeContentType(path);
        return contentType;
    }
}
