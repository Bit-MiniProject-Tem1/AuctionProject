package com.bit.auction.admin.entity;

import com.bit.auction.admin.dto.FaqAttachedFileDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "FAQ_ATTACHED_FILE")
@SequenceGenerator(
        name = "boardFileSeqGenerator",
        sequenceName = "T_BOARD_FILE_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class FaqAttachedFile {

    @ManyToOne
    @JoinColumn(name = "FAQ_ID", nullable = false)
    @JsonBackReference
    private Faq faq;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "FILE_ID")
    private Long fileId;

    @Column(name = "FILE_NAME", length = 100, nullable = false)
    private String fileName;

    @Column(name = "FILE_PATH", length = 300, nullable = false)
    private String filePath;

    @Column(name = "FILE_TYPE", length = 20, nullable = false)
    private String fileType;


    @Transient
    private String faqAttachedFileStatus;

    @Transient
    private String newFileName;

    public FaqAttachedFileDTO toDTO() {
        return FaqAttachedFileDTO.builder()
                .faqId(this.faq.getFaqId())
                .fileId(this.fileId)
                .fileName(this.fileName)
                .filePath(this.filePath)
                .fileType(this.fileType)
                .faqAttachedFileStatus(this.faqAttachedFileStatus)
                .newFileName(this.newFileName)
                .build();
    }
}
