package com.cookmates.categoryservice.service;

import com.cookmates.categoryservice.dto.CaterogyDTO;
import com.cookmates.categoryservice.dto.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICategoryService {
    Page<CaterogyDTO> getAllCaterogies(Pageable pageable);
    CaterogyDTO getCaterogyById(Long id);
    CaterogyDTO createCaterogy(CaterogyDTO caterogy);
    CaterogyDTO updateCaterogy(Long id, CaterogyDTO caterogy);
    void deleteCaterogy(Long id);
}
