package com.example.monthlylifebackend.common.exception.handler;


import com.example.monthlylifebackend.common.code.BaseErrorCode;
import com.example.monthlylifebackend.common.exception.GeneralException;

public class RentalDeliveryHandler extends GeneralException {
    public RentalDeliveryHandler(BaseErrorCode code) {
        super(code);
    }
}