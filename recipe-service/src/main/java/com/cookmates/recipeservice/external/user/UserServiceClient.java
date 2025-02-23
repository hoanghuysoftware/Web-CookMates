package com.cookmates.recipeservice.external.user;

import com.cookmates.recipeservice.dto.ResponseMessage;
import com.cookmates.recipeservice.external.user.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "http://localhost:8080/api/v1/users", fallback = UserServiceFallback.class)
public interface UserServiceClient {
    @GetMapping("/{id}")
    ResponseEntity<ResponseMessage> doGetUserById(@PathVariable("id") long id);
}
