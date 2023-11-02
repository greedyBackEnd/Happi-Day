package com.happiday.Happi_Day.domain.service;

import com.happiday.Happi_Day.domain.entity.product.*;
import com.happiday.Happi_Day.domain.entity.product.dto.OrderRequestDto;
import com.happiday.Happi_Day.domain.entity.product.dto.ReadOneOrderDto;
import com.happiday.Happi_Day.domain.entity.user.User;
import com.happiday.Happi_Day.domain.repository.OrderRepository;
import com.happiday.Happi_Day.domain.repository.ProductRepository;
import com.happiday.Happi_Day.domain.repository.SalesRepository;
import com.happiday.Happi_Day.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final UserRepository userRepository;
    private final SalesRepository salesRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    // 주문하기
    @Transactional
    public void order(Long salesId, String username, OrderRequestDto orderRequest) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Sales sales = salesRepository.findById(salesId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Map<Product, Integer> productMap = new HashMap<>();
        List<Product> productList = new ArrayList<>();
        int price = 0;
        for (String productName: orderRequest.getProducts().keySet()) {
            Product product = productRepository.findByNameAndSales(productName, sales)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            if(product.getProductStatus().equals(ProductStatus.OUT_OF_STOCK)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            productMap.put(product, orderRequest.getProducts().get(productName));
            productList.add(product);
            price += product.getPrice()*orderRequest.getProducts().get(productName);
        }

        Order newOrder = Order.builder()
                .user(user)
                .sales(sales)
                .orderStatus(OrderStatus.ORDER_COMPLETED)
                .orderedAt(LocalDateTime.now())
//                .products(productList)
                .address(orderRequest.getAddress())
                .totalPrice(price)
                .build();

        orderRepository.save(newOrder);
    }

    public ReadOneOrderDto orderOneOrder(Long salesId, Long orderId, String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Sales sales = salesRepository.findById(salesId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // user 확인
        if(!user.equals(order.getUser()) && !user.equals(sales.getUsers())) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        return ReadOneOrderDto.fromEntity(sales, order, user);
    }

    // 주문 취소하기
    @Transactional
    public void orderCancel(Long salesId, Long orderId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Sales sales = salesRepository.findById(salesId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(!user.equals(order.getUser())) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        // 배송중이거나 배송완료일 경우 취소 불가
        if(order.getOrderStatus().equals(OrderStatus.DELIVERING) || order.getOrderStatus().equals(OrderStatus.DELIVERY_COMPLETED)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        order.updateStatus(OrderStatus.ORDER_CANCEL);
    }

    // 주문 삭제하기
    @Transactional
    public void orderDelete(Long salesId, Long orderId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Sales sales = salesRepository.findById(salesId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!user.equals(sales.getUsers())) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        if (order.getOrderStatus().equals(OrderStatus.ORDER_CANCEL)) {
            orderRepository.delete(order);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    // 주문 상태 변경
    @Transactional
    public String changeOrderStatus(Long salesId, Long orderId, String username, String status) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Sales sales = salesRepository.findById(salesId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(!user.equals(order.getUser())) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        if(status.equals("입금확인")) return order.updateStatus(OrderStatus.CONFIRM);
        if(status.equals("주문완료")) return order.updateStatus(OrderStatus.ORDER_CANCEL);
        if(status.equals("발송준비중")) return order.updateStatus(OrderStatus.PREPARE);
        if(status.equals("배송중")) return order.updateStatus(OrderStatus.DELIVERING);
        if(status.equals("배송완료")) return order.updateStatus(OrderStatus.DELIVERY_COMPLETED);
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
