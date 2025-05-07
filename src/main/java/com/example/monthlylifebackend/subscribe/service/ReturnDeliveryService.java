package com.example.monthlylifebackend.subscribe.service;

import com.example.monthlylifebackend.admin.repository.ItemRepository;
import com.example.monthlylifebackend.common.code.status.ErrorStatus;
import com.example.monthlylifebackend.common.exception.handler.ItemHandler;
import com.example.monthlylifebackend.common.exception.handler.ReturnDeliveryHandler;
import com.example.monthlylifebackend.item.model.Item;
import com.example.monthlylifebackend.item.model.ItemLocation;
import com.example.monthlylifebackend.product.model.Condition;
import com.example.monthlylifebackend.product.model.Product;
import com.example.monthlylifebackend.product.repository.ItemLocationRepository;
import com.example.monthlylifebackend.sale.model.Sale;
import com.example.monthlylifebackend.sale.model.SaleHasProduct;
import com.example.monthlylifebackend.sale.repository.SaleHasProductRepository;
import com.example.monthlylifebackend.subscribe.dto.req.PostAdminReturnDeliveryReq;
import com.example.monthlylifebackend.subscribe.dto.res.GetAdminReturnDeliveryRes;
import com.example.monthlylifebackend.subscribe.model.*;
import com.example.monthlylifebackend.subscribe.repository.ReturnDeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.monthlylifebackend.common.code.status.ErrorStatus._NOT_FOUND_LOCATION;

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


    public Page<GetAdminReturnDeliveryRes> getRepairRequestlist(Pageable pageable, ReturnDeliveryStatus status, LocalDate dateFrom, LocalDate dateTo) {
        LocalDateTime from = dateFrom != null ? dateFrom.atStartOfDay() : null;
        LocalDateTime to = dateTo != null ? dateTo.atTime(23, 59, 59) : null;
        String[] parts = String.valueOf(status).split("_");
        String part = parts[0];
        return returnDeliveryRepository.findRepairRequestByFilter(pageable, part, from, to);
    }

    public void updateReturnRequst(Long returnDeliveryIdx, PostAdminReturnDeliveryReq dto) {
        ReturnDelivery delivery = returnDeliveryRepository.findById(returnDeliveryIdx).orElseThrow(() -> new ReturnDeliveryHandler(ErrorStatus._NOT_FOUND_RETURNDELIVERY));
        delivery.updateReturnLocation(ReturnLocation.valueOf(dto.getReturnLocation()));
        delivery.updateStatus(ReturnDeliveryStatus.RETURN_ACCEPT);
        returnDeliveryRepository.save(delivery);

        List<ItemLocation> itemLocationList = itemLocationRepository.findAll();
        ItemLocation warehouse = null;
        ItemLocation subscribing = null;
        ItemLocation repairing = null;

        for (ItemLocation itemLocation : itemLocationList) {
            if (itemLocation.getName().equals("창고")) warehouse = itemLocation;
            if (itemLocation.getName().equals("수리중")) repairing = itemLocation;
            if (itemLocation.getName().equals("대여중")) subscribing = itemLocation;
        }
        if (warehouse == null || repairing == null) {
            throw new ItemHandler(_NOT_FOUND_LOCATION);
        }

        SubscribeDetail detail = delivery.getSubscribeDetail();
        detail.updateStatus(SubscribeStatus.RETURNING);
        Sale sale = detail.getSale();

        List<SaleHasProduct> saleHasProducts = saleHasProductRepository.findBySale(sale);


        if (ReturnLocation.valueOf(dto.getReturnLocation()) == ReturnLocation.WAREHOUSE) {


            for (SaleHasProduct shp : saleHasProducts) {
                Product product = shp.getProduct();
                Condition condition =  shp.getCondition();

                Item itemSubscribing = itemRepository.findByProductAndItemLocationAndCondition(product, subscribing, condition).orElseThrow(() -> new ItemHandler(ErrorStatus._NOT_FOUND_ITEM));
                Item itemWarehouse = itemRepository.findByProductAndItemLocationAndCondition(product, warehouse, condition).orElseThrow(() -> new ItemHandler(ErrorStatus._NOT_FOUND_ITEM));

                if (itemSubscribing.getCount() > 0) {

                    itemSubscribing.reduceOneCount();
                    itemWarehouse.increaseOneCount();

                } else {
                    //재고 개수 안맞음
                    throw new ItemHandler(ErrorStatus._NOT_FOUND_ITEM);
                }
            }

        }

        if (ReturnLocation.valueOf(dto.getReturnLocation()) == ReturnLocation.REPAIRING) {


            for (SaleHasProduct shp : saleHasProducts) {
                Product product = shp.getProduct();
                Condition condition =  shp.getCondition();


                Item itemSubscribing = itemRepository.findByProductAndItemLocationAndCondition(product, subscribing, condition).orElseThrow(() -> new ItemHandler(ErrorStatus._NOT_FOUND_ITEM));
                Item itemRepairing = itemRepository.findByProductAndItemLocationAndCondition(product, repairing, condition).orElseThrow(() -> new ItemHandler(ErrorStatus._NOT_FOUND_ITEM));

                if (itemSubscribing.getCount() > 0) {

                    itemSubscribing.reduceOneCount();
                    itemRepairing.increaseOneCount();

                }
            }
        }
    }


    public void updateRepairRequest(Long returnDeliveryIdx, PostAdminReturnDeliveryReq dto) {

        ReturnDelivery delivery = returnDeliveryRepository.findById(returnDeliveryIdx).orElseThrow(() -> new ReturnDeliveryHandler(ErrorStatus._NOT_FOUND_RETURNDELIVERY));


            SubscribeDetail detail = delivery.getSubscribeDetail();
            Sale sale = detail.getSale();

            List<SaleHasProduct> saleHasProducts = saleHasProductRepository.findBySale(sale);

            List<ItemLocation> itemLocationList = itemLocationRepository.findAll();
            ItemLocation warehouse = null;
            ItemLocation repairing = null;

            for (ItemLocation itemLocation : itemLocationList) {
                if (itemLocation.getName().equals("창고")) warehouse = itemLocation;
                if (itemLocation.getName().equals("수리중")) repairing = itemLocation;
            }
            if (warehouse == null || repairing == null) {
                throw new ItemHandler(_NOT_FOUND_LOCATION);
            }

            for (SaleHasProduct shp : saleHasProducts) {
                Product product = shp.getProduct();
                Condition condition =  shp.getCondition();

                Item warehouseItem = itemRepository.findByProductAndItemLocationAndCondition(product, warehouse,condition).orElseThrow(() -> new ItemHandler(ErrorStatus._NOT_FOUND_ITEM));
                Item repairingItem = itemRepository.findByProductAndItemLocationAndCondition(product, repairing,condition).orElseThrow(() -> new ItemHandler(ErrorStatus._NOT_FOUND_ITEM));

                if (warehouseItem.getCount() > 0) {
                    warehouseItem.reduceOneCount();
                    itemRepository.save(warehouseItem);
                    repairingItem.increaseOneCount();
                    delivery.updateReturnLocation(ReturnLocation.REPAIRING);
                    delivery.updateStatus(ReturnDeliveryStatus.REPAIR_ACCEPT);
                    returnDeliveryRepository.save(delivery);
                } else {
                    delivery.updateStatus(ReturnDeliveryStatus.REPAIR_CANCELED);
                }
        }
    }

    public int countByStatuses(List<ReturnDeliveryStatus> returnRequested) {
        return returnDeliveryRepository.countByStatuses(returnRequested);
    }
}
