package com.hsm.authservice.controller;

import com.hsm.authservice.dto.user.CreateUserRequestDTO;
import com.hsm.authservice.dto.user.UpdatePasswordRequestDTO;
import com.hsm.authservice.dto.user.UpdateUserRequestDTO;
import com.hsm.authservice.dto.user.UserResponseDTO;
import com.hsm.authservice.mapper.UserMapper;
import com.hsm.authservice.model.User;
import com.hsm.authservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserMapper userMapper;
    private final UserService userService;

    @Operation(summary = "Create a new user")
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(
            @Valid @RequestBody CreateUserRequestDTO createUserRequestDTO) {
        try {
            User user = userService.createUser(createUserRequestDTO);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(userService.userResponseDTO(user));
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(summary = "Update user information")
    @PutMapping("/{userId}/password")
    public ResponseEntity<UserResponseDTO> updateUSer(
           @PathVariable Long userId,
           @Valid @RequestBody UpdateUserRequestDTO updateUserRequestDTO) {
        try {
            User user = userService.updateUser(userId, updateUserRequestDTO);
            return ResponseEntity.ok(userService.userResponseDTO(user));
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(summary = "Update user password")
    @PutMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> updatePassword(
            @PathVariable Long userId, @Valid
            @RequestBody UpdatePasswordRequestDTO updatePasswordRequestDTO) {
        try {
            User user = userService.updatePassword(userId, updatePasswordRequestDTO);
            return ResponseEntity.ok(userMapper.toUserDTO(user));
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(summary = "Disable a user")
    @PutMapping("/{userId}/disable")
    public ResponseEntity<UserResponseDTO> disableUser(@PathVariable Long userId) {
        try {
            User user = userService.disableUser(userId);
            return ResponseEntity.ok(userService.userResponseDTO(user));
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(summary = "Enable a user")
    @PutMapping("/{userId}/enable")
    public ResponseEntity<UserResponseDTO> enableUser(
            @PathVariable Long userId
    ){
        try {
            User user = userService.enableUser(userId);
            return ResponseEntity.ok(userService.userResponseDTO(user));
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
