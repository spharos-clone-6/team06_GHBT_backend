package com.ghbt.ghbt_starbucks.api.mobile_card.service;

import com.ghbt.ghbt_starbucks.api.mobile_card.dto.RequestEnrollMobileCard;
import com.ghbt.ghbt_starbucks.api.mobile_card.dto.ResponseMobileCard;
import java.util.List;

public interface IMobileCardService {

    List<ResponseMobileCard> getAllMobileCard();

    ResponseMobileCard getOneMobileCard(Long mobileCardId);

    Long enrollMobileCard(RequestEnrollMobileCard requestEnrollMobileCard);
}
