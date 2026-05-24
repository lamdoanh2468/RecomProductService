package com.furniro.RecomProductService.database.entity;

import com.furniro.RecomProductService.utils.RecomReason;
import com.furniro.RecomProductService.utils.RecommendationReason;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "ProductRecommendation",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_source_recommended_product",
                        columnNames = {"sourceProductID", "recommendedProductID"}
                )
        },
        indexes = {
                @Index(name = "idx_source_product", columnList = "sourceProductID"),
                @Index(name = "idx_recommended_product", columnList = "recommendedProductID")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecomProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer recommendationID;

    @Column(nullable = false)
    private Integer sourceProductID;

    @Column(nullable = false)
    private Integer recommendedProductID;

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