package com.bit.auction.goods.dto;

import com.bit.auction.goods.entity.PointHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PointHistoryDTO {
    private Long userPointId;

    private String userId;

    private int point;

    private char status;

    private LocalDateTime modifiedDate;


    public PointHistory toEntity(){
        return PointHistory.builder()
                .userId(userId)
                .point(point)
                .status(status)
                .modifiedDate(LocalDateTime.now())
                .build();
    }
}

