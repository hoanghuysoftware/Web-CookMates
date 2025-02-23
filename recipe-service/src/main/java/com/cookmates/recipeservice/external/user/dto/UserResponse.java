package com.cookmates.recipeservice.external.user.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String fullName;
    private String username;
    private String email;
    private String password;
    private String role;
}
