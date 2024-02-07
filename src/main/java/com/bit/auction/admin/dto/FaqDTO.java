package com.bit.auction.admin.dto;

import com.bit.auction.admin.entity.Faq;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class FaqDTO {
    private Long faqId;  // primary key
    private String category;
    private String title;
    private String content;
    private String regdate;
    private int viewsCount;
    private List<FaqAttachedFileDTO> faqAttachedFileDTOList;
    private String searchCondition;
    private String searchKeyword;

    public FaqDTO() {
        this.faqAttachedFileDTOList = new ArrayList<>();
    }

    public Faq toEntity() {
        return Faq.builder()
                .faqId(this.faqId)
                .category(this.category)
                .title(this.title)
                .content(this.content)
                .regdate(LocalDateTime.parse(this.regdate))
                .viewsCount(this.viewsCount)
                .faqAttachedFileList(new ArrayList<>())
                .build();
    }
}