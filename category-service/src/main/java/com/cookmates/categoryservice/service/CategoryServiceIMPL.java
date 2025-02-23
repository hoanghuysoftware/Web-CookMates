package com.cookmates.categoryservice.service;

import com.cookmates.categoryservice.dto.CaterogyDTO;
import com.cookmates.categoryservice.dto.PageResponse;
import com.cookmates.categoryservice.exception.DataNotFoundException;
import com.cookmates.categoryservice.model.Category;
import com.cookmates.categoryservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceIMPL implements ICategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public Page<CaterogyDTO> getAllCaterogies(Pageable pageable) {
        return categoryRepository.findAll(pageable).map(
                m -> modelMapper.map(m, CaterogyDTO.class)
        );
    }

    @Override
    public CaterogyDTO getCaterogyById(Long id) {
        Category result = categoryRepository.getCategoryById(id).orElseThrow(
                () -> new DataNotFoundException("Category not found")
        );
        return modelMapper.map(result, CaterogyDTO.class);
    }

    @Override
    public CaterogyDTO createCaterogy(CaterogyDTO caterogy) {
        boolean exist = categoryRepository.existsByName(caterogy.getName());
        if(exist) {
            throw new DataNotFoundException("Category name already exists");
        }
        Category category = Category.builder()
                .name(caterogy.getName())
                .build();
        categoryRepository.save(category);

        return modelMapper.map(category, CaterogyDTO.class);
    }

    @Override
    public CaterogyDTO updateCaterogy(Long id, CaterogyDTO caterogy) {
        Category result = categoryRepository.getCategoryById(id).orElseThrow(
                () -> new DataNotFoundException("Category not found")
        );
        result.setName(caterogy.getName());
        categoryRepository.save(result);
        return modelMapper.map(result, CaterogyDTO.class);
    }

    @Override
    public void deleteCaterogy(Long id) {
        Category result = categoryRepository.getCategoryById(id).orElseThrow(
                () -> new DataNotFoundException("Category not found")
        );
        categoryRepository.delete(result);
    }
}
