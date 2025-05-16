package com.example.batch.settlement.core.domain;

import com.example.batch.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User extends BaseEntity {
    @Id
    @Column(length = 20, nullable = false)
    private String id;

    @Column(length = 20, nullable = false)
    private String phoneNumber;

    @Column(length = 256)
    private String password;

    @Column(length = 10, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String email;

    @ColumnDefault("false")
    private boolean isDelayed;

    @Column(length = 10)
    private String postalCode;

    @Column(length = 100)
    private String address1;

    @Column(length = 100)
    private String address2;

    private LocalDate birth;

    private String role;

    public void setDelayed(boolean isDelayed) {
        this.isDelayed = isDelayed;
    }
}