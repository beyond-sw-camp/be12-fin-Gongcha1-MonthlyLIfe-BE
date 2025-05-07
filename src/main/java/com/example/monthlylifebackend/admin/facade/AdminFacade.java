package com.example.monthlylifebackend.admin.facade;


import com.example.monthlylifebackend.admin.dto.res.*;
import com.example.monthlylifebackend.admin.mapper.ItemMapper;
import com.example.monthlylifebackend.admin.service.ItemService;
import com.example.monthlylifebackend.common.customAnnotation.Facade;
import com.example.monthlylifebackend.item.dto.ItemDetailDto;
import com.example.monthlylifebackend.payment.dto.res.GetAdminPaymentRes;
import com.example.monthlylifebackend.payment.dto.res.GetAdminRecentPaymentRes;
import com.example.monthlylifebackend.payment.service.PaymentService;
import com.example.monthlylifebackend.product.dto.res.ProductImageRes;
import com.example.monthlylifebackend.product.model.Product;
import com.example.monthlylifebackend.product.service.ProductService;
import com.example.monthlylifebackend.subscribe.dto.req.PostAdminReturnDeliveryReq;
import com.example.monthlylifebackend.subscribe.dto.res.GetAdminReturnDeliveryRes;
import com.example.monthlylifebackend.subscribe.dto.res.GetAdminSubscribeDetailRes;
import com.example.monthlylifebackend.subscribe.dto.res.GetAdminSubscribeRes;
import com.example.monthlylifebackend.subscribe.dto.res.GetDeliveryListRes;
import com.example.monthlylifebackend.subscribe.model.ReturnDeliveryStatus;
import com.example.monthlylifebackend.subscribe.service.RentalDeliveryService;
import com.example.monthlylifebackend.subscribe.service.ReturnDeliveryService;
import com.example.monthlylifebackend.subscribe.service.SubscribeDetailService;
import com.example.monthlylifebackend.subscribe.service.SubscribeService;
import com.example.monthlylifebackend.user.dto.res.GetAdminRecentUserRes;
import com.example.monthlylifebackend.user.dto.res.GetAdminUserRes;
import com.example.monthlylifebackend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.example.monthlylifebackend.subscribe.model.SubscribeStatus.SUBSCRIBING;

@Facade
@RequiredArgsConstructor
public class AdminFacade {
    private final SubscribeService subscribeService;
    private final ItemService itemService;
    private final ItemMapper itemMapper;
    private final ProductService productService;
    private final UserService userService;
    private final PaymentService paymentService;
    private final ReturnDeliveryService returnDeliveryService;
    private final SubscribeDetailService subscribeDetailService;
    private final RentalDeliveryService rentalDeliveryService;

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
    public void modifyItemCount(Long idx, int count) {
        itemService.modifyItemCount(idx, count);

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

        // 3) 이미지 리스트 조회 (ItemService에 구현)
        List<ProductImageRes> imgs = itemService.getItemImages(productCode);

        // Mapper로 변환하여 DTO 통합 응답 생성
        return itemMapper.toResponse(product, itemList,imgs);
    }

    @Transactional(readOnly=true)
    public List<GetDeliveryListRes> findAllDelivery() {
        List<GetDeliveryListRes> pagedto = subscribeService.findDeliveryList();
        return pagedto;
    }

    @Transactional(readOnly=true)
    public Page<GetAdminUserRes> getAdminUserList(    int page, int size, String sort,
                                                      String searchType, String searchQuery,
                                                      LocalDate dateFrom, LocalDate dateTo,
                                                      //String tags,
                                                      boolean overdueOnly) {
        Page<GetAdminUserRes> dto = userService.getAdminUserList(page, size, sort, searchType, searchQuery, dateFrom, dateTo,
                //tags,
                overdueOnly);
        return dto;
    }

    @Transactional(readOnly=true)
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

    @Transactional(readOnly=true)
    public List<GetAdminSubscribeDetailRes> getAdminSubscribeDetail(Long subscribeId){
        return subscribeService.getAdminSubscribeDetail(subscribeId);
    }



    @Transactional(readOnly=true)
    public Page<GetAdminReturnDeliveryRes> getReturnRequestList(Pageable pageable, ReturnDeliveryStatus status, LocalDate dateFrom, LocalDate dateTo) {

        return returnDeliveryService.getReturnRequestList(pageable,status,dateFrom, dateTo);
    }

    @Transactional(readOnly=true)
    public Page<GetAdminReturnDeliveryRes> getRepairRequestList(Pageable pageable, ReturnDeliveryStatus status, LocalDate dateFrom, LocalDate dateTo) {
            return returnDeliveryService.getRepairRequestlist(pageable,status,dateFrom, dateTo);
    }

    @Transactional
    public void updateReturnRequest(Long returnDeliveryIdx, PostAdminReturnDeliveryReq dto) {
        returnDeliveryService.updateReturnRequst(returnDeliveryIdx, dto);

    }

    @Transactional
    public void updateRepairRequest(Long returnDeliveryIdx, PostAdminReturnDeliveryReq dto) {
        returnDeliveryService.updateRepairRequest(returnDeliveryIdx, dto);
    }

    @Transactional(readOnly=true)
    public GetAdminHomeCardRes findCardView() {
        return GetAdminHomeCardRes.builder()
                .userCount(userService.countByUser())
                .revenue(subscribeDetailService.SumOfMothlyPrice())
                .build();
    }

    @Transactional(readOnly=true)
    public GetAdminStatisticsCardRes findStatisticsCardView() {
        Long userCount = userService.countByUser();
        Long totalRevenue = paymentService.sumTotalPaid(); // is_paid = true만
        Integer activeSubscriptions = subscribeDetailService.countByStatus(SUBSCRIBING);
        Integer repairAndReturnRequests = returnDeliveryService.countByStatuses(List.of(ReturnDeliveryStatus.RETURN_REQUESTED, ReturnDeliveryStatus.REPAIR_REQUESTED));

        Long productCount = productService.countAll();

        return GetAdminStatisticsCardRes.builder()
                .userCount(userCount)
                .totalRevenue(totalRevenue != null ? totalRevenue : 0)
                .activeSubscriptions(activeSubscriptions)
                .repairAndReturnRequests(repairAndReturnRequests)
                .productCount(productCount)
                .build();
    }

    @Transactional(readOnly=true)
    public GetAdminStatisticsRes findStatistics() {
        List<Integer> monthlySales = paymentService.getAdminStatistics();
        List<Integer> monthlyNewUsers = userService.getAdminStatistics();

        return GetAdminStatisticsRes.builder()
                .monthlySales(monthlySales)
                .monthlyNewUsers(monthlyNewUsers)
                .build();
    }

    @Transactional(readOnly=true)
    public List<GetAdminRecentUserRes> getAdminRecentUsers() {
        return userService.getAdminRecentUserRes();
    }

    @Transactional(readOnly=true)
    public List<GetAdminRecentPaymentRes> getAdminRecentPayments() {
        return paymentService.getAdminRecentPaymentRes();
    }


    public void updateDeliveryStatus(Long deliveryIdx) {
        rentalDeliveryService.updateDeliveryStatus(deliveryIdx);
    }
}


