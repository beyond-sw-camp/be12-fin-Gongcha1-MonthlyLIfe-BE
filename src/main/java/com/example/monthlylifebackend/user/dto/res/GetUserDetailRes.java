package com.example.monthlylifebackend.user.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetUserDetailRes {

    @Schema(description = "유저 아이디(영문자,숫자 6 ~ 20자)",  example = "test01")
    private String id;
    @Schema(description = "유저 이름(한글 2 ~20자)",  example = "홍길동")
    private String name;
    @Schema(description = "생일(yyyy-MM-dd)", example = "2025-04-10")
    private LocalDate birth;
    @Schema(description = "유저 전화번호(숫자 9 ~ 11자)",  example = "01012345678")
    private String phoneNumber;;
    @Schema(description = "유저 우편번호(숫자 5자)", example = "07060")
    private String postalCode;
    @Schema(description = "유저 기본 주소(100자 이내)", example = "서울시 동작구 보라매로 87")
    private String address1;
    @Schema(description = "유저 상세 주소(100자 이내)", example = "4층 12기 강의실")
    private String address2;
}
