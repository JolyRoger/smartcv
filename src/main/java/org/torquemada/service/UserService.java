package org.torquemada.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.torquemada.dto.UserDto;
import org.torquemada.dto.UserLoginDto;
import org.torquemada.dto.UserResponseDto;
import org.torquemada.entity.User;
import org.torquemada.repository.UserRepository;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDto createUser(UserDto userDto) {
        if (userRepository.findByEmail(userDto.email()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }

        User user = new User();
        user.setName(userDto.name());
        user.setEmail(userDto.email());
        user.setPasswordHash(passwordEncoder.encode(userDto.password()));
        user.setCreatedAt(LocalDateTime.now());

        user = userRepository.save(user);

        return new UserResponseDto(user.getId(), user.getName(), user.getEmail());
    }

    public UserResponseDto login(UserLoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.email())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (!passwordEncoder.matches(loginDto.password(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        // Here you'd typically return a JWT token or session info.
        return new UserResponseDto(user.getId(), user.getName(), user.getEmail());
    }

    public void logout(Long userId) {
        // Stub for session invalidation or JWT blacklist
        log.info("User with ID {} has been logged out", userId);
    }
}
