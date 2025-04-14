package com.example.monthlylifebackend.common.exception.handler;


import com.example.monthlylifebackend.common.code.BaseErrorCode;
import com.example.monthlylifebackend.common.exception.GeneralException;

public class ItemHandler extends GeneralException {
    public ItemHandler(BaseErrorCode code) {
        super(code);
    }
}
