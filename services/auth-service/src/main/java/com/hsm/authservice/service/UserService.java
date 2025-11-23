package com.hsm.authservice.service;


import com.hsm.authservice.dto.user.CreateUserRequestDTO;
import com.hsm.authservice.dto.user.UpdatePasswordRequestDTO;
import com.hsm.authservice.dto.user.UpdateUserRequestDTO;
import com.hsm.authservice.dto.user.UserResponseDTO;
import com.hsm.authservice.mapper.UserMapper;
import com.hsm.authservice.model.User;
import com.hsm.authservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    public final UserRepository userRepository;
    public final PasswordEncoder passwordEncoder;
    public final UserMapper userMapper;

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Creates a new user with encoded password and default status
     * @param createUserRequestDTO containing user details
     * @return Created user
     * @throws IllegalArgumentException if email already exists
     */
    @Transactional
    public User createUser(@Valid CreateUserRequestDTO createUserRequestDTO){

        log.debug("Creating user with email: {} ", createUserRequestDTO.getEmail());

        if(userRepository.findByEmail(createUserRequestDTO.getEmail()).isPresent()){
            throw new IllegalArgumentException("Email already exists");
        }

        User user = userMapper.toEntityUser(createUserRequestDTO);
        user.setPassword(passwordEncoder.encode(createUserRequestDTO.getPassword()));
        user.setStatus("ACTIVE");

        log.info("Successfully user creation with email: {}", createUserRequestDTO.getEmail());
        return userRepository.save(user);
    }

    /**
     * Updates user information such as email and role, first checking email address user existence
     * */
    @Transactional
    public User updateUser(Long userId, @Valid UpdateUserRequestDTO updateUserRequestDTO){
        log.debug("Updating user with id: {}", userId);

        User user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("User not found"));

        if(userRepository.existsByEmailAndIdNot(updateUserRequestDTO.getEmail(), userId)){
            throw new IllegalArgumentException("Email address already exists");
        }

        userMapper.updateUserFromDto(updateUserRequestDTO, user);

        log.info("Successfully user update with id: {}, ", userId);

        return userRepository.save(user);


    }

    /**
    * Updates user password encoding adding validations
    * */
    @Transactional
    public User updatePassword(Long userId, UpdatePasswordRequestDTO updatePasswordRequestDTO){

        log.debug("Updating password user with id: {} ", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));


        if (!passwordEncoder.matches(updatePasswordRequestDTO.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }

        if (passwordEncoder.matches(passwordEncoder.encode(updatePasswordRequestDTO.getNewPassword()), user.getPassword())){
            throw new IllegalArgumentException("New password must be different from current password");
        }

        user.setPassword(passwordEncoder.encode(updatePasswordRequestDTO.getNewPassword()));

        log.info("Successfully user update password with email: {} ", userId);
        return userRepository.save(user);
    }

    /**
    *  Disables a user by setting status to DISABLED
    * */
    @Transactional
    public User disableUser(Long userId){

        log.debug("Disabling user with id: {} ", userId);

        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("User not found")
        );

        if ("INACTIVE".equals(user.getStatus())){
            throw new IllegalArgumentException("User is already disabled");
        }

        log.info("Successfully user disabled with id: {}", userId);
        user.setStatus("INACTIVE");
        return userRepository.save(user);
    }

    /**
    *  Enables a user by setting status ENABLED
    * */
    @Transactional
    public User enableUser(Long userId){
        log.debug("Enabling user with id: {} ", userId);
        User user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("User not found"));

        if ("ACTIVE".equals(user.getStatus())){
            throw new IllegalArgumentException("User is already enabled");
        }

        log.info("Successfully user enabled with id: {}", userId);
        user.setStatus("ACTIVE");
        return userRepository.save(user);
    }

    /**
     * Converts User entity to UserResponseDTO using mapper
     * */
    public UserResponseDTO userResponseDTO(User user){
        return userMapper.toUserDTO(user);
    }






}
