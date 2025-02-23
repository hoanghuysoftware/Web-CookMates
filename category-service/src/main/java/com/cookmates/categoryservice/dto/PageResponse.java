package com.cookmates.categoryservice.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse {
    private boolean status;
    private String message;
    private LocalDateTime timestamp;
    private int totalPages;
    private long totalElements;
    private List<CaterogyDTO> data;
}
