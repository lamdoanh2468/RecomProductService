package com.furniro.RecomProductService.database.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "ProductViewLog")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductViewLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer viewLogID;

    private Integer productID;

    private LocalDateTime viewedAt;

    @CreationTimestamp
    private LocalDateTime createdAt;
}