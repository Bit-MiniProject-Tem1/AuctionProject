package com.bit.auction.user.dto;

import com.bit.auction.user.entity.Inquiry;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@ToString
public class InquiryDTO {
    private long inquiryNo;
    private String inquiryType;
    private String inquiryTitle;
    private String inquiryContent;
    private String inquiryAnswer;
    private LocalDateTime inquiryRegdate;
    private List<InquiryFileDTO> inquiryFileDTOList;
    private String searchCondition;
    private String searchKeyword;


    public static InquiryDTO toinquiryDTO(Inquiry inquiry) {
        InquiryDTO inquiryDTO = new InquiryDTO();
        inquiryDTO.setInquiryNo(inquiry.getInquiryNo());
        inquiryDTO.setInquiryType(inquiryDTO.getInquiryType());
        inquiryDTO.setInquiryTitle(inquiryDTO.getInquiryTitle());
        inquiryDTO.setInquiryContent(inquiryDTO.getInquiryContent());
        inquiryDTO.setInquiryAnswer(inquiryDTO.getInquiryAnswer());
        inquiryDTO.setInquiryRegdate(inquiryDTO.getInquiryRegdate());
        inquiryDTO.setInquiryFileDTOList(inquiryDTO.getInquiryFileDTOList());
        inquiryDTO.setSearchCondition(inquiryDTO.getSearchCondition());
        inquiryDTO.setSearchKeyword(inquiryDTO.getSearchKeyword());
        return inquiryDTO;
    }

public Inquiry toEntity() {
    return Inquiry.builder()
            .inquiryNo(this.inquiryNo)
            .inquiryType(this.inquiryType)
            .inquiryTitle(this.inquiryTitle)
            .inquiryContent(this.inquiryContent)
            .inquiryAnswer(this.inquiryAnswer)
            .inquiryRegdate(LocalDateTime.now())
            .inquiryFileList(new ArrayList<>())
            .build();
    }
}
