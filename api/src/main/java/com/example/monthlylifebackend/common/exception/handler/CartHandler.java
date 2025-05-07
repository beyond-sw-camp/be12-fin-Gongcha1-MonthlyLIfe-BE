package com.example.monthlylifebackend.common.exception.handler;


import com.example.monthlylifebackend.common.code.BaseErrorCode;
import com.example.monthlylifebackend.common.exception.GeneralException;

public class CartHandler extends GeneralException {
    public CartHandler(BaseErrorCode code) {
        super(code);
    }
}
