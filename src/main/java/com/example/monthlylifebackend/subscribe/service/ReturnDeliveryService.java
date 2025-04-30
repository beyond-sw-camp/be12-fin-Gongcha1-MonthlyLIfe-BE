package com.example.monthlylifebackend.subscribe.service;

import com.example.monthlylifebackend.admin.repository.ItemRepository;
import com.example.monthlylifebackend.common.code.status.ErrorStatus;
import com.example.monthlylifebackend.common.exception.handler.ItemLocationHandler;
import com.example.monthlylifebackend.common.exception.handler.ReturnDeliveryHandler;
import com.example.monthlylifebackend.item.model.Item;
import com.example.monthlylifebackend.item.model.ItemLocation;
import com.example.monthlylifebackend.product.model.Product;
import com.example.monthlylifebackend.product.repository.ItemLocationRepository;
import com.example.monthlylifebackend.sale.model.Sale;
import com.example.monthlylifebackend.sale.model.SaleHasProduct;
import com.example.monthlylifebackend.sale.repository.SaleHasProductRepository;
import com.example.monthlylifebackend.subscribe.dto.req.PostAdminReturnDeliveryReq;
import com.example.monthlylifebackend.subscribe.dto.res.GetAdminReturnDeliveryRes;
import com.example.monthlylifebackend.subscribe.model.ReturnDelivery;
import com.example.monthlylifebackend.subscribe.model.ReturnDeliveryStatus;
import com.example.monthlylifebackend.subscribe.model.ReturnLocation;
import com.example.monthlylifebackend.subscribe.model.SubscribeDetail;
import com.example.monthlylifebackend.subscribe.repository.ReturnDeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReturnDeliveryService {
    private final ReturnDeliveryRepository returnDeliveryRepository;
    private final SaleHasProductRepository saleHasProductRepository;
    private final ItemRepository itemRepository;
    private final ItemLocationRepository itemLocationRepository;

    public Page<GetAdminReturnDeliveryRes> getReturnRequestList(Pageable pageable, ReturnDeliveryStatus status, LocalDate dateFrom, LocalDate dateTo) {
        LocalDateTime from = dateFrom != null ? dateFrom.atStartOfDay() : null;
        LocalDateTime to = dateTo != null ? dateTo.atTime(23, 59, 59) : null;
        String[] parts = String.valueOf(status).split("_");
        String part = parts[0];
        return returnDeliveryRepository.findReturnRequestByFilter(pageable, part, from, to);
    }


    public Page<GetAdminReturnDeliveryRes> getRePairRequestList(Pageable pageable, ReturnDeliveryStatus status, LocalDate dateFrom, LocalDate dateTo) {
        LocalDateTime from = dateFrom != null ? dateFrom.atStartOfDay() : null;
        LocalDateTime to = dateTo != null ? dateTo.atTime(23, 59, 59) : null;
        String[] parts = String.valueOf(status).split("_");
        String part = parts[0];
        return returnDeliveryRepository.findRepairRequestByFilter(pageable, part, from, to);
    }

    public void updateReturnRequst(Long returnDeliveryIdx, PostAdminReturnDeliveryReq dto) {
        ReturnDelivery delivery = returnDeliveryRepository.findById(returnDeliveryIdx)
                .orElseThrow(() -> new ReturnDeliveryHandler(ErrorStatus._NOT_FOUND_RETURNDELIVERY));
        delivery.updateReturnLocation(ReturnLocation.valueOf(dto.getReturnLocation()));
        delivery.updateStatus(ReturnDeliveryStatus.RETURN_ACCEPT);
        returnDeliveryRepository.save(delivery);
        returnDeliveryRepository.save(delivery);

        if (ReturnLocation.valueOf(dto.getReturnLocation()) == ReturnLocation.WAREHOUSE) {
            SubscribeDetail detail = delivery.getSubscribeDetail();
            Sale sale = detail.getSale(); // Join 되어 있어야 함

            List<SaleHasProduct> saleHasProducts = saleHasProductRepository.findBySale(sale);

            ItemLocation warehouse = itemLocationRepository.findFirstByName("창고").orElseThrow(()->new ItemLocationHandler(ErrorStatus._NOT_FOUND_ITEMLOCATION));
            ItemLocation renting = itemLocationRepository.findFirstByName("대여중").orElseThrow(()->new ItemLocationHandler(ErrorStatus._NOT_FOUND_ITEMLOCATION));
            for (SaleHasProduct shp : saleHasProducts) {
                Product product = shp.getProduct();

                itemRepository.findByProductAndItemLocation(product, warehouse)
                        .ifPresentOrElse(
                                Item::increaseOneCount,
                                () -> {
                                    throw new RuntimeException("창고에 해당 상품 아이템이 존재하지 않습니다: " + product.getCode());
                                }
                        );
                itemRepository.findByProductAndItemLocation(product, renting)
                        .ifPresentOrElse(
                                Item::increaseOneCount,
                                () -> {
                                    throw new RuntimeException("대여중에 해당 상품 아이템이 존재하지 않습니다: " + product.getCode());
                                }
                        );
            }

        }

        if (ReturnLocation.valueOf(dto.getReturnLocation()) == ReturnLocation.REPAIRING) {
            SubscribeDetail detail = delivery.getSubscribeDetail();
            Sale sale = detail.getSale(); // Join 되어 있어야 함

            List<SaleHasProduct> saleHasProducts = saleHasProductRepository.findBySale(sale);

            ItemLocation reparing = itemLocationRepository.findFirstByName("수리중").orElseThrow(() -> new ItemLocationHandler(ErrorStatus._NOT_FOUND_ITEMLOCATION));
            for (SaleHasProduct shp : saleHasProducts) {
                Product product = shp.getProduct();

                itemRepository.findByProductAndItemLocation(product, reparing)
                        .ifPresentOrElse(
                                Item::increaseOneCount,
                                () -> {
                                    throw new RuntimeException("REPAIRING에 해당 상품 아이템이 존재하지 않습니다: " + product.getCode());
                                }
                        );
            }
        }
    }

    public void updateRepairRequest(Long returnDeliveryIdx, PostAdminReturnDeliveryReq dto) {
        ReturnDelivery delivery = returnDeliveryRepository.findById(returnDeliveryIdx)
                .orElseThrow(() -> new ReturnDeliveryHandler(ErrorStatus._NOT_FOUND_RETURNDELIVERY));
        delivery.updateReturnLocation(ReturnLocation.valueOf(dto.getReturnLocation()));
        delivery.updateStatus(ReturnDeliveryStatus.REPAIR_ACCEPT);
        returnDeliveryRepository.save(delivery);

        if (ReturnLocation.valueOf(dto.getReturnLocation()) == ReturnLocation.REPAIRING) {
            SubscribeDetail detail = delivery.getSubscribeDetail();
            Sale sale = detail.getSale(); // Join 되어 있어야 함

            List<SaleHasProduct> saleHasProducts = saleHasProductRepository.findBySale(sale);

            ItemLocation itemLocation = itemLocationRepository.findFirstByName("REPAIRING").orElseThrow(() -> new ItemLocationHandler(ErrorStatus._NOT_FOUND_ITEMLOCATION));
            for (SaleHasProduct shp : saleHasProducts) {
                Product product = shp.getProduct();

                itemRepository.findByProductAndItemLocation(product, itemLocation)
                        .ifPresentOrElse(
                                Item::increaseOneCount,
                                () -> {
                                    throw new RuntimeException("REPAIRING에 해당 상품 아이템이 존재하지 않습니다: " + product.getCode());
                                }
                        );
            }
        }
    }

    public int countByStatuses(List<ReturnDeliveryStatus> returnRequested) {
        return returnDeliveryRepository.countByStatuses(returnRequested);
    }
}
