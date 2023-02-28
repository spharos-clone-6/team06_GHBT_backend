package com.ghbt.ghbt_starbucks.shipping_address.dto;

import com.ghbt.ghbt_starbucks.shipping_address.model.ShippingAddress;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseShippingAddress {

  private String receiver;

  private String zipCode;

  private String addressNickname;

  private String detailAddress;

  private String baseAddress;

  private String phoneNumber1;

  private String phoneNumber2;

  private String notice;

  private Boolean isDefault;

  public static ResponseShippingAddress from(ShippingAddress shippingAddress) {
    return ResponseShippingAddress.builder()
        .receiver(shippingAddress.getReceiver())
        .zipCode(shippingAddress.getZipCode())
        .addressNickname(shippingAddress.getAddressNickname())
        .detailAddress(shippingAddress.getDetailAddress())
        .baseAddress(shippingAddress.getBaseAddress())
        .phoneNumber1(shippingAddress.getPhoneNumber1())
        .phoneNumber2(shippingAddress.getPhoneNumber2())
        .notice(shippingAddress.getNotice())
        .isDefault(shippingAddress.getIsDefault())
        .build();
  }
}
