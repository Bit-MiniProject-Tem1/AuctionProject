package com.bit.auction.goods.dto;

import com.bit.auction.goods.entity.Category;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CategoryDTO {
    private Long id;
    private String name;
    private Long topCategoryId; // fk
    private List<Category> subCategoryList;
}
