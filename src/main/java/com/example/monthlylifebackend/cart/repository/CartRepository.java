package com.example.monthlylifebackend.cart.repository;


import com.example.monthlylifebackend.cart.dto.GetCartListProjection;
import com.example.monthlylifebackend.cart.model.Cart;
import com.example.monthlylifebackend.sale.model.SalePrice;
import com.example.monthlylifebackend.sale.model.SaleHasProduct;
import com.example.monthlylifebackend.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    void deleteByIdx(Long cartIdx);

    boolean existsByUserAndSalePrice(User user, SalePrice salePrice);


    @Query("""
    SELECT c FROM Cart c
    JOIN FETCH c.salePrice sp
    JOIN FETCH sp.sale s
    JOIN FETCH s.saleHasProductList shp
    JOIN FETCH shp.product p
    LEFT JOIN FETCH p.productImageList pi
    WHERE c.user.id = :userId
""")
    List<Cart> findWithSaleByUser(@Param("userId") String userId);


    @Query(value = """
    SELECT 
        c.idx AS cartIdx,
        s.idx AS saleIdx,
        s.name AS saleName,
        sp.period AS period,
        sp.price AS price,
        p.code AS productCode,
        (
            SELECT pi.product_img_url
            FROM product_image pi
            WHERE pi.product_code = p.code
            LIMIT 1
        ) AS productImgUrl
    FROM cart c
    JOIN sale_price sp ON c.sale_price_idx = sp.idx
    JOIN sale s ON sp.sale_idx = s.idx
    JOIN sale_has_product shp ON s.idx = shp.sale_idx
    JOIN product p ON shp.product_code = p.code
    WHERE c.user_idx = :userId
    GROUP BY c.idx
""", nativeQuery = true)
    List<GetCartListProjection> findCartListProjection(@Param("userId") String userId);



}
