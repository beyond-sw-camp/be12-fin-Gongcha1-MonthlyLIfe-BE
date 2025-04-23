package com.example.monthlylifebackend.admin.facade;


import com.example.monthlylifebackend.admin.dto.request.PatchItemCountReq;
import com.example.monthlylifebackend.admin.dto.response.GetProductItemRes;
import com.example.monthlylifebackend.admin.dto.response.GetProductRes;
import com.example.monthlylifebackend.admin.mapper.ItemMapper;
import com.example.monthlylifebackend.admin.service.ItemService;
import com.example.monthlylifebackend.common.customAnnotation.Facade;
import com.example.monthlylifebackend.item.dto.ItemDetailDto;
import com.example.monthlylifebackend.product.dto.res.ProductImageRes;
import com.example.monthlylifebackend.product.model.Product;
import com.example.monthlylifebackend.product.service.ProductService;
import com.example.monthlylifebackend.subscribe.dto.response.GetDeliveryListRes;
import com.example.monthlylifebackend.subscribe.service.SubscribeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Facade
@RequiredArgsConstructor
public class AdminFacade {
    private final SubscribeService subscribeService;
    private final ItemService itemService;
    private final ItemMapper itemMapper;
    private final ProductService productService;

    @Transactional(readOnly = true)
    public List<GetProductRes> findAllItems() {
        List<GetProductRes> dtoList = itemService.findAllItems();
        return dtoList;
    }

    @Transactional(readOnly = true)
    public Page<GetProductRes> findAllItemsByPage(int page, int size) {
        Page<GetProductRes> pagedto = itemService.findAllItemsByPage(page, size);
        return pagedto;
    }

    @Transactional
    public void modifyItemCount(PatchItemCountReq dto) {
         itemService.modifyItemCount(dto);

    }

    @Transactional(readOnly = true)
    public Page<GetDeliveryListRes> findAllDeliveryByPage(int page, int size) {
        Page<GetDeliveryListRes> pagedto = subscribeService.findDeliveryListByPage(page,size);
        return pagedto;
    }


    @Transactional(readOnly = true)
    public GetProductItemRes findItemDetail(String productCode) {
        // 상품 코드로 상품 조회 (엔티티)
        Product product = productService.getProduct(productCode);

        // 상품에 연결된 item 리스트 조회 (엔티티)
        List<ItemDetailDto> itemList = itemService.getItemDetail(productCode);

        // 3) 이미지 리스트 조회 (ItemService에 구현)
        List<ProductImageRes> imgs = itemService.getItemImages(productCode);

        // Mapper로 변환하여 DTO 통합 응답 생성
        return itemMapper.toResponse(product, itemList,imgs);
    }

    public List<GetDeliveryListRes> findAllDelivery() {
        List<GetDeliveryListRes> pagedto = subscribeService.findDeliveryList();
        return pagedto;
    }

}
