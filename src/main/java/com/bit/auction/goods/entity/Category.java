package com.bit.auction.goods.entity;

import com.bit.auction.goods.dto.CategoryDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;
    private String name;
    @Column(name = "top_category_id")
    private Long topCategoryId;

    @ManyToOne
    //@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "top_category_id", referencedColumnName = "category_id", insertable = false, updatable = false)
    private Category parent;
    @OneToMany(mappedBy = "parent")
    //@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private List<Category> childrenList;

    // @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE)
    // private List<Auction> auctionList;

    public CategoryDTO toDTO() {
        return CategoryDTO.builder()
                .id(this.id)
                .name(this.name)
                .topCategoryId(this.topCategoryId)
                .subCategoryList(this.childrenList)
                .build();
    }
}
