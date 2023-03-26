package com.ghbt.ghbt_starbucks.api.kakaopay.service;

import static com.ghbt.ghbt_starbucks.api.kakaopay.KakoPayUrl.*;

import com.ghbt.ghbt_starbucks.api.kakaopay.dto.KakaoApproveResponse;
import com.ghbt.ghbt_starbucks.api.kakaopay.dto.KakaoReadyResponse;
import com.ghbt.ghbt_starbucks.api.kakaopay.dto.KakaoPayOrderDto;
import com.ghbt.ghbt_starbucks.api.user.model.User;
import com.ghbt.ghbt_starbucks.global.error.ServiceException;
import com.ghbt.ghbt_starbucks.global.security.redis.RedisService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class KakaoPayService {

    private final RedisService redisService;
    @Value("${kakaopay.cid}")
    private String cid;
    @Value("${kakaopay.secret}")
    private String adminKey;

    /**
     * 결제 준비
     */
    public KakaoReadyResponse kakaoPayReady(KakaoPayOrderDto kakaoPayOrderDto) {

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("tax_free_amount", "0");
        parameters.add("partner_order_id", kakaoPayOrderDto.getOrderId());
        parameters.add("partner_user_id", kakaoPayOrderDto.getMemberId());
        parameters.add("item_name", kakaoPayOrderDto.getProductName());
        parameters.add("quantity", kakaoPayOrderDto.getProductCount());
        parameters.add("total_amount", kakaoPayOrderDto.getTotalPrice());
        parameters.add("approval_url", APPROVAL.getUrl());
        parameters.add("cancel_url", CANCEL.getUrl());
        parameters.add("fail_url", FAIL.getUrl());

        KakaoReadyResponse kakaoReadyResponse = new RestTemplate().postForObject(
            READY_TO_POST.getUrl(),
            new HttpEntity<>(parameters, getHeaders()),
            KakaoReadyResponse.class
        );

        redisService.setValuesWithTimeout(kakaoPayOrderDto.getMemberId(), kakaoPayOrderDto.getOrderId() + ","
            + kakaoReadyResponse.getTid(), 5 * 60 * 1000);
        log.info("[ 결제 승인 번호     ]: " + kakaoReadyResponse.getTid());
        log.info("[ 결제 준비 일시     ]: " + kakaoReadyResponse.getCreated_at());
        log.info("[ PC 승인 URL      ]: " + kakaoReadyResponse.getNext_redirect_pc_url());
        log.info("[ MOBILE 승인 URL  ]: " + kakaoReadyResponse.getNext_redirect_mobile_url());

        return kakaoReadyResponse;
    }

    /**
     * 결제 승인
     */

    public KakaoApproveResponse approveKakaopayment(String pgToken, User loginUser) {
        String[] orderIdAndTId = redisService.getValues(loginUser.getId().toString()).split(",");
        String orderId = orderIdAndTId[0];
        String tId = orderIdAndTId[1];
        log.info("orderId" + orderId);
        log.info("tId" + tId);
        log.info("pgToke" + pgToken);
        log.info("loginUser" + loginUser.toString());
        // 카카오 요청
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("tid", tId);
        parameters.add("partner_order_id", orderId);
        parameters.add("partner_user_id", loginUser.getId().toString());
        parameters.add("pg_token", pgToken);

        return new RestTemplate().postForObject(
            APPROVE_TO_POST.getUrl(),
            new HttpEntity<>(parameters, getHeaders()),
            KakaoApproveResponse.class
        );

    }

    /**
     * 카카오 요구 헤더값
     */
    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();

        String auth = "KakaoAK " + adminKey;
        httpHeaders.set("Authorization", auth);
        httpHeaders.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        return httpHeaders;
    }

}