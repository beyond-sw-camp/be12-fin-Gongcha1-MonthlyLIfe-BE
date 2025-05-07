package com.example.monthlylifebackend.payment.dto;

import com.example.monthlylifebackend.payment.model.Settlement;
import com.example.monthlylifebackend.subscribe.model.Subscribe;
import com.example.monthlylifebackend.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DailySettlementDto {
    Settlement settlement;
    List<Subscribe> delayedSubscribeList;
    List<User> delayedUserList;

}
