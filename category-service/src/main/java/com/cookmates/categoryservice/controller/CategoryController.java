package com.cookmates.categoryservice.controller;

import com.cookmates.categoryservice.dto.CaterogyDTO;
import com.cookmates.categoryservice.dto.PageResponse;
import com.cookmates.categoryservice.dto.ResponseMessage;
import com.cookmates.categoryservice.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final ICategoryService categoryService;

    @GetMapping
    public ResponseEntity<PageResponse> doGetAllCategories(@RequestParam(value = "page", defaultValue = "0") int page,
                                                           @RequestParam(value = "limit", defaultValue = "10") int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<CaterogyDTO> result = categoryService.getAllCaterogies(pageable);
        return new ResponseEntity<>(PageResponse.builder()
                .status(true)
                .message("Success")
                .timestamp(LocalDateTime.now())
                .totalPages(result.getTotalPages())
                .totalElements(result.getTotalElements())
                .data(result.getContent())
                .build(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage> doGetCategoryById(@PathVariable Long id) {
        return new ResponseEntity<>(ResponseMessage.builder()
                .status(true)
                .message("Success")
                .timestamp(LocalDateTime.now())
                .data(categoryService.getCaterogyById(id))
                .build(),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseMessage> doPostCategory(@RequestBody CaterogyDTO categoryDTO) {
        return new ResponseEntity<>(ResponseMessage.builder()
                .status(true)
                .message("Success")
                .timestamp(LocalDateTime.now())
                .data(categoryService.createCaterogy(categoryDTO))
                .build(),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> doPutCategory(@PathVariable Long id,
                                                          @RequestBody CaterogyDTO categoryDTO) {
        return new ResponseEntity<>(ResponseMessage.builder()
                .status(true)
                .message("Success")
                .timestamp(LocalDateTime.now())
                .data(categoryService.updateCaterogy(id, categoryDTO))
                .build(),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> doDeleteCategory(@PathVariable Long id) {
        categoryService.deleteCaterogy(id);
        return new ResponseEntity<>(ResponseMessage.builder()
                .status(true)
                .message("Success")
                .timestamp(LocalDateTime.now())
                .build(),HttpStatus.OK);
    }


}
