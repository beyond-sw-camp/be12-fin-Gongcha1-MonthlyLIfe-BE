package com.example.monthlylifebackend.common.code.status;

import com.example.monthlylifebackend.common.code.BaseErrorCode;
import com.example.monthlylifebackend.common.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // 기본 에러
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),


    // User 에러
    _NOT_FOUND_USER(HttpStatus.NOT_FOUND, "USER4000", "사용자가 존재하지 않습니다."),
    _DUPLICATED_USER(HttpStatus.BAD_REQUEST, "USER4001", "중복된 ID 입니다."),


    //Item 에러
    _NOT_FOUND_ITEM(HttpStatus.NOT_FOUND, "ITEM4004", "아이템이 존재하지 않습니다."),

    //Payment 에러
    _FAIL_PAYMENT(HttpStatus.BAD_REQUEST, "PAYMENT4000", "결제 실패했습니다."),
    _NOT_FOUND_PAYMENT(HttpStatus.NOT_FOUND, "PAYMENT4040", "결제 정보가 존재하지 않습니다.");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder().message(message).code(code).isSuccess(false).build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build();
    }
}