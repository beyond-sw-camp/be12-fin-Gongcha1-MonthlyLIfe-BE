package com.example.monthlylifebackend.subscribe.dto;

import com.example.monthlylifebackend.sale.model.SalePrice;
import com.example.monthlylifebackend.subscribe.model.Subscribe;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SubscribeAndSalesDto {
    Subscribe subscribe;
    List<SalePrice> salePriceList;
}