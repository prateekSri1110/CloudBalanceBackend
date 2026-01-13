package com.CloudKeeper.CloudBalanceBackend.modal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CeRequestDTO {
    @NotBlank(message = "Account Name cannot be blank!")
    private String accountName;

    @NotNull(message = "Account ID cannot be blank!")
    private Long accountId;

    @NotBlank(message = "Account ARN cannot be blank!")
    private String arn;
}