package com.bit.auction.goods.dto;

import com.bit.auction.goods.entity.Point;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PointDTO {

    private Long userPointId;

    private String userId;

    private int point;

    private LocalDate modifiedDate;

    public Point toEntity(){
    return Point.builder()
            .userId(userId)
            .point(point)
            .modifiedDate(LocalDate.now())
            .build();
}

}


