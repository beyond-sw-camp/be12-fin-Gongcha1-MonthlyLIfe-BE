package com.example.monthlylifebackend.common.code;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
@Builder
public class ReasonDTO{
    private HttpStatus httpStatus;
    private String code;
    private String message;
    private boolean isSuccess;



}
