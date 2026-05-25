package com.furniro.RecomProductService.service.kafka;

import com.furniro.RecomProductService.service.RecommendService;
import com.furniro.RecomProductService.service.event.ProductViewedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductViewedConsumer {

    private final RecommendService recommendService;

    @KafkaListener(
            topics = "product-viewed",
            groupId = "recommend-product"
    )
    public void consume(ProductViewedEvent event) {
        log.info("Received product viewed event. productID={}",
                event.getProductID());

        recommendService.handleProductViewed(event);
    }
}