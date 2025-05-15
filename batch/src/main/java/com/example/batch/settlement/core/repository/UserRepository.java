package com.example.batch.settlement.core.repository;

import com.example.batch.settlement.core.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
