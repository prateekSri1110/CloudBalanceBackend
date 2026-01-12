package com.CloudKeeper.CloudBalanceBackend.controller;

import com.CloudKeeper.CloudBalanceBackend.entity.UserEntity;
import com.CloudKeeper.CloudBalanceBackend.modal.ProfileDTO;
import com.CloudKeeper.CloudBalanceBackend.modal.UserAccountDTO;
import com.CloudKeeper.CloudBalanceBackend.modal.UserRequestDTO;
import com.CloudKeeper.CloudBalanceBackend.modal.UserResponseDTO;
import com.CloudKeeper.CloudBalanceBackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasAnyRole('ADMIN', 'READONLY')")
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> allUsers = userService.getAllUsers();
        if (allUsers.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(allUsers);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UserEntity> addUser(@RequestBody UserRequestDTO user) {
        return userService.createUser(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<String> updateUser(@RequestBody UserRequestDTO user) {
        return userService.updateUser(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/status")
    public ResponseEntity<String> updateStatus(@RequestParam("emailId") String emailId) {
        return userService.updateStatus(emailId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping
    public ResponseEntity<String> deleteUser(@RequestParam("emailId") String emailId) {
        return userService.deleteByEmailId(emailId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/assignaccount")
    public void assignAccountToUser(@RequestBody UserRequestDTO user, @RequestParam("accountIds") List<Long> accountIds) {
        userService.assignAccountToUser(user, accountIds);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'READONLY')")
    @GetMapping("/useraccounts")
    public ResponseEntity<Set<UserAccountDTO>> associatedAccounts(@RequestParam("emailId") String emailId) {
        return ResponseEntity.ok(userService.associatedAccounts(emailId));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'READONLY','CUSTOMER')")
    @GetMapping("/profile")
    public ResponseEntity<ProfileDTO> profile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        UserDetails user = (UserDetails) auth.getPrincipal();
        assert user != null;
        return userService.profile(user.getUsername());
    }
}


//ADMIN - getAll, addUser, DeleteUser, UpdateStatus, UpdateUser
//CUSTOMER -  cannot view or modify
//READONLY - getAll - only view