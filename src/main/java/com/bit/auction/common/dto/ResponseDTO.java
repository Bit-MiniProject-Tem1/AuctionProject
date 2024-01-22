package com.bit.auction.common.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class ResponseDTO<T> {
    private List<T> items;
    
    private Page<T> pageItems;

    private T item;

    private String errorMessage;

    private int errorCode;

    private int statusCode;
}
