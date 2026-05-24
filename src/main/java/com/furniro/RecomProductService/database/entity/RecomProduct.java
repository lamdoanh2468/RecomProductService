package com.furniro.RecomProductService.database.entity;

import com.furniro.RecomProductService.utils.RecomReason;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "RecomProduct")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecomProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer recomID;

    @Column(nullable = false)
    private Integer sourceProductID;

    @Column(nullable = false)
    private Integer recomProductID;

    @Column(nullable = false)
    private Double score;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private RecomReason reason;

    private Boolean active = true;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}