package com.cookmates.user_service.controller;

import com.cookmates.user_service.dto.JwtResponse;
import com.cookmates.user_service.dto.LoginForm;
import com.cookmates.user_service.dto.ResponseMessage;
import com.cookmates.user_service.dto.UserDTO;
import com.cookmates.user_service.service.IUserService;
import com.cookmates.user_service.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @GetMapping
    public ResponseEntity<ResponseMessage> doGetAllUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                                         @RequestParam(value = "limit", defaultValue = "10") int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        return new ResponseEntity<>(ResponseMessage.builder()
                .status(true)
                .message("success")
                .timestamp(LocalDateTime.now())
                .data(userService.getAllUser(pageable))
                .build(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage> doGetUserById(@PathVariable("id") long id) {
        return new ResponseEntity<>(ResponseMessage.builder()
                .status(true)
                .message("success")
                .timestamp(LocalDateTime.now())
                .data(userService.getUserById(id))
                .build(), HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<?> doLogin(@RequestBody LoginForm loginForm) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword())
            );
            String token = jwtUtils.generateToken(auth);
            return new ResponseEntity<>(JwtResponse.builder()
                    .status(true)
                    .message("success")
                    .timestamp(LocalDateTime.now())
                    .tokenType("Bearer")
                    .token(token)
                    .build(), HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(ResponseMessage.builder()
                    .status(false)
                    .message("Invalid username or password")
                    .timestamp(LocalDateTime.now())
                    .build(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseMessage> doRegisterUser(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(ResponseMessage.builder()
                .status(true)
                .message("success")
                .timestamp(LocalDateTime.now())
                .data(userService.createUser(userDTO))
                .build(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> doUpdateUser(@PathVariable("id") long id, @RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(ResponseMessage.builder()
                .status(true)
                .message("success")
                .timestamp(LocalDateTime.now())
                .data(userService.updateUser(id, userDTO))
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> doDeleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(ResponseMessage.builder()
                .status(true)
                .message("deleted user successfully")
                .timestamp(LocalDateTime.now())
                .build(), HttpStatus.OK);
    }
}
