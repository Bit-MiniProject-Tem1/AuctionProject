package com.bit.auction.user.dto;

import com.bit.auction.user.entity.Faq;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
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