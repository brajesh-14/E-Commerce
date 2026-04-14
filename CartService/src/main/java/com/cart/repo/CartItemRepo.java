package com.cart.repo;

import com.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Long> {

        CartItem findByCartIdProductId(Long cartId, Long productId);

        void deleteByCartIdProductId(Long cartId, Long productId);
}
