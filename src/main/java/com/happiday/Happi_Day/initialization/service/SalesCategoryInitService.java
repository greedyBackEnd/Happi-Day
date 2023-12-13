package com.happiday.Happi_Day.initialization.service;

import com.happiday.Happi_Day.domain.entity.product.SalesCategory;
import com.happiday.Happi_Day.domain.repository.SalesCategoryRepository;
import com.happiday.Happi_Day.exception.CustomException;
import com.happiday.Happi_Day.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SalesCategoryInitService {

    private final SalesCategoryRepository salesCategoryRepository;

    public void initSalesCategories() {
        List<SalesCategory> categories = List.of(
                SalesCategory.builder()
                        .name("굿즈")
                        .description("굿즈 카테고리입니다.")
                        .build(),
                SalesCategory.builder()
                        .name("공구")
                        .description("공구 카테고리입니다.")
                        .build()
        );

        categories.forEach(category -> {
            try {
                if (!salesCategoryRepository.existsByName(category.getName())) {
                    salesCategoryRepository.save(category);
                }
            } catch (Exception e) {
                log.error("DB Seeder 판매 카테고리 저장 중 예외 발생 - 카테고리명: {}", category.getName(), e);
                throw new CustomException(ErrorCode.DB_SEEDER_SALES_CATEGORY_SAVE_ERROR);
            }
        });
    }
}
