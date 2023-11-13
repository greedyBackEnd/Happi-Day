package com.happiday.Happi_Day.domain.controller;

import com.happiday.Happi_Day.domain.entity.product.dto.OrderRequestDto;
import com.happiday.Happi_Day.domain.entity.product.dto.ReadOneOrderDto;
import com.happiday.Happi_Day.domain.entity.product.dto.ReadOrderListForSalesDto;
import com.happiday.Happi_Day.domain.entity.product.dto.UpdateOrderDto;
import com.happiday.Happi_Day.domain.service.OrderService;
import com.happiday.Happi_Day.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Security;
import java.util.List;

@RestController
@RequestMapping("/api/v1/sales")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    // 주문하기
    @PostMapping("{salesId}/order")
    public String order(
            @PathVariable("salesId") Long salesId,
            @RequestPart(name = "order") OrderRequestDto orderRequest) {
        String username = SecurityUtils.getCurrentUsername();
        orderService.order(salesId, username, orderRequest);
        return "주문이 완료되었습니다.";
    }

    // 주문 상세 조회
    @GetMapping("{salesId}/order/{orderId}")
    public ResponseEntity<ReadOneOrderDto> readOneOrder(
            @PathVariable("salesId") Long salesId,
            @PathVariable("orderId") Long orderId) {
        String username = SecurityUtils.getCurrentUsername();
        ReadOneOrderDto responseDto = orderService.readOneOrder(salesId, orderId, username);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 판매글 주문 목록 조회
    @GetMapping("{salesId}/order")
    public ResponseEntity<Page<ReadOrderListForSalesDto>> readOrderListForSales(
            @PathVariable("salesId") Long salesId,
            @PageableDefault(size = 12, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        String username = SecurityUtils.getCurrentUsername();
        Page<ReadOrderListForSalesDto> responseDto = orderService.orderListForSales(salesId, username, pageable);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 주문 취소 상태로 변경
    @PutMapping("{salesId}/order/{orderId}/cancel")
    public String orderCancel(
            @PathVariable("salesId") Long salesId,
            @PathVariable("orderId") Long orderId) {
        String username = SecurityUtils.getCurrentUsername();
        orderService.orderCancel(salesId, orderId, username);
        return "주문이 취소되었습니다.";
    }

    // 주문 삭제
    @DeleteMapping("{salesId}/order/{orderId}")
    public String orderDelete(
            @PathVariable("salesId") Long salesId,
            @PathVariable("orderId") Long orderId) {
        String username = SecurityUtils.getCurrentUsername();
        orderService.orderDelete(salesId, orderId, username);
        return "주문이 삭제되었습니다.";
    }

    // 주문 상태 변경
    @PutMapping("{salesId}/order/{orderId}/changeStatus")
    public String changeStatus(
            @PathVariable("salesId") Long salesId,
            @PathVariable("orderId") Long orderId,
            @RequestPart(name = "status") UpdateOrderDto updateOrderDto) {
        String username = SecurityUtils.getCurrentUsername();
        orderService.changeOrderStatus(salesId, orderId, username, updateOrderDto);
        return "주문 상태가 변경되었습니다.";
    }
}
