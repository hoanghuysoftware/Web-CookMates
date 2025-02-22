package com.cookmates.user_service.service;

import com.cookmates.user_service.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {
    Page<UserDTO> getAllUser(Pageable pageable);
    UserDTO getUserById(long id);
    UserDTO getUserByUsername(String username);
    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUser(Long id, UserDTO userDTO);
    void deleteUser(long id);
}
