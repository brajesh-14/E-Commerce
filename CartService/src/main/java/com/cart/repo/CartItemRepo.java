package com.cart.repo;

import com.cart.entity.CartItem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Long> {

        CartItem findByShoppingCart_IdAndProductId(Long cartId, Long productId);

        void deleteByShoppingCart_IdAndProductId(Long cartId, Long productId);

        @Modifying
        @Transactional
        @Query("""
        DELETE FROM CartItem c
        WHERE c.shoppingCart.id = :cartId
        AND c.productId IN :productIds
    """)
        int deleteItems(Long cartId, List<Long> productIds);
}
