package com.cookmates.recipeservice.external.user;

import com.cookmates.recipeservice.dto.ResponseMessage;
import com.cookmates.recipeservice.external.user.dto.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserServiceFallback implements UserServiceClient {

    @Override
    public ResponseEntity<ResponseMessage> doGetUserById(long id) {
        return new ResponseEntity<>(ResponseMessage.builder()
                .status(false)
                .message("User not found or connection is closed")
                .timestamp(LocalDateTime.now())
                .data(null)
                .build(), HttpStatus.BAD_REQUEST);
    }
}
