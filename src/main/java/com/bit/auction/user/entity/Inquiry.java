package com.bit.auction.user.entity;

import com.bit.auction.user.dto.InquiryDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "INQUIRY")
@SequenceGenerator(
        name = "InquirySeqGenerator",
        sequenceName = "INQUIRY_SEQ",
        initialValue = 1,
        allocationSize = 1
)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Setter
@Builder
public class Inquiry {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "InquirySeqGenerator"
    )
    private Long inquiryNo;

    @Column(nullable = false, name = "inquiry_type")
    private String inquiryType;

    @Column(nullable = false, name = "inquiry_title")
    private String inquiryTitle;

    @Column(nullable = false, name = "inquiry_content")
    private String inquiryContent;

    @Column(nullable = false, name = "inquiry_answer")
    private String inquiryAnswer;

    @Column(nullable = false, name = "inquiry_cnt")
    private int inquiryCnt;

    @Column(name = "inquiry_regdate")
    private LocalDateTime inquiryRegdate;

    @OneToMany(mappedBy = "inquiry", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<InquiryFile> inquiryFileList;
    @Transient
    private String searchCondition;
    @Transient
    private String searchKeyword;

    public InquiryDTO toDTO() {
        return InquiryDTO.builder()
                .inquiryNo(this.inquiryNo)
                .inquiryType(this.inquiryType)
                .inquiryTitle(this.inquiryTitle)
                .inquiryContent(this.inquiryContent)
                .inquiryAnswer(this.inquiryAnswer)
                .inquiryRegdate(LocalDateTime.now())
                .inquiryFileDTOList(this.inquiryFileList.stream().map(inquiryFile -> inquiryFile.toDTO()).toList())
                .build();
    }

    public void addInquiryFileList(InquiryFile inquiryFile) {
        this.inquiryFileList.add(inquiryFile);
    }
}
