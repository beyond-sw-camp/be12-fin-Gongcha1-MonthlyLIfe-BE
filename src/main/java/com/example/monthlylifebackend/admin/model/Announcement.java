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
@Schema(description = "공지사항")
public class Announcement extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(length = 45)
    private String title;

    @Column(length = 45)
    private String content;
}
