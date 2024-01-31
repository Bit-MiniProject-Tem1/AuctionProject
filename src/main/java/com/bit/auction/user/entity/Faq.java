package com.bit.auction.user.entity;

import com.bit.auction.user.dto.FaqDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "FAQ")
public class Faq {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FAQ_ID")
    private Long faqId;

    @Column(name = "CATEGORY", length = 20, nullable = false)
    private String category;

    @Column(name = "TITLE", length = 300, nullable = false)
    private String title;

    @Column(name = "CONTENT", columnDefinition = "TEXT", nullable = false)
    private String content;

//    @CreationTimestamp
    @Column(name = "REGDATE", columnDefinition = "DATETIME", nullable = false)
    private LocalDateTime regdate;

    @Column(name = "VIEWS_COUNT", columnDefinition = "INT DEFAULT 0", nullable = false)
    private int viewsCount;

    @OneToMany(mappedBy = "faq", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<FaqAttachedFile> faqAttachedFileList;

    @Transient
    private String searchCondition;

    @Transient
    private String searchKeyword;


    public FaqDTO toDTO() {
        return FaqDTO.builder()
                .faqId(this.faqId)
                .category(this.category)
                .title(this.title)
                .content(this.content)
                .regdate(this.regdate.toString().substring(0, 10))
                .viewsCount(this.viewsCount)
                .faqAttachedFileDTOList(this.faqAttachedFileList.stream()
                        .map(faqAttachedFile -> faqAttachedFile.toDTO())
                        .toList()
                )
                .build();
    }

    public void addFaqAttachedFileList(FaqAttachedFile faqAttachedFile) {
        this.faqAttachedFileList.add(faqAttachedFile);
    }
}
