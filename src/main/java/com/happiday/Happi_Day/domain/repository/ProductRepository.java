package com.happiday.Happi_Day.domain.repository;

import com.happiday.Happi_Day.domain.entity.product.Product;
import com.happiday.Happi_Day.domain.entity.product.Sales;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    void deleteAllBySales(Sales sales);
    Optional<Product> findByNameAndSales(String name, Sales sales);

    boolean existsByName(String name);
}
