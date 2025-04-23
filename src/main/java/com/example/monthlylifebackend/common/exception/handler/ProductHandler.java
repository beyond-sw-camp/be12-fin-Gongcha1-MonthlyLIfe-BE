package com.example.monthlylifebackend.common.exception.handler;

import com.example.monthlylifebackend.common.code.BaseErrorCode;
import com.example.monthlylifebackend.common.exception.GeneralException;

public class ProductHandler extends GeneralException {
    public ProductHandler(BaseErrorCode code) {
        super(code);
    }
}