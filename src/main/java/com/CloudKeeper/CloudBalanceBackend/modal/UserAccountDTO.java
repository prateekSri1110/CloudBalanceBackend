package com.CloudKeeper.CloudBalanceBackend.modal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserAccountDTO {
    private Long accountId;
    private String accountName;
    private String arn;
}


//#spring.datasource.snowflake.password:Prateek1005Cloudkeeper