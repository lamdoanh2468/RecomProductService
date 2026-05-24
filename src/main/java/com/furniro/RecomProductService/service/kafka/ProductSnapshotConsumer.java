package com.furniro.RecomProductService.service.kafka;


import com.furniro.RecomProductService.service.event.ProductSnapshotEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor

public class ProductSnapshotConsumer {

    private final ProductSnapshotService productSnapshotService;

    @KafkaListener(
            topics = "${app.kafka.topic.product-snapshot}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(ProductSnapshotEvent event) {
        log.info("Received product snapshot event. productID={}, name={}",
                event.getProductID(),
                event.getName()
        );
        productSnapshotService.handleProductSnapshotEvent(event);
    }


}
