package com.happiday.Happi_Day.domain.repository;

import com.happiday.Happi_Day.domain.entity.product.Order;
import com.happiday.Happi_Day.domain.entity.product.Sales;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findAllBySales(Sales sales, Pageable pageable);
}
