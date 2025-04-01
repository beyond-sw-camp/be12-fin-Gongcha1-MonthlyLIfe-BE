package com.example.monthlylifebackend.product.controller;

import org.springframework.web.bind.annotation.PostMapping;

public class ProductManagementController {

    //todo 상품 등록 우선순위 1
    @PostMapping("/ ")
    public void createProduct() {
        // 상품 등록 로직 구현
    }

    //todo 상품 수정 우선순위 2
    @PostMapping("/ ")
    public void updateProduct() {
        // 상품 수정 로직 구현
    }

    //todo 상품 삭제 우선순위 1
    @PostMapping("/ ")
    public void deleteProduct() {
        // 상품 삭제 로직 구현
    }

    //todo 재고 상태 변경 우선순위 1
    @PostMapping("/ ")
    public void updateStockStatus() {
        // 재고 상태 변경 로직 구현
    }

    //todo 재고 추가 우선순위 1
    @PostMapping("/ ")
    public void addStock() {
        // 재고 추가 로직 구현
    }

    //todo 재고 삭제 우선순위 1
    @PostMapping("/ ")
    public void removeStock() {
        // 재고 삭제 로직 구현
    }
}
