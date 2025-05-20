package com.example.monthlylifebackend.admin.test.sale;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SaleFieldSet {
    public String rawField;
    public String choseongField;
    public String kor2engField;
    public String eng2korField;
    public String ngramField;
}