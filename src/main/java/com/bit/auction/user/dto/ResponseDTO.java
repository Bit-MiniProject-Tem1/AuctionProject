package com.bit.auction.user.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class ResponseDTO<T> {
    private List<T> items;
    //페이징 처리된 데이터 목록
    private Page<T> pageItems;

    private T item;

    private String errorMessage;

    private int errorCode;

    private int statusCode;
}
