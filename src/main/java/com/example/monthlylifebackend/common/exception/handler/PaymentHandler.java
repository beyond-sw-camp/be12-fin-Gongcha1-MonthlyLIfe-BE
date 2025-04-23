package com.example.monthlylifebackend.common.exception.handler;

import com.example.monthlylifebackend.common.code.BaseErrorCode;
import com.example.monthlylifebackend.common.exception.GeneralException;

public class PaymentHandler extends GeneralException {
    public PaymentHandler(BaseErrorCode code) {
        super(code);
    }
}