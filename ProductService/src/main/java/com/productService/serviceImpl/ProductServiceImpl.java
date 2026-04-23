package com.productService.serviceImpl;

import com.productService.dto.ProductReqDto;
import com.productService.dto.ServiceResponseDto;
import com.productService.entity.Product;
import com.productService.repository.ProductRepo;
import com.productService.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;

    @Override
    public ResponseEntity<?> addProduct(ProductReqDto productReqDto) {

        ServiceResponseDto response = new ServiceResponseDto();

        try{

            Product product = Product.builder()
                    .name(productReqDto.getName())
                    .description(productReqDto.getDescription())
                    .brandName(productReqDto.getBrandName())
                    .pricePerUnit(productReqDto.getPricePerUnit())
                    .productWholeSalePrice(productReqDto.getProductWholeSalePrice())
                    .noOfStocks(productReqDto.getNoOfStocks())
                    .build();

            Product save = productRepo.save(product);

            response.setCode("200");
            response.setMessage("Product Added Successfully");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){

            log.error("Internal Server Error");
            response.setCode("500");
            response.setMessage("Internal Server Error");

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> getAllProducts() {

        ServiceResponseDto response = new ServiceResponseDto();

        try{

            List<Product> allProducts = productRepo.findAll();

            if(!(allProducts == null)){
                return new ResponseEntity<>(allProducts, HttpStatus.OK);
            }else {
                response.setCode("404");
                response.setMessage("No Products available for shopping");

                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Internal Server Error");
            response.setCode("500");
            response.setMessage("Internal Server Error");

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
