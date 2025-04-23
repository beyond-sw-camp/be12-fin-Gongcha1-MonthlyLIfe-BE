package com.example.monthlylifebackend.common.exception.handler;

import com.example.monthlylifebackend.common.code.BaseErrorCode;
import com.example.monthlylifebackend.common.exception.GeneralException;

public class SaleHandler extends GeneralException {
    public SaleHandler(BaseErrorCode code) {
        super(code);
    }
}
