package com.example.monthlylifebackend.product.facade;

import com.example.monthlylifebackend.common.customAnnotation.Facade;
import com.example.monthlylifebackend.product.dto.req.PostProductRegisterReq;
import com.example.monthlylifebackend.product.dto.res.GetProductDetailRes;
import com.example.monthlylifebackend.product.dto.res.GetProductListRes;
import com.example.monthlylifebackend.product.repository.ProductRepository;
import com.example.monthlylifebackend.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Facade
@RequiredArgsConstructor
public class ProductFacade {
    private final ProductService productService;

    public String registerProduct(PostProductRegisterReq dto,
                                  List<MultipartFile> images) throws IOException {
        return productService.registerProduct(dto, images);
    }

    public List<GetProductListRes> getProductList() {
        return productService.getProductList();
    }

    public GetProductDetailRes getProductDetail(String productCode) {
        return productService.getProductDetail(productCode);
    }
}
