package com.example.monthlylifebackend.admin.test.item.elastic;


import lombok.Builder;

@Builder
public class FieldSet {
    public String rawField;
    public String choseongField;
    public String kor2engField;
    public String eng2korField;
    public String ngramField;
    public String nestedPath;
    public String nestedField;
}
