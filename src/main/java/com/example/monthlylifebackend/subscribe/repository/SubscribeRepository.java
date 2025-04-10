package com.example.monthlylifebackend.subscribe.repository;


import com.example.monthlylifebackend.subscribe.model.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscribeRepository extends JpaRepository<Subscribe, Integer> {

}
