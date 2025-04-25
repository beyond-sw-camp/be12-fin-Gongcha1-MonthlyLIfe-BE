package com.example.monthlylifebackend.admin.service;

import com.example.monthlylifebackend.admin.dto.request.PatchItemCountReq;
import com.example.monthlylifebackend.admin.dto.response.GetProductRes;
import com.example.monthlylifebackend.admin.mapper.ItemMapper;
import com.example.monthlylifebackend.admin.repository.ItemRepository;
import com.example.monthlylifebackend.common.code.status.ErrorStatus;
import com.example.monthlylifebackend.common.exception.handler.ItemHandler;
import com.example.monthlylifebackend.item.dto.ItemDetailDto;
import com.example.monthlylifebackend.item.model.Item;
import com.example.monthlylifebackend.item.model.ItemLocation;
import com.example.monthlylifebackend.product.dto.res.ProductImageRes;
import com.example.monthlylifebackend.product.model.Condition;
import com.example.monthlylifebackend.product.model.Product;
import com.example.monthlylifebackend.product.repository.ItemLocationRepository;
import com.example.monthlylifebackend.sale.model.SaleHasProduct;
import com.example.monthlylifebackend.subscribe.model.Subscribe;
import com.example.monthlylifebackend.subscribe.model.SubscribeDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.example.monthlylifebackend.common.code.status.ErrorStatus.*;

@RequiredArgsConstructor
@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final ItemLocationRepository itemLocationRepository;

    public Page<GetProductRes> findAllItemsByPage(int page, int size, String productName, String manufacturer, LocalDate startDate, LocalDate endDate) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        LocalDateTime from = startDate != null ? startDate.atStartOfDay() : null;
        String name = productName != null ? "%"+productName+"%" : null;
        String manu = manufacturer != null ? "%"+manufacturer+"%" : null;
        LocalDateTime to = endDate != null ? endDate.atTime(23, 59, 59) : null;

        Page<GetProductRes> pagedto = (Page<GetProductRes>) itemRepository.findProductStockSummaryWithConditions(pageable, name, manu, from, to);
        // pagedto 내부의 summaryDto + 이미지 리스트를 매퍼로 합쳐서 재생성
        pagedto = pagedto.map(summaryDto -> {
            List<ProductImageRes> imgs =
                    Optional.ofNullable(itemRepository.findImageListByProductCode(summaryDto.getProductCode()))
                            .orElse(List.of()); // null이면 빈 리스트 반환
            return itemMapper.toGetProductRes(summaryDto, imgs);
        });

        return pagedto;
    }

    public List<GetProductRes> findAllItems() {
        List<GetProductRes> dtoList = itemRepository.findProductStockSummary();

        // dtoList 내부의 summaryDto + 이미지 리스트를 매퍼로 합쳐서 재생성
        dtoList = dtoList.stream()
                .map(summaryDto -> {
                    List<ProductImageRes> imgs =
                            itemRepository.findImageListByProductCode(summaryDto.getProductCode());
                    return itemMapper.toGetProductRes(summaryDto, imgs);
                })
                .toList();
        return dtoList;
    }

    public Item modifyItemCount(PatchItemCountReq dto) {
        Item entity = itemRepository.findById(dto.getIdx()).orElseThrow(() -> new ItemHandler(_NOT_FOUND_ITEM));
        entity.updateCount(dto.getCount());
        Item item = itemRepository.save(entity);
        return item;
    }




    public List<ItemDetailDto> getItemDetail(String productCode) {

        List<ItemDetailDto> itemList = itemRepository.findStockDetailsByProductCode(productCode);
            return itemList;
    }

    /** 상세 페이지용 이미지 조회 */
    public List<ProductImageRes> getItemImages(String productCode) {
        return itemRepository.findImageListByProductCode(productCode);
    }

    public void subscribeItem(Subscribe subscribe) {
        List<ItemLocation> itemLocationList = itemLocationRepository.findAll();
        ItemLocation subscribing = null;
        ItemLocation warehouse = null;

        for(ItemLocation itemLocation : itemLocationList) {
            if(itemLocation.getName().equals("창고"))
                warehouse = itemLocation;
            if(itemLocation.getName().equals("대여중"))
                subscribing = itemLocation;
        }
        if(warehouse == null || subscribing == null) {
            throw new ItemHandler(_NOT_FOUND_LOCATION);
        }

        for(SubscribeDetail detail : subscribe.getSubscribeDetailList()) {
            for(SaleHasProduct has : detail.getSale().getSaleHasProductList()) {
                Product product = has.getProduct();
                Condition condition = has.getCondition();

                List<Item> itemList = itemRepository.findByConditionAndProduct(condition, product);
                boolean subChanged = false;
                boolean wareChanged = false;
                for(Item item : itemList) {
                    if(Objects.equals(item.getItemLocation().getIdx(), subscribing.getIdx())) {
                        if(item.getCount() <= 0) throw new ItemHandler(_OUT_OF_STOCK);
                        item.reduceOneCount();
                        subChanged = true;
                    }
                    if(Objects.equals(item.getItemLocation().getIdx(), warehouse.getIdx())) {
                        item.increaseOneCount();
                        wareChanged = true;
                    }
                }
                if(!subChanged || !wareChanged)
                    throw new ItemHandler(_NOT_FOUND_ITEM);
                itemRepository.saveAll(itemList);
            }
        }


    }

}
