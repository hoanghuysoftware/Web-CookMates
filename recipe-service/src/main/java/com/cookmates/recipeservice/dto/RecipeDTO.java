package com.cookmates.recipeservice.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDTO {
    private Long id;
    private Long userId;
    private String title;
    private String description;
    private String ingredients;
    private String instructions;
    private List<Long> categoryIds;
    private int prepTime;
    private int cookTime;
    private int servings;
}
