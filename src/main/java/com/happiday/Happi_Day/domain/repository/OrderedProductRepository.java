package com.happiday.Happi_Day.domain.repository;

import com.happiday.Happi_Day.domain.entity.product.Order;
import com.happiday.Happi_Day.domain.entity.product.OrderedProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderedProductRepository extends JpaRepository<OrderedProduct, Long> {
    List<OrderedProduct> findAllByOrder(Order order);
}
