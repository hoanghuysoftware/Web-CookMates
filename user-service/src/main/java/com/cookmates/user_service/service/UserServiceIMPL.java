package com.cookmates.user_service.service;

import com.cookmates.user_service.dto.UserDTO;
import com.cookmates.user_service.exception.DataNotFoundException;
import com.cookmates.user_service.model.RoleName;
import com.cookmates.user_service.model.User;
import com.cookmates.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceIMPL implements IUserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Page<UserDTO> getAllUser(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(u -> modelMapper.map(u, UserDTO.class));
    }

    @Override
    public UserDTO getUserById(long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("User with id " + id + " not found")
        );
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        User user = userRepository.getUserByUsername(username).orElseThrow(
                () -> new DataNotFoundException("User with username " + username + " not found")
        );
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        boolean existsUsername = userRepository.existsByUsername(userDTO.getUsername());
        if (existsUsername) {
            throw new RuntimeException("User with username " + userDTO.getUsername() + " already exists");
        }
        boolean existsEmail = userRepository.existsByEmail(userDTO.getEmail());
        if (existsEmail) {
            throw new RuntimeException("User with email " + userDTO.getEmail() + " already exists");
        }

        User user = User.builder()
                .fullName(userDTO.getFullName())
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .role(RoleName.valueOf(userDTO.getRole()))
                .build();
        userRepository.save(user);
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("User with id " + id + " not found")
        );
        user.setFullName(userDTO.getFullName());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRole(RoleName.valueOf(userDTO.getRole()));
        userRepository.save(user);

        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public void deleteUser(long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("User with id " + id + " not found")
        );
        userRepository.delete(user);
    }
}
