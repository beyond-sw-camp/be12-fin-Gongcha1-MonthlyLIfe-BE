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
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public Page<GetProductRes> findAllItemsByPage(int page, int size) {
        Page<GetProductRes> pagedto = (Page<GetProductRes>) itemRepository.findProductStockSummary(PageRequest.of(page,size));
        return pagedto;
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
