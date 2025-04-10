package com.example.monthlylifebackend.admin.mapper;

import com.example.monthlylifebackend.admin.dto.request.PatchItemCountReq;
import com.example.monthlylifebackend.product.model.Item;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    //PatchItemCountReq toDTO(Item e);

    Item toEntity(PatchItemCountReq dto);
}
