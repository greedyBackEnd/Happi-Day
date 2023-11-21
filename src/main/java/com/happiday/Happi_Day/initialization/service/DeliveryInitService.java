package com.happiday.Happi_Day.initialization.service;

import com.happiday.Happi_Day.domain.entity.product.Delivery;
import com.happiday.Happi_Day.domain.entity.product.Sales;
import com.happiday.Happi_Day.domain.repository.DeliveryRepository;
import com.happiday.Happi_Day.domain.repository.SalesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryInitService {

    private final DeliveryRepository deliveryRepository;
    private final SalesRepository salesRepository;

    public void initDeliveries() {

        Sales sales1 = salesRepository.findById(1L).orElse(null);

        List<Delivery> deliveries = List.of(
                Delivery.builder()
                        .name("표준 배송")
                        .price(3000)
                        .sales(sales1)
                        .build(),
                Delivery.builder()
                        .name("급행 배송")
                        .price(10000)
                        .sales(sales1)
                        .build()
        );

        deliveries.forEach(delivery -> {
            if (!deliveryRepository.existsByName(delivery.getName())) {
                deliveryRepository.save(delivery);
            }
        });
    }
}
