package com.cookmates.recipeservice.service;

import com.cookmates.recipeservice.dto.RecipeDTO;
import com.cookmates.recipeservice.exception.DataNotFoundException;
import com.cookmates.recipeservice.model.Recipe;
import com.cookmates.recipeservice.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeServiceIMPL implements IRecipeService{
    private final RecipeRepository recipeRepository;
    private final ModelMapper modelMapper;

    @Override
    public Page<RecipeDTO> getAllRecipes(Pageable pageable) {
        return recipeRepository.findAll(pageable)
                .map(r -> modelMapper.map(r, RecipeDTO.class));
    }

    @Override
    public RecipeDTO getRecipeById(Long id) {
        Recipe recipe = recipeRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Recipe with id " + id + " not found")
        );
        return modelMapper.map(recipe, RecipeDTO.class);
    }

    @Override
    public RecipeDTO getRecipeByTitle(String title) {
        return null;
    }

    @Override
    public RecipeDTO createRecipe(RecipeDTO recipeDTO) {
        return null;
    }

    @Override
    public RecipeDTO updateRecipe(Long id, RecipeDTO recipeDTO) {
        return null;
    }

    @Override
    public void deleteRecipe(Long id) {

    }
}
