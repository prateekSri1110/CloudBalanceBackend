package com.CloudKeeper.CloudBalanceBackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
public class AccountsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String accountName;

    @Column(nullable = false)
    private Long accountId;

    @Column(nullable = false)
    private String arn;

    @ManyToMany(mappedBy = "accounts", cascade = CascadeType.MERGE)
    private Set<UserEntity> users = new HashSet<>();
}
