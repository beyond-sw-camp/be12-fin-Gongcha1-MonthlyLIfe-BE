package com.example.monthlylifebackend.admin.repository;

import com.example.monthlylifebackend.admin.dto.response.GetItemListRes;
import com.example.monthlylifebackend.item.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItemRepository extends JpaRepository<Item, Long> {

@Query("""
            SELECT new com.example.monthlylifebackend.admin.dto.response.GetItemListRes(
            i.count,
            p.name,
            c.name,
            il.name,
            ca.name,
            i.createdAt
    )
            FROM Item i
            JOIN i.product p
            JOIN p.saleHasProductList shp
            JOIN shp.sale s
            JOIN s.category ca
            JOIN i.condition c
            JOIN i.itemLocation il
            """)
Page<GetItemListRes> findItemSummary(PageRequest pageRequest);


}
