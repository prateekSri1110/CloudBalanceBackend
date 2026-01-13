package com.CloudKeeper.CloudBalanceBackend.service;

import com.CloudKeeper.CloudBalanceBackend.entity.UserEntity;
import com.CloudKeeper.CloudBalanceBackend.globalExceptions.InvalidEmailidOrPassword;
import com.CloudKeeper.CloudBalanceBackend.repository.UserRepository;
import com.CloudKeeper.CloudBalanceBackend.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserLoginService {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepo;
    private final UserService userService;

    @Transactional
    public ResponseEntity<String> loginUserService(String emailId, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(emailId, password));
            UserEntity user = userRepo.findByEmailId(emailId);
            String token = jwtUtil.generateToken(user);
            userService.updateLoginTime(userRepo.findByEmailId(emailId));
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            throw new InvalidEmailidOrPassword("Invalid EmailId or Password!");
        }
    }
}
