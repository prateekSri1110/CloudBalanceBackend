package com.CloudKeeper.CloudBalanceBackend.service;

import com.CloudKeeper.CloudBalanceBackend.repository.UserRepository;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepo;

    public UserDetailService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    @NullMarked
    public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
        UserDetails user = userRepo.findByEmailId(emailId);
        if (user == null) throw new UsernameNotFoundException("User not found!");
        return user;
    }
}