package com.CloudKeeper.CloudBalanceBackend.modal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {
    String token;
    String emailId;
    String name;
    String role;
}

