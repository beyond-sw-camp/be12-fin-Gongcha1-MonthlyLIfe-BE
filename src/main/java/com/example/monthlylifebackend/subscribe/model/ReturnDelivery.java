package com.example.monthlylifebackend.subscribe.model;

import com.example.monthlylifebackend.common.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ReturnDelivery extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    @Schema(description = "구독자 이름", example = "홍길동")
    private String subscribeName;

    @Schema(description = "구독자 전화번호", example = "010-1234-5678")
    private String subscribePhone;



    @Schema(description = "주소 1", example = "서울특별시 강남구 테헤란로 123")
    private String address1;

    @Schema(description = "주소 2", example = "101동 202호")
    private String address2;

    @Schema(description = "서유", example = "문 앞에 놔주세요")
    private String description;

    @Schema(description = "회수 가능 시간", example = "2025-04-14T15:00:00")
    private LocalDateTime pickupDate;


    @Schema(description = "반납 요청 상태", example = "REQUESTED")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReturnDeliveryStatus status = ReturnDeliveryStatus.REQUESTED;

    @ManyToOne
    @JoinColumn(name = "subscribeDetail_idx")
    private SubscribeDetail subscribeDetail;


    public void updateStatus(ReturnDeliveryStatus status) {
        this.status = status;
    }

}