package com.cookmates.recipeservice.service;

import com.cookmates.recipeservice.dto.RecipeDTO;
import com.cookmates.recipeservice.dto.ResponseMessage;
import com.cookmates.recipeservice.exception.DataNotFoundException;
import com.cookmates.recipeservice.external.user.UserServiceClient;
import com.cookmates.recipeservice.external.user.dto.UserResponse;
import com.cookmates.recipeservice.model.Recipe;
import com.cookmates.recipeservice.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeServiceIMPL implements IRecipeService {
    private final RecipeRepository recipeRepository;
    private final ModelMapper modelMapper;
    private final UserServiceClient userServiceClient;

    @Cacheable(value = "users", key = "#userId") // Cache trong bo nho cua spring cung cap
    public UserResponse getUserById(Long userId) {
        ResponseEntity<ResponseMessage> user = userServiceClient.doGetUserById(userId);
        Object obj =  user.getBody().getData();
        UserResponse userResponse;
        if (obj == null)
            throw new DataNotFoundException("User not found or connect failed to user-server");
        else{
            userResponse =  modelMapper.map(obj, UserResponse.class);
        }
        return userResponse;
    }

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
    public RecipeDTO createRecipe(RecipeDTO recipeDTO) { // da bao gom userId trong recipeDTO
        UserResponse userResponse = getUserById(recipeDTO.getUserId());
        Recipe recipe = Recipe.builder()
//                .userId(userResponse.getId())
                .userId(recipeDTO.getUserId())
                .title(recipeDTO.getTitle())
                .description(recipeDTO.getDescription())
                .ingredients(recipeDTO.getIngredients())
                .instructions(recipeDTO.getInstructions())
                .prepTime(recipeDTO.getPrepTime())
                .cookTime(recipeDTO.getCookTime())
                .servings(recipeDTO.getServings())
                .categoryIds(recipeDTO.getCategoryIds())
                .build();
        recipeRepository.save(recipe);
        return modelMapper.map(recipe, RecipeDTO.class);
    }

    @Override
    public RecipeDTO updateRecipe(Long id, RecipeDTO recipeDTO) {
        Recipe recipe = recipeRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Recipe with id " + id + " not found")
        );
        recipe.setUserId(recipeDTO.getUserId());
        recipe.setTitle(recipeDTO.getTitle());
        recipe.setDescription(recipeDTO.getDescription());
        recipe.setIngredients(recipeDTO.getIngredients());
        recipe.setInstructions(recipeDTO.getInstructions());
        recipe.setPrepTime(recipeDTO.getPrepTime());
        recipe.setCookTime(recipeDTO.getCookTime());
        recipe.setServings(recipeDTO.getServings());
        recipeRepository.save(recipe);
        return modelMapper.map(recipe, RecipeDTO.class);
    }

    @Override
    public void deleteRecipe(Long id) {
        Recipe recipe = recipeRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Recipe with id " + id + " not found")
        );
        recipeRepository.delete(recipe);
    }
}
