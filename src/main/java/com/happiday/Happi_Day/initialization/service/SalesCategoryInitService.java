package com.happiday.Happi_Day.initialization.service;

import com.happiday.Happi_Day.domain.entity.product.SalesCategory;
import com.happiday.Happi_Day.domain.repository.SalesCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalesCategoryInitService {

    private final SalesCategoryRepository salesCategoryRepository;

    public void initSalesCategories() {
        List<SalesCategory> categories = List.of(
                SalesCategory.builder()
                        .name("의류")
                        .description("의류 카테고리입니다.")
                        .build(),
                SalesCategory.builder()
                        .name("전자제품")
                        .description("전자제품 카테고리입니다.")
                        .build(),
                SalesCategory.builder()
                        .name("앨범")
                        .description("앨범 카테고리입니다.")
                        .build()
        );

        categories.forEach(category -> {
            if (!salesCategoryRepository.existsByName(category.getName())) {
                salesCategoryRepository.save(category);
            }
        });
    }
}
