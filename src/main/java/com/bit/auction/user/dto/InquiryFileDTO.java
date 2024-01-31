package com.bit.auction.user.dto;

import com.bit.auction.user.entity.Inquiry;
import com.bit.auction.user.entity.InquiryFile;
import jakarta.persistence.Transient;
import lombok.*;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class InquiryFileDTO {
    private Long inquiryNo;
    private Long inquiryFileNo;
    private String inquiryFileName;
    private String inquiryFilePath;
    private String inquiryFileOrigin;
    private String inquiryFileCate;
    private String inquiryFileStatus;
    private String newFileName;


    public InquiryFile toEntity(Inquiry inquiry) {
        return InquiryFile.builder()
                .inquiry(inquiry)
                .inquiryFileNo(this.inquiryFileNo)
                .inquiryFileName(this.inquiryFileName)
                .inquiryFilePath(this.inquiryFilePath)
                .inquiryFileOrigin(this.inquiryFileOrigin)
                .inquiryFileCate(this.inquiryFileCate)
                .inquiryFileStatus(this.inquiryFileStatus)
                .newFileName(this.newFileName)
                .build();
    }
}
