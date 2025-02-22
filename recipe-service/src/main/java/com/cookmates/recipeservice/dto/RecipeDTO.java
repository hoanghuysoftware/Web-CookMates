package com.cookmates.recipeservice.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDTO {
    private Long userId;
    private String title;
    private String description;
    private String ingredients;
    private String instructions;
    private int prepTime;
    private int cookTime;
    private int servings;
}
