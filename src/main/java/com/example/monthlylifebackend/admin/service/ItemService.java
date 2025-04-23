package com.example.monthlylifebackend.admin.service;

import com.example.monthlylifebackend.admin.dto.request.PatchItemCountReq;
import com.example.monthlylifebackend.admin.dto.response.GetProductRes;
import com.example.monthlylifebackend.admin.repository.ItemRepository;
import com.example.monthlylifebackend.common.code.status.ErrorStatus;
import com.example.monthlylifebackend.common.exception.handler.ItemHandler;
import com.example.monthlylifebackend.item.dto.ItemDetailDto;
import com.example.monthlylifebackend.item.model.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public Page<GetProductRes> findAllItemsByPage(int page, int size, String productName, String manufacturer, LocalDate startDate, LocalDate endDate) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        //--------------------------------------------------------------------
//        // 검색 조건으로 넘기기 전에 null 체크 + 쿼리 조건에 맞는 형태로 가공
//        productName     → LIKE 검색을 위해 "%청소기%" 형태로
//        manufacturer    → LIKE 검색을 위해 "%삼성%" 형태로
//        startDate       → 날짜 시작 범위 계산을 위해 00:00:00 시간 추가
//        endDate         → 날짜 종료 범위 계산을 위해 23:59:59 시간 추가
//          만약에 null이면 쿼리단에서 처리x
         //-------------------------------------------------------------------
        Page<GetProductRes> pagedto = (Page<GetProductRes>) itemRepository.findProductStockSummaryWithConditions(
                pageable,
                productName != null ? "%" + productName + "%" : null,
                manufacturer != null ? "%" + manufacturer + "%" : null,
                startDate != null ? startDate.atStartOfDay() : null,
                endDate != null ? endDate.atTime(23, 59, 59) : null);
        return pagedto;
    }

    public List<GetProductRes> findAllItems() {
        List<GetProductRes> dtoList = itemRepository.findProductStockSummary();
        return dtoList;
    }

    public Item modifyItemCount(PatchItemCountReq dto) {
        Item entity = itemRepository.findById(dto.getIdx()).orElseThrow(() -> new ItemHandler(ErrorStatus._NOT_FOUND_ITEM));
        entity.updateCount(dto.getCount());
        Item item = itemRepository.save(entity);
        return item;
    }




    public List<ItemDetailDto> getItemDetail(String productCode) {

        List<ItemDetailDto> itemList = itemRepository.findStockDetailsByProductCode(productCode);
            return itemList;
    }

}
