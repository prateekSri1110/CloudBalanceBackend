package com.CloudKeeper.CloudBalanceBackend.controller;

import com.CloudKeeper.CloudBalanceBackend.entity.AccountsEntity;
import com.CloudKeeper.CloudBalanceBackend.modal.AccountDTO;
import com.CloudKeeper.CloudBalanceBackend.repository.UserRepository;
import com.CloudKeeper.CloudBalanceBackend.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> createAccount(@RequestBody AccountsEntity accountDet) {
        return accountService.createAccount(accountDet);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'READONLY')")
    @GetMapping
    public ResponseEntity<List<AccountDTO>> allAccounts() {
        return accountService.allAccounts();
    }
}
