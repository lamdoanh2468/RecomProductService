package com.furniro.RecomProductService.service.kafka;

import com.furniro.RecomProductService.database.entity.RecomProduct;
import com.furniro.RecomProductService.dto.response.RecomProductRes;
import com.furniro.RecomProductService.service.RecommendService;
import com.furniro.RecomProductService.service.event.ProductViewedEvent;
import com.furniro.RecomProductService.utils.RecomReason;
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
        Integer productID = event != null ? event.getProductID() : null;

        try {
            if (productID == null) {
                log.warn("Invalid product viewed event: {}", event);
                return;
            }

            RecomReason reason = event.getReason();

            log.info("Received product viewed event: productID={}, viewedAt={}, reason={}",
                    productID,
                    event.getViewedAt(),
                    reason
            );

            recommendService.handleProductViewed(event);

            List<RecomProductRes> similarProducts;

            if (reason != null) {
                similarProducts = recommendService.getRecommendProducts(productID, reason);
            } else {
                similarProducts = recommendService.getRecommendProducts(productID);
            }

            log.info("Found {} similar products for productID={}, reason={}",
                    similarProducts.size(),
                    productID,
                    reason
            );

        } catch (Exception ex) {
            log.error("Failed to handle product viewed event. productID={}",
                    productID,
                    ex
            );
        }
    }
}