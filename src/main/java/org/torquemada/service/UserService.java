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
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<UserResponseDto> createUser(UserDto userDto) {
        final User[] user = { null };

        userRepository.findByEmail(userDto.email()).ifPresentOrElse(existingUser -> { },
                () -> {
                    user[0] = new User();
                    user[0].setName(userDto.name());
                    user[0].setEmail(userDto.email());
                    user[0].setPasswordHash(passwordEncoder.encode(userDto.password()));
                    user[0].setCreatedAt(LocalDateTime.now());
                    user[0] = userRepository.save(user[0]);
                });

        return Optional.ofNullable(user[0]).map(newUser -> new UserResponseDto(newUser.getId(), newUser.getName(), newUser.getEmail()));
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
