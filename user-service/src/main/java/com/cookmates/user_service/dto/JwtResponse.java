package com.cookmates.user_service.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private boolean status;
    private String message;
    private String tokenType;
    private LocalDateTime timestamp;
    private String token;
}
