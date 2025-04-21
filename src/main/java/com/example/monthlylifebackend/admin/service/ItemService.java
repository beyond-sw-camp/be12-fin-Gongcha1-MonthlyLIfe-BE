package com.example.monthlylifebackend.admin.service;

import com.example.monthlylifebackend.admin.dto.request.PatchItemCountReq;
import com.example.monthlylifebackend.admin.dto.response.GetProductRes;
import com.example.monthlylifebackend.admin.mapper.ItemMapper;
import com.example.monthlylifebackend.admin.repository.ItemRepository;
import com.example.monthlylifebackend.common.code.status.ErrorStatus;
import com.example.monthlylifebackend.common.exception.handler.ItemHandler;
import com.example.monthlylifebackend.item.dto.ItemDetailDto;
import com.example.monthlylifebackend.item.model.Item;
import com.example.monthlylifebackend.product.dto.res.ProductImageRes;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    public Page<GetProductRes> findAllItemsByPage(int page, int size) {
        // 기존 조회
        Page<GetProductRes> pagedto =
                itemRepository.findProductStockSummaryByPage(PageRequest.of(page, size));

        // pagedto 내부의 summaryDto + 이미지 리스트를 매퍼로 합쳐서 재생성
        pagedto = pagedto.map(summaryDto -> {
            List<ProductImageRes> imgs =
                    itemRepository.findImageListByProductCode(summaryDto.getProductCode());
            return itemMapper.toGetProductRes(summaryDto, imgs);
        });

        return pagedto;
    }

    public List<GetProductRes> findAllItems() {
        // 기존 조회
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
