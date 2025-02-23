package com.cookmates.recipeservice.controller;

import com.cookmates.recipeservice.dto.PageResponse;
import com.cookmates.recipeservice.dto.RecipeDTO;
import com.cookmates.recipeservice.dto.ResponseMessage;
import com.cookmates.recipeservice.external.user.UserServiceClient;
import com.cookmates.recipeservice.external.user.dto.UserResponse;
import com.cookmates.recipeservice.repository.RecipeRepository;
import com.cookmates.recipeservice.service.IRecipeService;
import com.cookmates.recipeservice.service.RecipeServiceIMPL;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/recipe")
@RequiredArgsConstructor
public class RecipeController {
    private final IRecipeService recipeService;
    private final RecipeServiceIMPL recipeServiceIMPLTest;
    private final UserServiceClient userServiceClient;

    @GetMapping("/test/{userId}")
    public ResponseEntity<?> testFeign(@PathVariable Long userId) {
        ResponseEntity<ResponseMessage> user = userServiceClient.doGetUserById(userId);
        return ResponseEntity.ok(recipeServiceIMPLTest.getUserById(userId));
    }

    @GetMapping
    public ResponseEntity<PageResponse> doGetAllRecipe(@RequestParam(value = "page", defaultValue = "0") int page,
                                                       @RequestParam(value = "limit", defaultValue = "10") int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<RecipeDTO> result = recipeService.getAllRecipes(pageable);
        return new ResponseEntity<>(PageResponse.builder()
                .status(true)
                .message("Success")
                .timestamp(LocalDateTime.now())
                .totalPages(result.getTotalPages())
                .totalElements(result.getTotalElements())
                .data(result.getContent())
                .build(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage> doGetRecipeById(@PathVariable Long id) {
        return new ResponseEntity<>(ResponseMessage.builder()
                .status(true)
                .message("Success")
                .timestamp(LocalDateTime.now())
                .data(recipeService.getRecipeById(id))
                .build(),HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ResponseMessage> doPostRecipe(@RequestBody RecipeDTO recipeDTO) {
        return new ResponseEntity<>(ResponseMessage.builder()
                .status(true)
                .message("Success")
                .timestamp(LocalDateTime.now())
                .data(recipeService.createRecipe(recipeDTO))
                .build(),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> doPutRecipe(@PathVariable Long id,@RequestBody RecipeDTO recipeDTO) {
        return new ResponseEntity<>(ResponseMessage.builder()
                .status(true)
                .message("Success")
                .timestamp(LocalDateTime.now())
                .data(recipeService.updateRecipe(id, recipeDTO))
                .build(),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> doDeleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
        return new ResponseEntity<>(ResponseMessage.builder()
                .status(true)
                .message("Success")
                .timestamp(LocalDateTime.now())
                .build(),HttpStatus.OK);
    }

}
