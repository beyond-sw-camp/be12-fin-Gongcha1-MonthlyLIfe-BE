package com.example.monthlylifebackend.subscribe.model;

import com.example.monthlylifebackend.common.BaseEntity;
import com.example.monthlylifebackend.sale.model.Sale;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Schema(description = "구독 상세 엔티티")
public class SubscribeDetail extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "구독 상세 ID", example = "1")
    private Long idx;

    @Schema(description = "구독 시작일", example = "2025-04-14T00:00:00")
    private LocalDateTime start_at;

    @Schema(description = "구독 종료일", example = "2025-07-14T00:00:00")
    private LocalDateTime endAt;

    @Schema(description = "상품 개별 가격", example = "29900")
    private int price;

    @Schema(description = "구독 기간 (개월 수)", example = "3")
    private String period;

    @ManyToOne
    @JoinColumn(name = "sale_idx")
    @Schema(description = "연결된 판매 상품")
    private Sale sale;

    @ManyToOne
    @JoinColumn(name = "subscribe_idx")
    @Schema(description = "연결된 구독")
    private Subscribe subscribe;

    @Version
    @Schema(description = "버전 (낙관적 락)", example = "0")
    private Long version;

    @OneToMany(mappedBy = "subscribeDetail")
    @Schema(description = "수리 요청 목록")
    private List<RepairRequest> repairRequestList = new ArrayList<>();

    @OneToMany(mappedBy = "subscribeDetail", cascade = CascadeType.ALL)
    @Schema(description = "배송 내역")
    private List<RentalDelivery> rentalDeliveryList = new ArrayList<>();

    @OneToMany(mappedBy = "subscribeDetail")
    @Schema(description = "반납 내역")
    private List<ReturnDelivery> returnDeliveryList = new ArrayList<>();
}
