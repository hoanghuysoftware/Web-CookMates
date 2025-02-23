package com.cookmates.categoryservice.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessage {
    private boolean status;
    private String message;
    private LocalDateTime  timestamp;
    private Object data;
}
