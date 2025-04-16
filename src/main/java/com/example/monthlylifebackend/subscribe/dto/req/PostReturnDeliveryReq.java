package com.example.monthlylifebackend.subscribe.dto.req;

import com.example.monthlylifebackend.subscribe.model.SubscribeDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "반납 배송 요청 DTO")

public class PostReturnDeliveryReq {


        @Schema(description = "구독한 상품 idx ", required = true)
        private Long subscribedetailIdx;

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


    }
    
