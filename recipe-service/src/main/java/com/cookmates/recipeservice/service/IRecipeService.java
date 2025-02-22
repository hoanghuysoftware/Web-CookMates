package com.cookmates.recipeservice.service;

import com.cookmates.recipeservice.dto.RecipeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IRecipeService {
    Page<RecipeDTO> getAllRecipes(Pageable pageable);
    RecipeDTO getRecipeById(Long id);
    RecipeDTO getRecipeByTitle(String title);
    RecipeDTO createRecipe(RecipeDTO recipeDTO);
    RecipeDTO updateRecipe(Long id, RecipeDTO recipeDTO);
    void deleteRecipe(Long id);
}
