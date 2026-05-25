package com.furniro.RecomProductService.service;

import com.furniro.RecomProductService.database.entity.ProductViewLog;
import com.furniro.RecomProductService.database.entity.RecomProduct;
import com.furniro.RecomProductService.database.repository.ProductViewLogRepository;
import com.furniro.RecomProductService.database.repository.RecomProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendService {
    private final ProductViewLogRepository productViewLogRepository;
    private final RecomProductRepository recomProductRepository;

    public void handleProductViewed(Integer productID) {
        ProductViewLog log = ProductViewLog.builder()
                .productID(productID)
                .viewedAt(LocalDateTime.now())
                .build();

        productViewLogRepository.save(log);
    }

    public ResponseEntity<List<RecomProduct>> getSimilarProducts(Integer productID) {
        List<RecomProduct> similarProducts = recomProductRepository
                .findTop8BySourceProductIDAndActiveTrueOrderByScoreDesc(productID);
        return ResponseEntity.ok(similarProducts);
    }

}

