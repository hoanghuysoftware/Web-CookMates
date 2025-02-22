package com.cookmates.user_service.dto;

import com.cookmates.user_service.model.RoleName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String fullName;
    private String username;
    private String email;
    private String password;
    private String role;
}
