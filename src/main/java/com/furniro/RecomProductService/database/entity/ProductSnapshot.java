package com.furniro.RecomProductService.database.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ProductSnapshot")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSnapshot {

    @Id
    private Integer productID;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private BigDecimal basePrice;

    private String brand;

    private Integer categoryID;

    private String categoryName;

    private String thumbnailUrl;

    private String status;

    private LocalDateTime productCreatedAt;

    private LocalDateTime productUpdatedAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}