package com.orderService.service;

import com.orderService.requestDto.CancelOrderReqDto;
import com.orderService.requestDto.CreateOrderReqDto;
import com.orderService.requestDto.GetAllOrderReqDto;
import org.springframework.http.ResponseEntity;

public interface OrderService {

    ResponseEntity<?> createOrder(CreateOrderReqDto createOrderReqDto);

    ResponseEntity<?> cancelOrder(CancelOrderReqDto cancelOrderReqDto);

    ResponseEntity<?> getAllOrders(GetAllOrderReqDto getAllOrderReqDto);
}
