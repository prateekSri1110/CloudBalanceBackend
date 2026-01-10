package com.CloudKeeper.CloudBalanceBackend.modal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccountDTO {
    private Long accountId;
    private String accName;
    private String arn;
}
