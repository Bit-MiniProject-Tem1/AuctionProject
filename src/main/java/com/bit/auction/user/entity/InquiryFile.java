package com.bit.auction.user.entity;


import com.bit.auction.user.dto.InquiryFileDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "INQUIRY_FILE")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(InquiryFileId.class)
@SequenceGenerator(
        name = "inquiryFileSeqGenerator",
        sequenceName = "USER_INQUIRY_FILE_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class InquiryFile {
    @Id
    @ManyToOne
    @JoinColumn(name = "INQUIRY_NO")
    @JsonBackReference
    private Inquiry inquiry;

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "inquiryFileSeqGenerator"
    )
    private Long inquiryFileNo;
    private String inquiryFileName;
    private String inquiryFilePath;
    private String inquiryFileOrigin;
    private String inquiryFileCate;
    @Transient
    private String inquiryFileStatus;
    @Transient
    private String newFileName;


    public InquiryFileDTO toDTO() {
        return InquiryFileDTO.builder()
                .inquiryNo(this.inquiry.getInquiryNo())
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
