package com.bit.auction.goods.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class LikeCntId implements Serializable {
    private Long auction;
    private long user;
}
