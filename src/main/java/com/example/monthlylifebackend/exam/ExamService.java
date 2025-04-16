package com.example.monthlylifebackend.exam;


import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@AllArgsConstructor
public class ExamService {

    private EntityRepository entityRepository;

    public List<ExamEntity> findAll() {
        return entityRepository.findAll();
    }

    public Optional<ExamEntity> findById(Long idx) {
        return entityRepository.findById(idx);
    }

    public ExamEntity save(ExamEntity entity) {
        return entityRepository.save(entity);
    }

    public ExamEntity update(Long idx, ExamEntity updatedEntity) {
        ExamEntity existing = entityRepository.findById(idx)
                .orElseThrow(() -> new EntityNotFoundException("해당 엔티티를 찾을 수 없습니다."));



        return entityRepository.save(existing);
    }


    public void delete(Long idx) {
        entityRepository.deleteById(idx);
    }







}
