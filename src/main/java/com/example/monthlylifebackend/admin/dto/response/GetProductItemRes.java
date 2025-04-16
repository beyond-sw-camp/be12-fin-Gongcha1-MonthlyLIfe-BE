package com.example.monthlylifebackend.admin.dto.response;

import com.example.monthlylifebackend.item.dto.ItemDetailDto;
import com.example.monthlylifebackend.product.dto.GetProductDetailDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetProductItemRes {
    GetProductDetailDto productDetail;
    List<ItemDetailDto> dtoList;
}
