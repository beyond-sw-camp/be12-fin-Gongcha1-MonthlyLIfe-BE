package com.example.monthlylifebackend.subscribe.dto.res;


import com.example.monthlylifebackend.subscribe.model.SubscribeStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor

public class GetSubscribeListRes {

    private Long subscribeIdx;


    private LocalDateTime createdAt;




    private List<GetSubscribeListDto> details;

    public GetSubscribeListRes(Long subscribeIdx, LocalDateTime createdAt,List<GetSubscribeListDto> details) {
        this.details = details;
        this.subscribeIdx = subscribeIdx;
        this.createdAt = createdAt;
    }


}
