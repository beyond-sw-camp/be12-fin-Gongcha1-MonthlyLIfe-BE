package com.example.monthlylifebackend.common.exception.handler;

import com.example.monthlylifebackend.common.code.BaseErrorCode;
import com.example.monthlylifebackend.common.exception.GeneralException;

public class ReturnDeliveryHandler extends GeneralException {
    public ReturnDeliveryHandler(BaseErrorCode code) {
        super(code);
    }
}