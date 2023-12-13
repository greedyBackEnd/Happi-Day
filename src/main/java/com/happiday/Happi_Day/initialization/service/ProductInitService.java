package com.happiday.Happi_Day.initialization.service;

import com.happiday.Happi_Day.domain.entity.product.Product;
import com.happiday.Happi_Day.domain.entity.product.ProductStatus;
import com.happiday.Happi_Day.domain.entity.product.Sales;
import com.happiday.Happi_Day.domain.repository.ProductRepository;
import com.happiday.Happi_Day.domain.repository.SalesRepository;
import com.happiday.Happi_Day.exception.CustomException;
import com.happiday.Happi_Day.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductInitService {

    private final ProductRepository productRepository;
    private final SalesRepository salesRepository;

    public void initProducts() {
        Sales sales1 = salesRepository.findById(2L).orElse(null);
        Sales sales2 = salesRepository.findById(2L).orElse(null);

        List<Product> products = List.of(
                Product.builder()
                        .sales(sales1)
                        .name("동방신기 흰색")
                        .price(12000)
                        .stock(99)
                        .productStatus(ProductStatus.ON_SALE)
                        .build(),
                Product.builder()
                        .sales(sales2)
                        .name("god 검은색")
                        .price(12000)
                        .stock(99)
                        .productStatus(ProductStatus.ON_SALE)
                        .build()
        );

        products.forEach(product -> {
            try {
                if (!productRepository.existsByName(product.getName())) {
                    productRepository.save(product);
                }
            } catch (Exception e) {
                log.error("DB Seeder 상품 저장 중 예외 발생 - 상품명: {}", product.getName(), e);
                throw new CustomException(ErrorCode.DB_SEEDER_PRODUCT_SAVE_ERROR);
            }
        });
    }
}
