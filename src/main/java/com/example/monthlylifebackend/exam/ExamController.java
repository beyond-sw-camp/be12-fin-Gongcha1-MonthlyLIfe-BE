package com.example.monthlylifebackend.exam;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor


public class ExamController {


    private final ExamService examService;

    @GetMapping
    public List<ExampleEntity> getAll() {
        return examService.findAll();
    }

    // 단일 조회
    @GetMapping("/{id}")
    public Optional<ExampleEntity> getOne(@PathVariable Long idx) {
        return examService.findById(idx);
    }

    // 등록
    @PostMapping
    public ExampleEntity create(@RequestBody ExampleEntity exam) {
        return examService.save(exam);
    }

    // 수정 (POST 방식)
    @PostMapping("/{id}/edit")
    public ExampleEntity update(@PathVariable Long id, @RequestBody ExampleEntity updatedExam) {
        return examService.update(id, updatedExam);
    }

    // 삭제
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        examService.delete(id);
        return "Deleted";
    }
    
    
    
}
