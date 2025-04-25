package com.example.monthlylifebackend.subscribe.dto.res;

import com.example.monthlylifebackend.subscribe.model.ReturnDeliveryStatus;
import com.example.monthlylifebackend.subscribe.model.ReturnLocation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAdminReturnDeliveryRes {
     private Long id;
     private String userName;
     private String description;
     private LocalDateTime createdAt;
     private ReturnDeliveryStatus status;
     private ReturnLocation returnLocation;
 }
