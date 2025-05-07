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
    _NOT_ALLOWED_USER(HttpStatus.BAD_REQUEST, "USER4002", "권한이 없는 사용자입니다."),


    //Item 에러
    _NOT_FOUND_ITEM(HttpStatus.NOT_FOUND, "ITEM4004", "아이템이 존재하지 않습니다."),
    _OUT_OF_STOCK(HttpStatus.CONFLICT, "ITEM4090", "아이템이 품절되었습니다."),

    //Payment 에러
    _PAYMENT_FAILED(HttpStatus.BAD_REQUEST, "PAYMENT4000", "결제 실패했습니다."),
    _UNSUPPORTED_PAYMENT_METHOD(HttpStatus.BAD_REQUEST, "PAYMENT4001", "지원하지 않는 결제 수단입니다."),
    _NOT_FOUND_PAYMENT(HttpStatus.NOT_FOUND, "PAYMENT4040", "결제 정보가 존재하지 않습니다."),

    //Product 에러
    _NOT_FOUND_PRODUCT(HttpStatus.NOT_FOUND, "PRODUCT4004", "상품이 존재하지 않습니다."),
    _NOT_FOUND_CONDITION(HttpStatus.NOT_FOUND, "PRODUCT4005", "상품 상태 등급 정보가 존재하지 않습니다."),
    _NOT_FOUND_LOCATION(HttpStatus.NOT_FOUND, "PRODUCT4006", "상품 위치 정보가 존재하지 않습니다."),
    _FILE_UPLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "PRODUCT5001", "파일 업로드에 실패했습니다."),

    //Sale 에러
    _NOT_FOUND_SALE(HttpStatus.NOT_FOUND,         "SALE4001", "판매 상품 정보를 찾을 수 없습니다."),
    _NOT_FOUND_SALE_CATEGORY(HttpStatus.NOT_FOUND,"SALE4002", "카테고리 정보를 찾을 수 없습니다."),
    _NOT_FOUND_SALE_PRODUCT(HttpStatus.NOT_FOUND, "SALE4003", "등록되지 않은 상품 코드입니다."),
    _NOT_FOUND_SALE_CONDITION(HttpStatus.NOT_FOUND,"SALE4004","Condition 정보를 찾을 수 없습니다."),
    _NOT_FOUND_SALE_IN_CATEGORY(HttpStatus.NOT_FOUND,"SALE4005","해당 카테고리에 판매상품이 없습니다."),
    _NOT_FOUND_SALE_PRICE(HttpStatus.NOT_FOUND,   "SALE4006", "판매 가격 정보를 찾을 수 없습니다."),
    _NOT_FOUND_SALE_FOR_DELETE(HttpStatus.NOT_FOUND,"SALE4007","삭제할 판매상품이 없습니다."),
    _INSUFFICIENT_STOCK(HttpStatus.BAD_REQUEST, "SALE4008", "재고가 부족하여 등록할 수 없습니다."),

    //Subscribe 에러
    // Subscribe 에러
    _NOT_FOUND_SUBSCRIBE(HttpStatus.NOT_FOUND, "SUBSCRIBE4004", "구독 정보가 존재하지 않습니다."),
    _INVALID_SUBSCRIBE_STATUS(HttpStatus.BAD_REQUEST, "SUBSCRIBE4001", "현재 구독 상태에서는 반납 요청이 불가능합니다."),
    _DUPLICATED_SUBSCRIBE(HttpStatus.BAD_REQUEST, "SUBSCRIBE4002", "이미 구독된 상품입니다."),
    _NOT_FOUND_SUBSCRIBE_DETAIL(HttpStatus.NOT_FOUND, "SUBSCRIBE4005", "구독 상세 정보가 존재하지 않습니다."),
    _UNAUTHORIZED_SUBSCRIBE_ACCESS(HttpStatus.FORBIDDEN, "SUBSCRIBE4006", "구독에 대한 접근 권한이 없습니다."),
    _ALREADY_RETURN_REQUESTED(HttpStatus.BAD_REQUEST, "SUBSCRIBE4009", "이미 반납 요청된 구독입니다."),
    _ALREADY_SUBSCRIBED(HttpStatus.CONFLICT, "SUBSCRIBE4010", "이미 동일한 조건으로 구독 중입니다."),
    _INVALID_SUBSCRIBE_PERIOD(HttpStatus.BAD_REQUEST, "SUBSCRIBE4011", "잘못된 구독 기간입니다."),
    _NOT_ELIGIBLE_FOR_RETURN(HttpStatus.FORBIDDEN, "SUBSCRIBE4014", "반납 가능한 기간이 아닙니다."),
    _RETURN_DELIVERY_NOT_FOUND(HttpStatus.NOT_FOUND, "SUBSCRIBE4015", "반납 배송 정보가 존재하지 않습니다."),
    _SUBSCRIBE_DETAIL_ALREADY_CANCELED(HttpStatus.BAD_REQUEST, "SUBSCRIBE4016", "이미 해지된 구독 상세입니다."),
    _INVALID_RETURN_REQUEST(HttpStatus.BAD_REQUEST, "SUBSCRIBE4017", "잘못된 반납 요청입니다."),
    _ALREADY_DELIVERED(HttpStatus.BAD_REQUEST, "SUBSCRIBE4018", "이미 배송 완료된 구독입니다."),
    _DELIVERY_IN_PROGRESS(HttpStatus.BAD_REQUEST, "SUBSCRIBE4019", "배송 중인 상품은 반납할 수 없습니다."),
    _AccessDenied_(HttpStatus.BAD_REQUEST, "SUBSCRIBE4020", "본인 구독만 취소 복원이 가능합니다"),
    _INVALID_RETURN_STATUS(HttpStatus.BAD_REQUEST, "SUBSCRIBE4021", "현재 반납(취소) 요청 상태가 아닙니다."),
    _NOT_FOUND_RETURN_(HttpStatus.BAD_REQUEST, "SUBSCRIBE4021", "반납 신청 내역이 없습니다"),
    _NOT_FOUND_ACCESS_RENTAL_INFO_(HttpStatus.BAD_REQUEST, "SUBSCRIBE4022", "배송 정보에 대한 권한이 없습니다."),

// Cart 에러
    _NOT_FOUND_CART(HttpStatus.NOT_FOUND, "CART4004", "장바구니 항목을 찾을 수 없습니다."), // 장바구니에 해당 ID가 없음
    _DUPLICATED_CART_ITEM(HttpStatus.BAD_REQUEST, "CART4001", "이미 장바구니에 존재하는 상품입니다."), // 중복 추가 방지
    _UNAUTHORIZED_CART_ACCESS(HttpStatus.FORBIDDEN, "CART4002", "장바구니에 대한 접근 권한이 없습니다."), // 다른 유저의 장바구니 접근 시
    _EMPTY_CART(HttpStatus.BAD_REQUEST, "CART4003", "장바구니가 비어 있습니다."), // 주문 전에 비어 있을 때
    _CART_SAVE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "CART4005", "장바구니 저장에 실패했습니다."), // 예상치 못한 저장 오류

//ReturnDelivery 에러
    _NOT_FOUND_RETURNDELIVERY(HttpStatus.NOT_FOUND, "ReturnDelivery4004", "해당 반납을 찾을 수 없습니다."); // ReturnDelivery에 해당 ID가 없음




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