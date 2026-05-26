package com.furniro.RecomProductService.service.kafka;

import com.furniro.RecomProductService.database.entity.RecomProduct;
import com.furniro.RecomProductService.service.RecommendService;
import com.furniro.RecomProductService.service.event.ProductViewedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductViewedConsumer {

    private final RecommendService recommendService;

    @KafkaListener(
            topics = "product.viewed",
            groupId = "recommend-product"
    )
    public void consume(ProductViewedEvent event) {
        try {
            if (event == null || event.getProductID() == null) {
                log.warn("Invalid product viewed event: {}", event);
                return;
            }

            log.info("Received product viewed event: productID={}, viewedAt={}",
                    event.getProductID(),
                    event.getViewedAt()
            );

            recommendService.handleProductViewed(event);

            List<RecomProduct> similarProducts =
                    recommendService.getSimilarProducts(event.getProductID());

            log.info("Found {} similar products for productID={}",
                    similarProducts.size(),
                    event.getProductID()
            );
        } catch (Exception ex) {
            log.error("Failed to handle product viewed event. productID={}",
                    event.getProductID(),
                    ex
            );
        }
    }
}