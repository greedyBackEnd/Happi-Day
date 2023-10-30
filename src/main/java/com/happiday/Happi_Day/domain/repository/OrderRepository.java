package com.happiday.Happi_Day.domain.repository;

import com.happiday.Happi_Day.domain.entity.product.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
