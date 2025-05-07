package com.example.monthlylifebackend.common.exception.handler;


import com.example.monthlylifebackend.common.code.BaseErrorCode;
import com.example.monthlylifebackend.common.exception.GeneralException;

public class ItemLocationHandler extends GeneralException {
    public ItemLocationHandler(BaseErrorCode code) {
        super(code);
    }
}