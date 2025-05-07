package com.example.monthlylifebackend.auth;

import com.example.monthlylifebackend.auth.model.AuthUser;
import com.example.monthlylifebackend.common.exception.handler.UserHandler;
import com.example.monthlylifebackend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.example.monthlylifebackend.common.code.status.ErrorStatus._NOT_FOUND_USER;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new AuthUser(userRepository.findById(username).orElseThrow(
                () -> new UserHandler(_NOT_FOUND_USER)
        ));
    }
}
