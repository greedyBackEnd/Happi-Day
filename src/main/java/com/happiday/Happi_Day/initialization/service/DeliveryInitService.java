package com.happiday.Happi_Day.initialization.service;

import com.happiday.Happi_Day.domain.entity.product.Delivery;
import com.happiday.Happi_Day.domain.entity.product.Sales;
import com.happiday.Happi_Day.domain.repository.DeliveryRepository;
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
            try {
                if (!deliveryRepository.existsByName(delivery.getName())) {
                    deliveryRepository.save(delivery);
                }
            } catch (Exception e) {
                log.error("DB Seeder 배송 정보 저장 중 예외 발생 - 배송정보명: {}", delivery.getName(), e);
                throw new CustomException(ErrorCode.DB_SEEDER_DELIVERY_SAVE_ERROR);
            }
        });
    }
}
