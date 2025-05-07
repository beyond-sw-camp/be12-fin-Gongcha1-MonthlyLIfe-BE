package com.example.monthlylifebackend.user.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class PostLoginReq {
    @Schema(description = "유저 아이디(영문자,숫자 6 ~ 20자)",  example = "test01")
    @Pattern(regexp = "^[a-zA-Z0-9]{6,20}$")
    @NotNull
    private String id;

    @Schema(description = "비밀번호(영문자, 숫자, 특수문자 8~20자, 영어 숫자 1개씩 포함",  example = "qwer1234")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d!@#$%^&*()_+{}|:;<>,.?/~`]{8,20}$")
    @NotNull
    private String password;
}
