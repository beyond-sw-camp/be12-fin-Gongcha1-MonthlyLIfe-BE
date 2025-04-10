package com.example.monthlylifebackend.subscribe.service;


import com.example.monthlylifebackend.subscribe.dto.response.GetDeliveryListRes;
import com.example.monthlylifebackend.subscribe.repository.SubscribeRepository;
 import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
 
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;

    public Page<GetDeliveryListRes> findDeliveryListByPage(int page, int size) {
        Page<GetDeliveryListRes> pagedto = (Page<GetDeliveryListRes>) subscribeRepository.findDeliveryList(PageRequest.of(page,size));
        return pagedto;
    }
}
