package com.bit.auction.goods.entity;

import com.bit.auction.goods.dto.PointDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "point")
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userPointId;

    private String userId;

    private int point;

    private LocalDateTime modifiedDate;

    public PointDTO toDTO() {
        return PointDTO.builder()
                .userId(userId)
                .point(point)
                .modifiedDate(LocalDateTime.now())
                .build();
    }
}
