package com.example.monthlylifebackend.common.exception.handler;

import com.example.monthlylifebackend.common.code.BaseErrorCode;
import com.example.monthlylifebackend.common.exception.GeneralException;

public class UserHandler extends GeneralException {
    public UserHandler(BaseErrorCode code) {
        super(code);
    }
}
