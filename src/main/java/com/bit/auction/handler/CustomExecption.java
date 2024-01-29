package com.bit.auction.handler;

public class CustomExecption extends RuntimeException{

    CustomExecption() {

    }

    public CustomExecption(String message) {
        super(message);
    }
}
