package com.example.monthlylifebackend.product.service;


import com.example.monthlylifebackend.admin.repository.ItemRepository;
import com.example.monthlylifebackend.common.code.status.ErrorStatus;
import com.example.monthlylifebackend.common.exception.handler.ProductHandler;
import com.example.monthlylifebackend.item.model.Item;
import com.example.monthlylifebackend.item.model.ItemLocation;
import com.example.monthlylifebackend.product.dto.req.PostProductRegisterReq;
import com.example.monthlylifebackend.product.dto.res.GetProductDetailRes;
import com.example.monthlylifebackend.product.dto.res.GetProductListRes;
import com.example.monthlylifebackend.product.mapper.ProductMapper;
import com.example.monthlylifebackend.product.model.Condition;
import com.example.monthlylifebackend.product.model.Product;
import com.example.monthlylifebackend.product.model.ProductImage;
import com.example.monthlylifebackend.product.repository.ConditionRepository;
import com.example.monthlylifebackend.product.repository.ItemLocationRepository;
import com.example.monthlylifebackend.product.repository.ProductRepository;
import com.example.monthlylifebackend.sale.mapper.SaleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ConditionRepository conditionRepository;
    private final ItemLocationRepository itemLocationRepository;
    private final ItemRepository itemRepository;
    private final SaleMapper saleMapper;

    @Value("${file.upload-dir}")
    private String uploadDir;

//    public String registerProduct(PostProductRegisterReq dto) {
//        // Product 생성
//        Product product = productMapper.toEntityWithImages(dto);
//        productRepository.save(product);
//
//        // Condition 조회
//        Condition condition = conditionRepository.findByName(dto.getCondition())
//                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품 상태 등급입니다: " + dto.getCondition()));
//
//        // ItemLocation 조회
//        ItemLocation location = itemLocationRepository.findByName(dto.getLocation())
//                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 위치입니다: " + dto.getLocation()));
//
//        // Item 생성
//        Item item = Item.builder()
//                .product(product)
//                .condition(condition)
//                .itemLocation(location)
//                .count(1)         // 기본 재고 수량 1개로 설정 (필요 시 수정)
//                .build();
//
//        itemRepository.save(item);
//
//        return product.getCode();
//    }
public String registerProduct(PostProductRegisterReq dto,
                              List<MultipartFile> images) throws IOException {
    // 1) 기본 Product 매핑
    Product product = productMapper.toEntity(dto);

    // 2) 이미지 파일 저장 및 Entity 연결
    if (images != null && !images.isEmpty()) {
        // 업로드 폴더가 없으면 생성
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        for (MultipartFile file : images) {
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(filename);
            file.transferTo(filePath.toFile());

            ProductImage img = ProductImage.builder()
                    .product(product)
                    .productImgUrl("/uploads/" + filename) // 정적 리소스 매핑 경로
                    .build();

            product.getProductImageList().add(img);
        }
    }

    // 3) Product 저장
    productRepository.save(product);

    // 4) 기존 Item 생성 로직
    // … condition, location 조회, Item 저장

    return product.getCode();
}

    // 상품 목록 조회
    public List<GetProductListRes> getProductList() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(productMapper::toGetProductListRes)
                .toList();
    }

    // 상품 상세 조회
    public GetProductDetailRes getProductDetail(String productCode) {
        Product product = productRepository.findById(productCode)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));
        return productMapper.toGetProductDetailRes(product);
    }

    public Product getProduct(String productCode) {
        Product product = productRepository.findById(productCode)
                .orElseThrow(() -> new ProductHandler(ErrorStatus._NOT_FOUND_PRODUCT));

        return product;
    }


}
