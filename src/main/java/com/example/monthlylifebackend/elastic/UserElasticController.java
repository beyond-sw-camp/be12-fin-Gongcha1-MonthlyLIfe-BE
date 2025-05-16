package com.example.monthlylifebackend.elastic;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserElasticController {

    @Autowired
    private UserElasticSearchRepository repository;

    // 사용자 저장
    @PostMapping
    public ResponseEntity<String> saveUser(@RequestBody UserDoc user) {
        repository.save(user);
        return ResponseEntity.ok("사용자 저장 완료: " + user);
    }

    // 사용자 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable String id) {
        Optional<UserDoc> result = repository.findById(id);
        return result.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
