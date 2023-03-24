package com.ghbt.ghbt_starbucks.api.kakaopay;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum KakoPayUrl {
    READY_TO_POST("https://kapi.kakao.com/v1/payment/ready"),
    APPROVE_TO_POST("https://kapi.kakao.com/v1/payment/approve"),
    CANCEL("http://localhost:8080/payment/cancel"),
    APPROVAL("http://localhost:8080/payment/success"),
    FAIL("http://localhost:8080/payment/fail"),
    ;

    private String url;

}
