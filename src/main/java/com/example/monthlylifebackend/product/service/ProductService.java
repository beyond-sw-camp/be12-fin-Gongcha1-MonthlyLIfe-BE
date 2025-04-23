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

    public String registerProduct(PostProductRegisterReq dto,
                                  List<MultipartFile> images) {
        // 1) 기본 Product 매핑
        Product product = productMapper.toEntity(dto);

        // 2) 이미지 파일 저장 및 Entity 연결
        try {
            if (images != null && !images.isEmpty()) {
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                for (MultipartFile file : images) {
                    if (file.isEmpty()) continue; // 👈 이거 꼭 넣어야 함!

                    String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
                    Path filePath = uploadPath.resolve(filename);
                    file.transferTo(filePath.toFile());

                    ProductImage img = ProductImage.builder()
                            .product(product)
                            .productImgUrl("/uploads/" + filename)
                            .build();
                    product.getProductImageList().add(img);
                }
            }
        } catch (IOException e) {
            throw new ProductHandler(ErrorStatus._FILE_UPLOAD_ERROR);
        }

        // 3) Product 저장
        productRepository.save(product);

        // 4) Condition 조회 (예외처리 적용)
        Condition condition = conditionRepository.findByName(dto.getCondition())
                .orElseThrow(() -> new ProductHandler(ErrorStatus._NOT_FOUND_CONDITION));

        // 5) ItemLocation 조회 (예외처리 적용)
        ItemLocation location = itemLocationRepository.findByName(dto.getLocation())
                .orElseThrow(() -> new ProductHandler(ErrorStatus._NOT_FOUND_LOCATION));

        // 6) Item 생성 및 저장
        Item item = Item.builder()
                .product(product)
                .condition(condition)
                .itemLocation(location)
                .count(dto.getCount())
                .build();
        itemRepository.save(item);

        return product.getCode();
    }

    // 상품 목록 조회 (변경 없음)
    public List<GetProductListRes> getProductList() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toGetProductListRes)
                .toList();
    }

    // 상품 상세 조회 (예외처리 적용)
    public GetProductDetailRes getProductDetail(String productCode) {
        Product product = productRepository.findById(productCode)
                .orElseThrow(() -> new ProductHandler(ErrorStatus._NOT_FOUND_PRODUCT));
        return productMapper.toGetProductDetailRes(product);
    }

    // 내부용 상품 조회
    public Product getProduct(String productCode) {
        return productRepository.findById(productCode)
                .orElseThrow(() -> new ProductHandler(ErrorStatus._NOT_FOUND_PRODUCT));
    }


}
