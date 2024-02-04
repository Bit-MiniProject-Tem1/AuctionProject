package com.bit.auction.goods.entity;

import com.bit.auction.goods.dto.PointHistoryDTO;
import jakarta.persistence.*;
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
    @Table(name = "pointHistory")
    public class PointHistory {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String userId;

        private int point;

        private char status;

        private LocalDateTime modifiedDate;

        public PointHistoryDTO toDTO() {
            return PointHistoryDTO.builder()
                    .userId(userId)
                    .point(point)
                    .status(status)
                    .modifiedDate(modifiedDate)
                    .build();
        }
    }

