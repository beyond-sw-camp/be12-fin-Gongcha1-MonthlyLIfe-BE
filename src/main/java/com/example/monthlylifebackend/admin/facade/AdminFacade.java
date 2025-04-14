package com.example.monthlylifebackend.admin.facade;


import com.example.monthlylifebackend.admin.dto.request.PatchItemCountReq;
import com.example.monthlylifebackend.admin.dto.response.GetItemListRes;
import com.example.monthlylifebackend.admin.mapper.ItemMapper;
import com.example.monthlylifebackend.admin.repository.ItemRepository;
import com.example.monthlylifebackend.admin.service.ItemService;
import com.example.monthlylifebackend.common.customAnnotation.Facade;
import com.example.monthlylifebackend.subscribe.dto.response.GetDeliveryListRes;
import com.example.monthlylifebackend.subscribe.service.SubscribeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

@Facade
@RequiredArgsConstructor
public class AdminFacade {
    private final ItemRepository itemRepository;
    private final SubscribeService subscribeService;
    private final ItemService itemService;
    private final ItemMapper itemMapper;

    @Transactional(readOnly = true)
    public Page<GetItemListRes> findAllItemsByPage(int page, int size) {
        Page<GetItemListRes> pagedto = itemService.findAllItemsByPage(page, size);
        return pagedto;
    }

    @Transactional
    public void modifyItemCount(PatchItemCountReq dto) {
        itemService.modifyItemCount(dto);
    }

    @Transactional(readOnly = true)
    public Page<GetDeliveryListRes> findAllDeliveryByPage(int page, int size) {
        Page<GetDeliveryListRes> pagedto = subscribeService.findDeliveryListByPage(page,size);
        return pagedto;
    }

}
