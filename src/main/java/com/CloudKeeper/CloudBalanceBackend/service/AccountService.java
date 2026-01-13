package com.CloudKeeper.CloudBalanceBackend.service;

import com.CloudKeeper.CloudBalanceBackend.entity.AccountsEntity;
import com.CloudKeeper.CloudBalanceBackend.modal.AccountDTO;
import com.CloudKeeper.CloudBalanceBackend.modal.CeRequestDTO;
import com.CloudKeeper.CloudBalanceBackend.repository.AccountsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountsRepository accountsRepo;

    //    all accounts
    public ResponseEntity<List<AccountDTO>> allAccounts() {
        List<AccountDTO> allAccounts = accountsRepo.findAll()
                .stream()
                .map(account -> new AccountDTO(
                        account.getAccountId(),
                        account.getAccountName(),
                        account.getArn()
                )).toList();
        return ResponseEntity.ok(allAccounts);
    }

    //    orphan account creation
    public ResponseEntity<String> createAccount(CeRequestDTO accountDet) {
        AccountsEntity existingAcc = accountsRepo.findByAccountId(accountDet.getAccountId());

        if (existingAcc != null) {
            return ResponseEntity.badRequest()
                    .body("Account with AccountId : "
                            + accountDet.getAccountId() + " already exists!");
        }

        AccountsEntity account = new AccountsEntity();
        account.setAccountName(accountDet.getAccountName());
        account.setAccountId(accountDet.getAccountId());
        account.setArn(accountDet.getArn());

        accountsRepo.save(account);

        return ResponseEntity.accepted().body("Account onboarding successful!");
    }
}