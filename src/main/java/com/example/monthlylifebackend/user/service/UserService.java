package com.example.monthlylifebackend.user.service;


import com.example.monthlylifebackend.common.exception.handler.UserHandler;
import com.example.monthlylifebackend.user.dto.req.PostSignupReq;
import com.example.monthlylifebackend.user.dto.res.GetAdminUserRes;
import com.example.monthlylifebackend.user.dto.res.GetUserDetailRes;
import com.example.monthlylifebackend.user.mapper.UserMapper;
import com.example.monthlylifebackend.user.model.User;
import com.example.monthlylifebackend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static com.example.monthlylifebackend.common.code.status.ErrorStatus._DUPLICATED_USER;
import static com.example.monthlylifebackend.common.code.status.ErrorStatus._NOT_FOUND_USER;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public GetUserDetailRes getUserDetail(User user) {
        User result = userRepository.findById(user.getId()).orElseThrow(() -> new UserHandler(_NOT_FOUND_USER));
        return userMapper.toGetUserDetailRes(result);
    }

    @Transactional
    public String signup(PostSignupReq dto) {
        // TODO : 동시에 유저를 회원가입시키면 충돌나서 락을 걸거나 다른 로직을 도입해야 한다.
        Optional<User> check = userRepository.findById(dto.getId());
        if (check.isPresent()) throw new UserHandler(_DUPLICATED_USER);

        User user = userMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user.getId();
    }


    //관리자 사용자 조회
    @Transactional(readOnly = true)
    public Page<GetAdminUserRes> getAdminUserList(int page, int size, String sort,
                                                  String searchType, String searchQuery,
                                                  LocalDate dateFrom, LocalDate dateTo,
                                                  //String tags,
                                                  boolean overdueOnly) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        return userRepository.findUserListSummary(
                pageable,
                searchType,
                searchQuery != null && !searchQuery.isBlank() ? searchQuery : null,
                dateFrom != null ? dateFrom.atStartOfDay() : null,
                dateTo != null ? dateTo.atTime(23, 59, 59) : null,
                overdueOnly);
    }
}


