package com.bit.auction.user.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class InquiryFileId implements Serializable {
    private Long inquiry;
    private Long inquiryFileNo;
}
