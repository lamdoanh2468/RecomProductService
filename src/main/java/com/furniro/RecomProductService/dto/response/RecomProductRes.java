package com.furniro.RecomProductService.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecomProductRes {
    private Integer productID;
    private String name;
    private BigDecimal basePrice;
    private String thumbnailUrl;
    private Double score;
    private String reason;
}
