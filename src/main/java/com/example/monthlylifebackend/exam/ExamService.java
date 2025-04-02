package com.example.monthlylifebackend.exam;


import com.example.monthlylifebackend.common.example.ExampleEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@AllArgsConstructor
public class ExamService {

    private EntityRepository entityRepository;

    public List<ExampleEntity> findAll() {
        return entityRepository.findAll();
    }

    public Optional<ExampleEntity> findById(Long idx) {
        return entityRepository.findById(idx);
    }

    public ExampleEntity save(ExampleEntity entity) {
        return entityRepository.save(entity);
    }

    public ExampleEntity update(Long idx, ExampleEntity updatedEntity) {
        ExampleEntity existing = entityRepository.findById(idx)
                .orElseThrow(() -> new EntityNotFoundException("해당 엔티티를 찾을 수 없습니다."));



        return entityRepository.save(existing);
    }


    public void delete(Long idx) {
        entityRepository.deleteById(idx);
    }







}
