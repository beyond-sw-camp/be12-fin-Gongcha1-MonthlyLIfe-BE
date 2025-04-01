package com.example.monthlylifebackend.admin.service;


import com.example.monthlylifebackend.admin.repository.AdminRepository;
import com.example.monthlylifebackend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
 
public class AdminService {



    private final AdminRepository adminRepository;
}
