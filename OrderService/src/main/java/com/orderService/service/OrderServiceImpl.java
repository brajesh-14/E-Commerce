package com.orderService.service;


import com.orderService.entity.OrderItem;
import com.orderService.entity.ProductOrder;
import com.orderService.repository.ProductOrderRepo;
import com.orderService.requestDto.CancelOrderReqDto;
import com.orderService.requestDto.CreateOrderReqDto;
import com.orderService.requestDto.GetAllOrderReqDto;
import com.orderService.responseDto.GetAllOrderRespDto;
import com.orderService.responseDto.OrderRespDto;
import com.orderService.responseDto.ProductItemRespDto;
import com.orderService.responseDto.ServiceResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService{

    private final ProductOrderRepo productOrderRepo;

    @Override
    public ResponseEntity<?> createOrder(CreateOrderReqDto createOrderReqDto) {

        ServiceResponseDto response = new ServiceResponseDto();

        try{

            List<OrderItem> orderItems = createOrderReqDto.getOrderItems()
                    .stream()
                    .map(item -> OrderItem.builder()
                            .productId(item.getProductId())
                            .productName(item.getProductName())
                            .productQuantity(item.getProductQty())
                            .pricePerUnit(item.getPricePerUnit())
                            .totalPrice(item.getTotalPrice())
                            .build()
                    ).toList();


            ProductOrder productOrder = ProductOrder.builder()
                    .cartId(createOrderReqDto.getCartId())
                    .orderStatus("In Progress")
                    .orderItems(orderItems)
                    .totalOrderPrice(createOrderReqDto.getTotalOrderPrice())
                    .build();

            productOrderRepo.save(productOrder);

            response.setCode("200");
            response.setMessage("Order Placed Successfully");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setCode("500");
            response.setMessage("Internal Server Error");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<?> cancelOrder(CancelOrderReqDto cancelOrderReqDto) {

        ServiceResponseDto response = new ServiceResponseDto();

        try{

            ProductOrder productOrder = productOrderRepo.findById(cancelOrderReqDto.getOrderId()).orElseThrow(() -> new RuntimeException("Order Not Found"));

            productOrder.setOrderStatus("CANCELLED");
            productOrderRepo.save(productOrder);

            response.setCode("200");
            response.setMessage("Order Cancelled Successfully");
            return ResponseEntity.ok(response);

        }catch (RuntimeException e){
            response.setCode("404");
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }catch (Exception e){
            response.setCode("500");
            response.setMessage("Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<?> getAllOrders(GetAllOrderReqDto getAllOrderReqDto) {

        try{

            List<ProductOrder> allOrders = productOrderRepo.findByCartId(getAllOrderReqDto.getCartId());

            if(allOrders.isEmpty()){
                return ResponseEntity.ok(Collections.emptyList());
            }

            List<OrderRespDto> orders = allOrders.stream()
                    .map(item -> {

                        List<ProductItemRespDto> orderItems = item.getOrderItems()
                                .stream()
                                .map(m -> ProductItemRespDto.builder()
                                        .id(m.getId())
                                        .productId(m.getProductId())
                                        .productName(m.getProductName())
                                        .productQty(m.getProductQuantity())
                                        .pricePerUnit(m.getPricePerUnit())
                                        .totalPrice(m.getTotalPrice())
                                        .build()
                                ).toList();

                        return OrderRespDto.builder()
                                .orderId(item.getId())
                                .totalOrderPrice(item.getTotalOrderPrice())
                                .orderStatus(item.getOrderStatus())
                                .createdAt(item.getCreatedAt())
                                .updatedAt(item.getUpdatedAt())
                                .orderItems(orderItems)
                                .build();
                    }).toList();

            GetAllOrderRespDto resp = new GetAllOrderRespDto();
            resp.setOrders(orders);

            return ResponseEntity.ok(resp);

        } catch (Exception e) {
            log.error("Error fetching the orders ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }
}
