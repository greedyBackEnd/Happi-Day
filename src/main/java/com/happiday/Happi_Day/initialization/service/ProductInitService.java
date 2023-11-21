package com.happiday.Happi_Day.initialization.service;

import com.happiday.Happi_Day.domain.entity.product.Product;
import com.happiday.Happi_Day.domain.entity.product.ProductStatus;
import com.happiday.Happi_Day.domain.entity.product.Sales;
import com.happiday.Happi_Day.domain.repository.ProductRepository;
import com.happiday.Happi_Day.domain.repository.SalesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
            if (!productRepository.existsByName(product.getName())) {
                productRepository.save(product);
            }
        });
    }
}
