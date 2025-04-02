package com.example.monthlylifebackend.chat.repository;


import com.example.monthlylifebackend.chat.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository  extends JpaRepository<Chat, Long> {

}
