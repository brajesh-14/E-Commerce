package com.orderService.service;


import com.orderService.requestDto.CancelOrderReqDto;
import com.orderService.requestDto.CreateOrderReqDto;
import com.orderService.requestDto.GetAllOrderReqDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService{

    @Override
    public ResponseEntity<?> createOrder(CreateOrderReqDto createOrderReqDto) {
        return null;
    }

    @Override
    public ResponseEntity<?> cancelOrder(CancelOrderReqDto cancelOrderReqDto) {
        return null;
    }

    @Override
    public ResponseEntity<?> getAllOrders(GetAllOrderReqDto getAllOrderReqDto) {
        return null;
    }
}
