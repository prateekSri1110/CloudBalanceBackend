package com.CloudKeeper.CloudBalanceBackend.service;

import com.CloudKeeper.CloudBalanceBackend.entity.AccountsEntity;
import com.CloudKeeper.CloudBalanceBackend.globalExceptions.ExistingAccountException;
import com.CloudKeeper.CloudBalanceBackend.repository.AccountsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OnboardingService {
    private final AccountsRepository accountsRepo;

    public OnboardingService(AccountsRepository accountsRepo) {
        this.accountsRepo = accountsRepo;
    }


    //    orphan account created
    public ResponseEntity<String> createAccount(AccountsEntity accountDet) {
        AccountsEntity existingAcc = accountsRepo.getAccountByArn(accountDet.getArn());

        if (existingAcc != null) {
            throw new ExistingAccountException("Account with arn : " + accountDet.getArn() + " already exists!");
        }
        if (accountDet.getAccName() == null) {
            throw new IllegalArgumentException("Account name is required");
        }

        accountDet.setAssigned(false);
        accountsRepo.save(accountDet);
        return ResponseEntity.accepted().body("Account onboarding successful!");
    }
}
