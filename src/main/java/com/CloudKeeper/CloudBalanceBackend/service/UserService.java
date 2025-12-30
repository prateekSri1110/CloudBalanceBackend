package com.CloudKeeper.CloudBalanceBackend.service;

import com.CloudKeeper.CloudBalanceBackend.entity.UserEntity;
import com.CloudKeeper.CloudBalanceBackend.modal.UserRequestDTO;
import com.CloudKeeper.CloudBalanceBackend.modal.UserResponseDTO;
import com.CloudKeeper.CloudBalanceBackend.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    //  constructor injection
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    //    get all users
    public List<UserResponseDTO> getAllUsers() {
        List<UserEntity> allUsers = userRepo.findAll();
        return allUsers.stream().map(user -> {
            UserResponseDTO dto = new UserResponseDTO();
            dto.setFirstName(user.getFirstName());
            dto.setLastName(user.getLastName());
            dto.setEmailId(user.getEmailId());
            dto.setActive(user.getActive());
            dto.setLastLogin(user.getLastLogin());
            dto.setRole(user.getRole());
            return dto;
        }).toList();
    }

    //    add new user
    public ResponseEntity<String> createUser(UserRequestDTO user) {

        if (userRepo.findByEmailId(user.getEmailId()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        }

        UserEntity entity = new UserEntity();
        entity.setFirstName(user.getFirstName());
        entity.setLastName(user.getLastName());
        entity.setEmailId(user.getEmailId());
        entity.setPassword(passwordEncoder.encode(user.getPassword()));
        entity.setRole(user.getRole());
        entity.setActive(user.getActive());
        entity.setLastLogin(user.getLastLogin());

        userRepo.save(entity);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //    update user details
    public ResponseEntity<String> updateUser(UserRequestDTO updatedUser) {
        UserEntity user = userRepo.findByEmailId(updatedUser.getEmailId());

        if (user == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with email Id : " + updatedUser.getEmailId() + " doesn't exist!");

        if (updatedUser.getFirstName() != null) user.setFirstName(updatedUser.getFirstName());
        if (updatedUser.getLastName() != null) user.setLastName(updatedUser.getLastName());
        user.setRole(updatedUser.getRole());

        userRepo.save(user);
        return ResponseEntity.ok("User Updated successfully!" + user.getEmailId());
    }

    //    update status
    public ResponseEntity<String> updateStatus(String emailId) {
        UserEntity user = userRepo.findByEmailId(emailId);
        if (user == null) return ResponseEntity.badRequest().body("something went wrong!");
        if (user.getActive() == null) user.setActive(true);
        else user.setActive(!user.getActive());
        userRepo.save(user);
        return ResponseEntity.accepted().body("user status updated!");
    }

    //    update login time
    @Transactional(propagation = Propagation.MANDATORY)
    public void updateLoginTime(UserEntity userLogin) {
        userLogin.setLastLogin(LocalDateTime.now());
        userRepo.save(userLogin);
    }

    //    delete user
    public ResponseEntity<String> deleteByEmailId(String emailId) {
        UserEntity user = userRepo.findByEmailId(emailId);
        if (user == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with email Id : " + emailId + " doesn't exist!");
        userRepo.delete(user);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("User with email Id : " + emailId + " is deleted successfully!");
    }
}
