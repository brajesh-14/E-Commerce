package com.orderService.repository;

import com.orderService.entity.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductOrderRepo extends JpaRepository<ProductOrder, Long> {

    List<ProductOrder> findByCartId(Long cartId);
}
