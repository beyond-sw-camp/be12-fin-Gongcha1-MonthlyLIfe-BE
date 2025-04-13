package com.example.monthlylifebackend.product.service;


import com.example.monthlylifebackend.product.dto.req.PostProductRegisterReq;
import com.example.monthlylifebackend.product.mapper.ProductMapper;
import com.example.monthlylifebackend.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
 
public class SaleService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public Long registerProduct(PostProductRegisterReq dto) {
         return productRepository.save(productMapper.toEntity(dto)).getIdx();
    }

}
