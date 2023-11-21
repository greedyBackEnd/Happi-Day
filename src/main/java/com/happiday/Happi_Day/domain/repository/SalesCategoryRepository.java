package com.happiday.Happi_Day.domain.repository;

import com.happiday.Happi_Day.domain.entity.product.SalesCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesCategoryRepository extends JpaRepository<SalesCategory, Long> {
    boolean existsByName(String name);
}
