package com.example.monthlylifebackend.admin.service;

import com.example.monthlylifebackend.admin.dto.request.PatchItemCountReq;
import com.example.monthlylifebackend.admin.dto.response.GetItemListRes;
import com.example.monthlylifebackend.admin.repository.ItemRepository;
import com.example.monthlylifebackend.product.model.Item;
import com.example.monthlylifebackend.subscribe.dto.response.GetDeliveryListRes;
import com.example.monthlylifebackend.subscribe.repository.SubscribeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public Page<GetItemListRes> findAllItemsByPage(int page, int size) {
        Page<GetItemListRes> pagedto = (Page<GetItemListRes>) itemRepository.findItemSummary(PageRequest.of(page,size));
        return pagedto;
    }

    public void modifyItemCount(Item entity) {
        itemRepository.save(entity);
    }


}
