package com.CloudKeeper.CloudBalanceBackend.modal;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class CEdataDTO {

    private String type;
    private String subtype;
    private Map<String, Long> monthCost;
    private Long totalCost;
}
