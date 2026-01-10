package com.CloudKeeper.CloudBalanceBackend.repository;

import com.CloudKeeper.CloudBalanceBackend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmailId(String emailId);

    @Query("""
                SELECT u FROM UserEntity u
                LEFT JOIN FETCH u.accounts
                WHERE u.emailId = :emailId
            """)
    Set<UserEntity> findUserWithAccounts(@Param("emailId") String emailId);
}
