package com.happiday.Happi_Day.domain.entity.subscription.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionRequestDto {
    private Long artistId;
    private Long teamId;
}
