package com.furniro.RecomProductService.dto.response;

import com.furniro.RecomProductService.utils.RecomReason;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RecomProductRes {
    private Integer productID;
    private Double score;
    private RecomReason reason;
}