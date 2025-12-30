package com.CloudKeeper.CloudBalanceBackend.service;

import com.CloudKeeper.CloudBalanceBackend.entity.UserEntity;
import com.CloudKeeper.CloudBalanceBackend.globalExceptions.InvalidEmailidOrPassword;
import com.CloudKeeper.CloudBalanceBackend.modal.LoginResponseDTO;
import com.CloudKeeper.CloudBalanceBackend.repository.UserRepository;
import com.CloudKeeper.CloudBalanceBackend.utils.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserLoginService {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepo;
    private final UserService userService;

    public UserLoginService(JwtUtil jwtUtil, AuthenticationManager authenticationManager, UserRepository userRepo, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userRepo = userRepo;
        this.userService = userService;
    }

    @Transactional
    public ResponseEntity<LoginResponseDTO> loginUserService(String emailId, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(emailId, password));
            UserEntity user = userRepo.findByEmailId(emailId);
            String token = jwtUtil.generateToken(user);
            userService.updateLoginTime(userRepo.findByEmailId(emailId));
            return ResponseEntity.ok(new LoginResponseDTO(token, user.getEmailId(), user.getFirstName() + " " + user.getLastName(), user.getRole()));
        } catch (Exception e) {
            throw new InvalidEmailidOrPassword("Invalid EmailId or Password!");
        }
    }
}
