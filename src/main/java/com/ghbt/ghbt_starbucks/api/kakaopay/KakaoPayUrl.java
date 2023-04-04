package com.ghbt.ghbt_starbucks.api.kakaopay;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum KakaoPayUrl {
    /**
     * backend.grapefruit-honey-black-tea.shop
     * localhost:8080
     * localhost:3000
     * https://www.grapefruit-honey-black-tea.shop
     */
    READY_TO_POST("https://kapi.kakao.com/v1/payment/ready"),
    APPROVE_TO_POST("https://kapi.kakao.com/v1/payment/approve"),
    APPROVAL("https://www.grapefruit-honey-black-tea.shop/pay_success"),
    CANCEL("https://www.grapefruit-honey-black-tea.shop/pay_cancel"),
    FAIL("https://www.grapefruit-honey-black-tea.shop/pay_cancel"),
    ;
    private String url;
}