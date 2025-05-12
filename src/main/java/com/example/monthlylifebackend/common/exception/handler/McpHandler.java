package com.example.monthlylifebackend.common.exception.handler;

import com.example.monthlylifebackend.common.code.BaseErrorCode;
import com.example.monthlylifebackend.common.exception.GeneralException;

public class McpHandler  extends GeneralException {
    public McpHandler(BaseErrorCode code) {
        super(code);
    }
}
