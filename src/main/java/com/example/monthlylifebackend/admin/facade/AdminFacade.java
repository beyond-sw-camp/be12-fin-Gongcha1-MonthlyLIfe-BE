package com.example.monthlylifebackend.admin.facade;


import com.example.monthlylifebackend.admin.dto.request.PatchItemCountReq;
import com.example.monthlylifebackend.admin.dto.response.GetProductItemRes;
import com.example.monthlylifebackend.admin.dto.response.GetProductRes;
import com.example.monthlylifebackend.admin.mapper.ItemMapper;
import com.example.monthlylifebackend.admin.service.ItemService;
import com.example.monthlylifebackend.common.customAnnotation.Facade;
import com.example.monthlylifebackend.item.dto.ItemDetailDto;
import com.example.monthlylifebackend.payment.dto.res.GetAdminPaymentRes;
import com.example.monthlylifebackend.payment.service.PaymentService;
import com.example.monthlylifebackend.product.model.Product;
import com.example.monthlylifebackend.product.service.ProductService;
import com.example.monthlylifebackend.subscribe.dto.res.GetAdminSubscribeDetailRes;
import com.example.monthlylifebackend.subscribe.dto.res.GetAdminSubscribeRes;
import com.example.monthlylifebackend.subscribe.dto.res.GetDeliveryListRes;
import com.example.monthlylifebackend.subscribe.service.SubscribeService;
import com.example.monthlylifebackend.user.dto.res.GetAdminUserRes;
import com.example.monthlylifebackend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Facade
@RequiredArgsConstructor
public class AdminFacade {
    private final SubscribeService subscribeService;
    private final ItemService itemService;
    private final ItemMapper itemMapper;
    private final ProductService productService;
    private final UserService userService;
    private final PaymentService paymentService;

    @Transactional(readOnly = true)
    public List<GetProductRes> findAllItems() {
        List<GetProductRes> dtoList = itemService.findAllItems();
        return dtoList;
    }

    @Transactional(readOnly = true)
    public Page<GetProductRes> findAllItemsByPage(int page, int size, String productName, String manufacturer, LocalDate startDate, LocalDate endDate) {

        Page<GetProductRes> pagedto = itemService.findAllItemsByPage(page, size, productName, manufacturer, startDate, endDate);
        return pagedto;
    }

    @Transactional
    public void modifyItemCount(PatchItemCountReq dto) {
        itemService.modifyItemCount(dto);

    }

    @Transactional(readOnly = true)
    public Page<GetDeliveryListRes> findAllDeliveryByPage(    int page, int size,
                                                              String searchType, String searchQuery,
                                                              LocalDate dateFrom, LocalDate dateTo) {
        Page<GetDeliveryListRes> pagedto = subscribeService.findDeliveryListByPage(page, size, searchType, searchQuery, dateFrom, dateTo);
        return pagedto;
    }


    @Transactional(readOnly = true)
    public GetProductItemRes findItemDetail(String productCode) {
        // 상품 코드로 상품 조회 (엔티티)
        Product product = productService.getProduct(productCode);

        // 상품에 연결된 item 리스트 조회 (엔티티)
        List<ItemDetailDto> itemList = itemService.getItemDetail(productCode);

        // Mapper로 변환하여 DTO 통합 응답 생성
        return itemMapper.toResponse(product, itemList);
    }

    public List<GetDeliveryListRes> findAllDelivery() {
        List<GetDeliveryListRes> pagedto = subscribeService.findDeliveryList();
        return pagedto;
    }

    public Page<GetAdminUserRes> getAdminUserList(int page, int size) {
        Page<GetAdminUserRes> dto = userService.getAdminUserList(page, size);
        return dto;
    }

    public Page<GetAdminPaymentRes> getPayments(int page, int size,String searchType,String searchQuery,LocalDate dateFrom, LocalDate dateTo, boolean overdueOnly) {
        return paymentService.getPayments(page, size, searchType, searchQuery, dateFrom,dateTo, overdueOnly);
    }
    public Page<GetAdminSubscribeRes> getSubscibeByPage(int page, int size,
                                                          String keyword,
                                                          String status,
                                                          Integer minMonths,
                                                          Integer maxMonths) {
        return subscribeService.getAdminSubscribeByPage(page, size, keyword, status, minMonths, maxMonths);
    }
    public List<GetAdminSubscribeDetailRes> getAdminSubscribeDetail(Long subscribeId){
        return subscribeService.getAdminSubscribeDetail(subscribeId);
    }

}


