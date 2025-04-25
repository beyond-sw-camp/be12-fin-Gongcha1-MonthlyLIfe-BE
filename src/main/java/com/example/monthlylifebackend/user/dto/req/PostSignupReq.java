package com.example.monthlylifebackend.user.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PostSignupReq {
    @Schema(description = "유저 아이디(영문자,숫자 6 ~ 20자)",  example = "test01")
    @Pattern(regexp = "^[a-zA-Z0-9]{6,20}$")
    @NotNull
    private String id;

    @Schema(description = "유저 전화번호(숫자 9 ~ 11자)",  example = "01012345678")
    @Pattern(regexp = "^\\d{9,11}$")
    @NotNull
    private String phoneNumber;

    @Schema(description = "비밀번호(영문자, 숫자, 특수문자 8~20자, 영어 숫자 1개씩 포함",  example = "qwer1234")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d!@#$%^&*()_+{}|:;<>,.?/~`]{8,20}$")
    private String password;

    @Schema(description = "유저 이름(한글 2 ~20자)",  example = "홍길동")
    @Pattern(regexp = "^[가-힣]{2,20}$")
    @NotNull

    private String name;
    @Schema(description = "이메일(이메일양식)", example = "test@test.com")
    @Email
    @NotNull
    private String email;

    @Schema(description = "유저 우편번호(숫자 5자)", example = "07060")
    @Pattern(regexp = "^\\d{5}$")
    private String postalCode;

    @Schema(description = "유저 기본 주소(100자 이내)", example = "서울특별시 동작구 보라매로 87")
    @Size(max=100)
    private String address1;
    @Schema(description = "유저 상세 주소(100자 이내)", example = "4층 12기 강의실")
    @Size(max=100)
    private String address2;


    @Schema(description = "생일(yyyy-MM-dd)", example = "2025-04-10")
    private LocalDate birth;
}
