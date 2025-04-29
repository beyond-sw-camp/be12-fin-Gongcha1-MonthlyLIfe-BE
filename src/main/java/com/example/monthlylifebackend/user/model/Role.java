package com.example.monthlylifebackend.user.model;

public enum Role {
    ROLE_USER,
    ROLE_ADMIN;

    // 필요하면 prefix 없이 깔끔한 이름 반환 메서드도 만들 수 있어요
    public String getAuthority() {
        return name();
    }
}
