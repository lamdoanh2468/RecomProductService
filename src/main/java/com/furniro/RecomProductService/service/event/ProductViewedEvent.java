package com.furniro.RecomProductService.service.event;

import com.furniro.RecomProductService.utils.RecomReason;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductViewedEvent {

    private Integer productID;
    private RecomReason reason;
    private LocalDateTime viewedAt;
}