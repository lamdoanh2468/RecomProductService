package com.furniro.RecomProductService.service.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSnapshotEvent {
    private Integer productID;

    private String name;

    private String description;

    private BigDecimal basePrice;

    private String brand;

    private Integer categoryID;

    private String categoryName;

    private String thumbnailUrl;

    private String status;

    private LocalDateTime productCreatedAt;

    private LocalDateTime productUpdatedAt;
}
