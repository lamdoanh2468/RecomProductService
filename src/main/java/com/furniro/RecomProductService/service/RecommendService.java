package com.furniro.RecomProductService.service;

import com.furniro.RecomProductService.database.entity.ProductViewLog;
import com.furniro.RecomProductService.database.repository.ProductViewLogRepository;
import com.furniro.RecomProductService.database.repository.RecomProductRepository;
import com.furniro.RecomProductService.dto.response.RecomProductRes;
import com.furniro.RecomProductService.service.event.ProductViewedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendService {

    private final ProductViewLogRepository productViewLogRepository;
    private final RecomProductRepository recomProductRepository;

    public void handleProductViewed(ProductViewedEvent event) {
        ProductViewLog log = ProductViewLog.builder()
                .productID(event.getProductID())
                .viewedAt(
                        event.getViewedAt() != null
                                ? event.getViewedAt()
                                : LocalDateTime.now()
                )
                .build();

        productViewLogRepository.save(log);
    }

    public List<RecomProductRes> getRecommendProducts(Integer productID) {
        return recomProductRepository
                .findBySourceProductIDAndActiveTrueOrderByScoreDesc(productID)
                .stream()
                .map(recom -> RecomProductRes.builder()
                        .productID(recom.getRecomProductID())
                        .score(recom.getScore())
                        .reason(recom.getReason())
                        .build())
                .toList();
    }
}