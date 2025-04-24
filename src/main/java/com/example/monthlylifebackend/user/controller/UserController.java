package com.example.monthlylifebackend.user.controller;

import com.example.monthlylifebackend.common.BaseResponse;
import com.example.monthlylifebackend.user.dto.req.PostSignupReq;
import com.example.monthlylifebackend.user.dto.res.GetUserDetailRes;
import com.example.monthlylifebackend.user.facade.UserFacade;
import com.example.monthlylifebackend.user.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "회원", description = "회원 가입, 정보 수정, 탈퇴 등 사용자 관련 API")
public class UserController {

    private final UserFacade userFacade;

    @Operation(summary = "회원가입", description = "신규 회원을 등록합니다.")
    @PostMapping("/register")
    public ResponseEntity<BaseResponse<String>> registerUser(@RequestBody @Valid PostSignupReq dto) {
        // 회원가입
        BaseResponse<String> result = BaseResponse.created(userFacade.signup(dto));
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "아이디/비밀번호 찾기", description = "아이디 또는 비밀번호를 찾습니다.")
    @PostMapping ("/find-account")
    public void findIdOrPassword() {
        // 아이디/비밀번호 찾기 로직
    }

    @Operation(summary = "비밀번호 변경", description = "회원의 비밀번호를 변경합니다.")
    @PostMapping("/change-password")
    public void changePassword() {
        // 비밀번호 변경 로직
    }

    @Operation(summary = "회원 탈퇴", description = "현재 로그인한 회원을 탈퇴 처리합니다.")
    @GetMapping("/withdraw")
    public  BaseResponse<Boolean> withdrawUser(@AuthenticationPrincipal @Valid @NotNull User user) {
        // 회원 탈퇴 로직
        BaseResponse<Boolean> result = BaseResponse.onSuccess(userFacade.deleteUser(user));
        return result;
    }

    @Operation(summary = "회원 정보 수정", description = "회원의 개인정보를 수정합니다.")
    @PostMapping("/update")
    public void updateUserInfo() {
        // 회원 정보 수정 로직
    }

    @Operation(summary = "회원 상세 정보 조회", description = "회원의 상세 정보를 조회합니다.")
    @GetMapping("/detail")
    public BaseResponse<GetUserDetailRes> getUserDetail(@AuthenticationPrincipal @Valid @NotNull User user) {
        // 상세 조회 로직
        BaseResponse<GetUserDetailRes> result = BaseResponse.onSuccess(userFacade.getUserDetail(user));
        return result;
    }
}
