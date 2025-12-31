package com.CloudKeeper.CloudBalanceBackend.repository;

import com.CloudKeeper.CloudBalanceBackend.entity.AccountsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsRepository extends JpaRepository<AccountsEntity, Long> {
    AccountsEntity getAccountByArn(String arn);

}
