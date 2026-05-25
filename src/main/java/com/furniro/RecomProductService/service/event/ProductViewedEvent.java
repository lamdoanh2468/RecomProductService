package com.furniro.RecomProductService.service.event;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductViewedEvent {

    private Integer productID;

    private LocalDateTime viewedAt;
}