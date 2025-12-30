package com.CloudKeeper.CloudBalanceBackend.repository;

import com.CloudKeeper.CloudBalanceBackend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByEmailId(String emailId);

    void deleteByEmailId(String emailId);
}
