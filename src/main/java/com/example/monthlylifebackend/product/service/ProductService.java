package com.example.monthlylifebackend.product.service;


import com.example.monthlylifebackend.product.dto.req.PostProductRegisterReq;
import com.example.monthlylifebackend.product.mapper.ProductMapper;
import com.example.monthlylifebackend.product.model.Product;
import com.example.monthlylifebackend.product.repository.ProductRepository;
import com.example.monthlylifebackend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
 
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public Long registerProduct(PostProductRegisterReq dto) {
         return productRepository.save(productMapper.toEntity(dto)).getIdx();
    }

}
