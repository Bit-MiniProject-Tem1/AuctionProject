package com.bit.auction.user.dto;


import com.bit.auction.user.entity.Faq;
import com.bit.auction.user.entity.FaqAttachedFile;
import lombok.*;

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
}
