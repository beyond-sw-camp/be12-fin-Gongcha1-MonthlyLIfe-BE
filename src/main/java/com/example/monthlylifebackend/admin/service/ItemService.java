package com.example.monthlylifebackend.admin.service;

import com.example.monthlylifebackend.admin.dto.response.GetItemListRes;
import com.example.monthlylifebackend.admin.repository.ItemRepository;
import com.example.monthlylifebackend.item.model.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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
