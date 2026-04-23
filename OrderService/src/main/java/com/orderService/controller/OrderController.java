package com.orderService.controller;

import com.orderService.requestDto.CancelOrderReqDto;
import com.orderService.requestDto.CreateOrderReqDto;
import com.orderService.requestDto.GetAllOrderReqDto;
import com.orderService.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/getAll")
    public ResponseEntity<?> getAllOrders(@RequestBody GetAllOrderReqDto req){
        return orderService.getAllOrders(req);

    }

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderReqDto req){
        return orderService.createOrder(req);
    }

    @PostMapping("/cancel")
    public ResponseEntity<?> cancelOrder(@RequestBody CancelOrderReqDto req){
        return orderService.cancelOrder(req);
    }
}
