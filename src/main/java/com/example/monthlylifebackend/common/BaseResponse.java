package com.example.monthlylifebackend.common;



import com.example.monthlylifebackend.common.code.BaseCode;
import com.example.monthlylifebackend.common.code.BaseErrorCode;
import com.example.monthlylifebackend.common.code.status.ErrorStatus;
import com.example.monthlylifebackend.common.code.status.SuccessStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class BaseResponse<T> {

    @JsonProperty("isSuccess")
    private final Boolean isSuccess;
    private final String code;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    // 성공한 경우 응답 생성
    public static <T> BaseResponse<T> onSuccess(T result) {
        return new BaseResponse<>(true, SuccessStatus._OK.getCode() ,
                SuccessStatus._OK.getMessage(), result);
    }

    public static <T> BaseResponse<T> onSuccess(BaseCode code, T result) {
        return new BaseResponse<>(true, code.getReasonHttpStatus().getCode() ,
                code.getReasonHttpStatus().getMessage(), result);
    }

    // 실패한 경우 응답 생성
    public static <T> BaseResponse<T> onFailure(BaseErrorCode code, T data) {
        return new BaseResponse<>(false, code.getReasonHttpStatus().getCode(),
                code.getReasonHttpStatus().getMessage(), data);
    }

    public static <T> BaseResponse<T> onFailure(T result) {
        return new BaseResponse<>(false, ErrorStatus._BAD_REQUEST.getCode(),
                ErrorStatus._BAD_REQUEST.getMessage(), result);
    }

    // 게시된 경우 응답 생성
    public static <T> BaseResponse<T> created(T result) {
        return new BaseResponse<>(true, SuccessStatus._CREATED.getCode(),
                SuccessStatus._CREATED.getMessage(), result);
    }

    // 삭제된 경우 응답 생성
    public static <T> BaseResponse<T> noContent() {
        return new BaseResponse<>(true, SuccessStatus._NO_CONTENT.getCode(),
                SuccessStatus._NO_CONTENT.getMessage(), null);
    }
}

