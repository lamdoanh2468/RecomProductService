package com.furniro.RecomProductService.service;

import com.furniro.RecomProductService.database.entity.ProductViewLog;
import com.furniro.RecomProductService.database.repository.ProductViewLogRepository;
import com.furniro.RecomProductService.database.repository.RecomProductRepository;
import com.furniro.RecomProductService.dto.response.RecomProductRes;
import com.furniro.RecomProductService.service.event.ProductViewedEvent;
import com.furniro.RecomProductService.utils.RecomReason;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
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
    public List<RecomProductRes> getMostViewedProducts(Integer productID) {
        return productViewLogRepository
                .findMostViewedProductIDsExceptCurrentProduct(
                        productID,
                        PageRequest.of(0, 8)
                )
                .stream()
                .map(recomProductID -> RecomProductRes.builder()
                        .productID(recomProductID)
                        .score(0.0)
                        .reason(RecomReason.MOST_VIEWED)
                        .build())
                .toList();
    }
    public List<RecomProductRes> getRecommendProducts(Integer productID) {
        return getRecommendProducts(productID, null);
    }

    public List<RecomProductRes> getRecommendProducts(Integer productID, RecomReason reason) {
        if (reason == RecomReason.MOST_VIEWED) {
            return getMostViewedProducts(productID);
        }

        if (reason != null) {
            return recomProductRepository
                    .findTop8BySourceProductIDAndReasonAndActiveTrueOrderByScoreDesc(productID, reason)
                    .stream()
                    .map(recom -> RecomProductRes.builder()
                            .productID(recom.getRecomProductID())
                            .score(recom.getScore())
                            .reason(recom.getReason())
                            .build())
                    .toList();
        }

        List<RecomProductRes> productRecom = recomProductRepository
                .findTop8BySourceProductIDAndActiveTrueOrderByScoreDesc(productID)
                .stream()
                .map(recom -> RecomProductRes.builder()
                        .productID(recom.getRecomProductID())
                        .score(recom.getScore())
                        .reason(recom.getReason())
                        .build())
                .toList();

        if (!productRecom.isEmpty()) {
            return productRecom;
        }

        return getMostViewedProducts(productID);
    }
}