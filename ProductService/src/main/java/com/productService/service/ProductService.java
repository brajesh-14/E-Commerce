package com.productService.service;

import com.productService.dto.ProductReqDto;
import org.springframework.http.ResponseEntity;

public interface ProductService {

    ResponseEntity<?> addProduct(ProductReqDto productReqDto);

    ResponseEntity<?> getAllProducts();

}
