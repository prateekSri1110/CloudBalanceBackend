package com.CloudKeeper.CloudBalanceBackend.service;

import com.CloudKeeper.CloudBalanceBackend.entity.AccountsEntity;
import com.CloudKeeper.CloudBalanceBackend.globalExceptions.ExistingAccountException;
import com.CloudKeeper.CloudBalanceBackend.repository.AccountsRepository;
import com.CloudKeeper.CloudBalanceBackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OnboardingService {
    private final AccountsRepository accountsRepo;
    private final UserRepository userRepo;

    //    orphan account creation
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

//    public void assignAccount(String emailId, List<Long> accounts) {
//        UserEntity user = userRepo.findByEmailId(emailId);
//        if(!user.getRole().equals("CUSTOMER")){
//            throw new RuntimeException("account assignment is only for customers!");
//        }
//
//        List<AccountsEntity> accountList = accountsRepo.findAllById(accounts);
//        if(accountList.isEmpty()){
//            throw new RuntimeException("Accounts not found!");
//        }
//    }
}