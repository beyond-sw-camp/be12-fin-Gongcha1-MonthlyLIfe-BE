package com.example.monthlylifebackend.common.exception.handler;


import com.example.monthlylifebackend.common.code.BaseErrorCode;
import com.example.monthlylifebackend.common.exception.GeneralException;

public class SubcribeHandler extends GeneralException {
    public SubcribeHandler(BaseErrorCode code) {
        super(code);
    }
}
