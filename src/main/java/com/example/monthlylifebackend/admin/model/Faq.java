package com.example.monthlylifebackend.admin.model;

import com.example.monthlylifebackend.common.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Getter

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Schema(description = "Faq")
public class Faq extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String question;

    private String answer;
}
