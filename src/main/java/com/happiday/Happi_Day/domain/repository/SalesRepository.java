package com.happiday.Happi_Day.domain.repository;

import com.happiday.Happi_Day.domain.entity.product.Sales;
import com.happiday.Happi_Day.domain.entity.product.SalesCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalesRepository extends JpaRepository<Sales, Long> {
    Page<Sales> findAllBySalesCategory(SalesCategory category, Pageable pageable);
}
