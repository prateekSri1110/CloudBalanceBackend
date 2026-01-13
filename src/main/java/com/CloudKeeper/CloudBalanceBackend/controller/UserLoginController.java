package com.CloudKeeper.CloudBalanceBackend.controller;

import com.CloudKeeper.CloudBalanceBackend.modal.UserRequestDTO;
import com.CloudKeeper.CloudBalanceBackend.service.UserLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserLoginController {

    private final UserLoginService userLoginService;

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserRequestDTO cred) {
        return userLoginService.loginUserService(cred.getEmailId(), cred.getPassword());
    }
}